package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.server.cache.PayMentGateWayRequestCache;
import com.haiercash.pluslink.capital.processer.server.copier.LoanApplyForCopier;
import com.haiercash.pluslink.capital.processer.server.enums.PayMentResponseStatusEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.event.LoanApplicationEvent;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PayMentGateWayLoanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lzh
 * @Title: LoanApplyForHandleService
 * @ProjectName pl-capital
 * @Description: 异步处理中心放款处理接口service
 * @date 2018/7/915:29
 */
@Service
@Slf4j
public class LoanApplyForHandleService extends BaseService {

    @Autowired
    private PayMentGateWayLoanService payMentGateWayLoanService;
    @Autowired
    private LoanDetailService loanDetailService;
    @Autowired
    PayMentGateWayRequestCache payMentGateWayRequestCache;
    @Autowired
    AssetsSplitService assetsSplitService;

    //都是同步的不需要事物回滚
    public void applyForHandle(RestRequest<LoanApplyForRequest> restRequest, AssetsSplit assetsSplitmq) {

        LoanApplyForRequest loanApplyForRequest = restRequest.getBody();

        //放款状态
        //支付网关返回消息
        PayMentGateWayLoanResponse payMentGateWayLoanResponse = new PayMentGateWayLoanResponse();
        PayMentGateWayLoanRequest payMentGateWayLoanRequest = new PayMentGateWayLoanRequest();
        try {
            log.info("=============业务编号:{},异步调用支付网关程序开始==============", loanApplyForRequest.getApplSeq());
            payMentGateWayLoanRequest = getPayMentGateWayLoanRequest(loanApplyForRequest, assetsSplitmq);
            //设置推送mq的请求Ip字段
            assetsSplitmq.setRequestIp(payMentGateWayLoanRequest.getRequestIp());
            payMentGateWayLoanResponse = payMentGateWayLoanService.notifyLoan(payMentGateWayLoanRequest);
            log.info("业务编号:{},推送支付网关放款请求参数：{}", loanApplyForRequest.getApplSeq(), JsonUtils.safeObjectToJson(payMentGateWayLoanRequest));
            //根据不同返回结果状态修改资产表状态分为 成功，失败，处理中，数据不存在
            //如果数据不存在或者失败更新资产表抛出异常
            if (StringUtils.equals(PayMentResponseStatusEnum.NOTEXISTS.getCode(), payMentGateWayLoanResponse.getTradeStatus())
                    || StringUtils.equals(PayMentResponseStatusEnum.FAIL.getCode(), payMentGateWayLoanResponse.getTradeStatus())) {
                //assetsSplitService.updateLoanStatusById(assetsSplitmq, PL0506Enum.PL0506_6_60, PL0106Enum.PL0106_1_NONSUPPORT.getCode());
                assetsSplitmq.setLoanStatus(PL0506Enum.PL0506_6_60.getCode());
            } else {
                assetsSplitmq.setLoanStatus(PL0506Enum.getEnumByPayMentReturnStatus(payMentGateWayLoanResponse.getTradeStatus()).getCode());
                //如果状态处理中更新资产表
                if (StringUtils.equals(PayMentResponseStatusEnum.PROCESSING.getCode(), payMentGateWayLoanResponse.getTradeStatus())) {
                    //更改资产表状态是网关处理中
                    assetsSplitService.updateOnlyLoanStatusById(assetsSplitmq, PL0506Enum.PL0506_2_20);
                }
                //assetsSplitService.updateLoanStatusById(assetsSplitmq, PL0506Enum.getEnumByPayMentReturnStatus(payMentGateWayLoanResponse.getTradeStatus()), PL0106Enum.PL0106_1_NONSUPPORT.getCode());
            }

            log.info("===============<处理中心>-业务编号:{},成功通知支付网关放款=====交易状态:{},放款状态描述:{}", loanApplyForRequest.getApplSeq(), payMentGateWayLoanResponse.getTradeStatus(), payMentGateWayLoanResponse.getErrorDesc());
            log.info("===============<处理中心>-业务编号:{},资产表放款状态{}", loanApplyForRequest.getApplSeq(), PL0506Enum.getEnumByPayMentReturnStatus(payMentGateWayLoanResponse.getTradeStatus()).getCode());
        } catch (SystemException e) {
            log.error("<处理中心>-业务编号:{},通知支付网关放款失败，放款状态设置为网关处理中，处理中心请求信息:{},失败描述:{},异常信息:{}", loanApplyForRequest.getApplSeq(), JsonUtils.writeObjectToJson(restRequest), payMentGateWayLoanResponse.getErrorDesc(), e.getThrowable().getMessage());
            //修改资产表状态
            assetsSplitmq.setLoanStatus(PL0506Enum.PL0506_2_20.getCode());
            //更改资产表状态是网关处理中
            assetsSplitService.updateOnlyLoanStatusById(assetsSplitmq, PL0506Enum.PL0506_2_20);
            //发布事件，产生mq
            publishLoanApplicationEvent(assetsSplitmq);
            //支付请求信息存入redis缓存
            payMentGateWayRequestCache.put(payMentGateWayLoanRequest);
            log.info("===============<处理中心>-业务编号:{},网关支付请求成功加入redis缓存[{}]", loanApplyForRequest.getApplSeq(), cn.jbinfo.cloud.core.utils.JsonUtils.writeObjectToJson(payMentGateWayLoanRequest));
            return;
        }
        //发布事件，产生mq
        publishLoanApplicationEvent(assetsSplitmq);

    }

