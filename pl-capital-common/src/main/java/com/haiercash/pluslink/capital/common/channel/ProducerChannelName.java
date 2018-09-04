package com.haiercash.pluslink.capital.common.channel;

/**
 * 生产者渠道名称
 *
 * @author keliang.jiang
 * @date 2017/12/27
 */
public abstract class ProducerChannelName {

    /**
     * 输出队列名称-消息发送请求（通用）
     */
    public static final String OUTPUT_MSG_REQUEST_CHANNEL = "outputMsgRequestChannel";

    /**
     * 输出队列名称-消息发送请求（签约短信专用队列）
     */
    public static final String OUTPUT_SIGN_VIP_REQUEST_CHANNEL = "outputSignVipRequestChannel";

    /**
     * 输出队列名称-消息发送记录（通用）
     */
    public static final String OUTPUT_MSG_RECORD_CHANNEL_COMMON = "outputMsgRecordChannelCommon";

    /**
     * 输出队列名称-消息发记录（签约短信专用队列）
     */
    public static final String OUTPUT_SIGN_VIP_RECORD_CHANNEL = "outputSignVipRecordChannel";

    /**
     * 输出队列名称-外联通知队列
     */
    public static final String OUTPUT_CMIS_CHANNEL = "outputCmisChannel";

    public static final String PL_LENDING_EXCHANGE = "plLendingExchange";
    public static final String PL_LENDING_CMIS_EXCHANGE = "plLendingCmisExchange";
}
