package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.common.utils.JsonUtils;
import cn.jbinfo.integration.busflow.context.BusContext;
import com.haiercash.pluslink.capital.common.utils.SpringUtil;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.processer.server.cache.PayMentGateWayRequestCache;
import com.haiercash.pluslink.capital.processer.server.enums.LoanStatusEnum;
import com.haiercash.pluslink.capital.processer.server.enums.ResponseStatusEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.PaymentGatewayBackHandler;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.PaymentGatewayBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.PayMentGateWayClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWaySearchRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PayMentGateWayLoanResponse;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

/**
 * > 查询支付网关代付状态实现类
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/18 15:43
 */
@Slf4j
public class TradeStatusTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.TradeStatusTask";

    private AssetsSplitService assetsSplitService;
    private PayMentGateWayClient payMentGateWayClient;

    PayMentGateWayRequestCache payMentGateWayRequestCache;

    private void init() {
        this.assetsSplitService = SpringUtil.getBean(AssetsSplitService.class);
        this.payMentGateWayClient = SpringUtil.getBean(PayMentGateWayClient.class);
        this.payMentGateWayRequestCache = SpringUtil.getBean(PayMentGateWayRequestCache.class);
    }

    /**
     * @param jobContext {"piType":"COLLECTION","elecChequeNo":"req_no_n1","use":"test use","businessPayNo":""}
     * @return
     */
    @Override
    public boolean execute(String jobContext) {
        this.init();
        //logger.info("===============定时查询支付网关放款结果入口===============");
        try {
            PayMentGateWaySearchRequest request = JsonUtils.readObjectByJson(jobContext, PayMentGateWaySearchRequest.class);
            AssetsSplit assetsSplit = assetsSplitService.selectBycontractNo(request.getElecChequeNo());
            if (FlowWorkUtils.isFinalState(assetsSplit)) {
                return true;
            }
            log.info("===============<处理中心:业务编号{}，定时查询支付网关放款结果入口>-查询资产表放款状态{}===============", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus());
            if (LoanStatusEnum.LOAN_INDEPENDENT_HANDING.getCode().equals(assetsSplit.getLoanStatus())) {
                PayMentGateWayLoanResponse response;
                try {
                    response = payMentGateWayClient.searchLoan(request);
                    log.info("业务编号：{}，网关状态{}，描述：{}", assetsSplit.getApplSeq(), response.getTradeStatus(), response.getErrorDesc());
                } catch (Exception ex) {
                    log.error("业务编号：{}，网关查询异常,任务继续：", assetsSplit.getApplSeq(), ex);
                    return false;
                }
                //如果查询支付网关放款不存在
                if (ResponseStatusEnum.NOTEXISTS.getCode().equals(response.getTradeStatus())) {
                    log.info("===============<处理中心:调用支付网关超时，业务编号{},重新推送支付网关放款", assetsSplit.getApplSeq());
                    //通知支付网关放款
                    try {
                        PayMentGateWayLoanRequest payMentGateWayLoanRequest = payMentGateWayRequestCache.get(request.getElecChequeNo());
                        log.info("===============<处理中心:调用支付网关超时，业务编号{},重新推送支付网关放款请求{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(payMentGateWayLoanRequest));
                        PayMentGateWayLoanResponse payMentGateWayLoanResponse = payMentGateWayClient.notifyLoan(payMentGateWayLoanRequest);

                        if (payMentGateWayLoanResponse != null) {

                            if (ResponseStatusEnum.FAIL.getCode().equals(payMentGateWayLoanResponse.getTradeStatus()) ||
                                    ResponseStatusEnum.SUCCESS.getCode().equals(payMentGateWayLoanResponse.getTradeStatus())) {
                                PaymentGatewayBackContext backContext = new PaymentGatewayBackContext(assetsSplit, response.getTradeStatus(), response.getElecChequeNo(), response.getBankOrderNo());
                                FlowWorkerServer flowWorkerServer = SpringContextHolder.getBean(FlowWorkerServer.class);
                                BusContext busContext = flowWorkerServer.paymentGatewayBackHandler(backContext, this.getClass().getName());
                                return !Objects.equals(busContext.getValue(PaymentGatewayBackHandler.HANDLE_STATUS), "-1");
                            }
                        }
                        log.info("===============<处理中心:重新推送网关放款结果，业务编号{},重新推送支付网关放款结果{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(payMentGateWayLoanResponse));
                        //异常，网关成功，网关失败，网关处理中
                    } catch (Exception e) {
                        log.error("业务编号{}，通知网关放款异常：{}", assetsSplit.getApplSeq(), e);
                    }
                    log.info("业务编号{}任务继续", assetsSplit.getApplSeq());
                    return false;
                } else {
                    log.info("业务编号{}进入支付网关回调flow", assetsSplit.getApplSeq());
                    PaymentGatewayBackContext backContext = new PaymentGatewayBackContext(assetsSplit, response.getTradeStatus(), response.getElecChequeNo(), response.getBankOrderNo());
                    FlowWorkerServer flowWorkerServer = SpringContextHolder.getBean(FlowWorkerServer.class);
                    BusContext busContext = flowWorkerServer.paymentGatewayBackHandler(backContext, this.getClass().getName());
                    return !Objects.equals(busContext.getValue(PaymentGatewayBackHandler.HANDLE_STATUS), "-1");
                }
            }
            log.info("业务编号{},任务结束", assetsSplit.getApplSeq());
            return false;
        } catch (RetryableException ex) {
            log.error("查询支付网关异常，将继续执行本次任务：", ex);
            return false;
        }
    }

}