    /**
     * 推送放款mq给资产拆分
     */
    public void publishLoanApplicationEvent(AssetsSplit assetsSplitmq) {
        //设置MQ的消息体
        //发布事件，产生mq
        LoanApplicationEvent loanApplicationEvent = new LoanApplicationEvent(this, assetsSplitmq);
        applicationEventPublisher.publishEvent(loanApplicationEvent);
    }

    /**
     * 得到支付网关请求实体
     */
    public PayMentGateWayLoanRequest getPayMentGateWayLoanRequest(LoanApplyForRequest loanApplyForRequest, AssetsSplit assetsSplitmq) {
        //通知支付网关放款
        //放款请求的消息转化为支付网关放款请求
        PayMentGateWayLoanRequest payMentGateWayLoanRequest = LoanApplyForCopier.convert(loanApplyForRequest);
        //固定值
        payMentGateWayLoanRequest.setPiType("TRANSFER");
        //设置业务类型
        payMentGateWayLoanRequest.setBizType("PLR_CARD_TRANSFER");
        //默认为信贷系统标识
        payMentGateWayLoanRequest.setMerchantNo("pl");
        //凭证号填合同id
        payMentGateWayLoanRequest.setElecChequeNo(loanApplyForRequest.getContractNo());
        payMentGateWayLoanRequest.setRequestTime(DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        //到账模式固定实时
        payMentGateWayLoanRequest.setArrivalMode("REAL");
        //业务流水号传合同编号
        payMentGateWayLoanRequest.setBusinessPayNo(loanApplyForRequest.getContractNo());
        //机构系统版本号
        payMentGateWayLoanRequest.setInstitutionVersion("V1");

        //调用支付网关付款service
        //设置请求ip 从风险接口获取,测试时暂时查询不到ip写死，打出logger,后续去掉此处try catch
        try {
            payMentGateWayLoanRequest.setRequestIp(loanDetailService.getAddrIp(
                    new ApplInfoAppRequestBody(assetsSplitmq.getApplSeq(), assetsSplitmq.getCertNo(), assetsSplitmq.getChannelNo(), null)));
        } catch (SystemException e) {
            //后续添加返回核心mq，修改资产表状态,抛出异常
            payMentGateWayLoanRequest.setRequestIp("127.0.0.1");
            log.info("===============<处理中心>-业务编号:{},调用风险分析接口未查到对应身份证的requestIp，测试写死Ip===============", assetsSplitmq.getApplSeq());
            return payMentGateWayLoanRequest;
        }
        return payMentGateWayLoanRequest;
    }

}
