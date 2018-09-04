package com.haiercash.pluslink.capital.processer.server.mq.consumer;

import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.common.channel.ConsumerChannelName;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.PaymentGatewayBackContext;
import com.haiercash.pluslink.capital.processer.server.pvm.vo.PayMentCallBackVo;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: PayMentCallBackConsumService
 * @ProjectName pl-capital
 * @Description: 消费支付回调接口的mq
 * @date 2018/7/1117:21
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class PayMentCallBackConsumService implements Serializable {

    @Autowired
    private AssetsSplitService assetsSplitService;

    @Autowired
    private FlowWorkerServer flowWorkerServer;

    @StreamListener(ConsumerChannelName.PL_TRANS_RESULTS_EXCHANGE_CONSUMER)
    @Transactional
    public void consumPayMentCallBack(String message) {
        log.info("支付回调消费成功consumPayMentCallBack:" + message);
        PayMentCallBackVo payMentCallBackVo = JsonUtils.readObjectByJson(message, PayMentCallBackVo.class);
        //查询资产表信息
        AssetsSplit assetsSplit = assetsSplitService.selectBycontractNo(payMentCallBackVo.getElecChequeNo());
        if (assetsSplit == null) {
            log.info("查询合同号【{}】为空，将不调用支付网关回调流程。", payMentCallBackVo.getElecChequeNo());
            return;
        }
        AssetsSplit assetsSplitCache = assetsSplitService.get(assetsSplit.getId());
        if (PL0506Enum.PL0506_3_30.getCode().equals(assetsSplitCache.getLoanStatus())) {
            log.info("支付回调，业务单号：{},网关放款成功，不做处理");
            return;
        }
        //调用bus
        PaymentGatewayBackContext paymentGatewayBackContext = new PaymentGatewayBackContext(assetsSplit, payMentCallBackVo.getTradeStatus(), payMentCallBackVo.getElecChequeNo(), payMentCallBackVo.getBankOrderNo());
        flowWorkerServer.paymentGatewayBackHandler(paymentGatewayBackContext, this.getClass().getName());
    }
}
