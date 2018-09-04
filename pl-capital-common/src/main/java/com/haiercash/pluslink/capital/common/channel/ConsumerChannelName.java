package com.haiercash.pluslink.capital.common.channel;

/**
 * 消费者渠道名称
 *
 * @author keliang.jiang
 * @date 2018/1/2
 */
public class ConsumerChannelName {

    /**
     * 输入队列名称-消息发送请求
     */
    public static final String INPUT_MSG_REQUEST_CHANNEL = "inputMsgRequestChannel";

    public static final String INPUT_SIGN_VIP_REQUEST_CHANNEL = "inputSignVipRequestChannel";

    /**
     * 输入队列名称-消息发送记录（通用）
     */
    public static final String INPUT_MSG_RECORD_CHANNEL_COMMON = "inputMsgRecordChannelCommon";

    public static final String INPUT_SIGN_VIP_RECORD_CHANNEL = "inputSignVipRecordChannel";

    /**
     * 输入队列名称-营销活动
     */
    public static final String INPUT_MARKET_ACTIVITY = "inputMarketActivity";

    /**
     * 输入队列名称-节点活动
     */
    public static final String INPUT_NODE_ACTIVITY = "inputNodeActivity";
    /*资产拆分消费*/
    public static final String PL_LENDING_EXCHANGE_CONSUMER = "plLendingExchangeConsumer";
    /*支付回调消费*/
    public static final String PL_TRANS_RESULTS_EXCHANGE_CONSUMER = "plTransResultsExchangeConsumer";
}
