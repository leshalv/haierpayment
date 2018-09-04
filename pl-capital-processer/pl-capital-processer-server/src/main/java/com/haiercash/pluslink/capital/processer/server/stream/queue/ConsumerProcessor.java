package com.haiercash.pluslink.capital.processer.server.stream.queue;

import com.haiercash.pluslink.capital.common.channel.ConsumerChannelName;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消费者处理器
 *
 * @author keliang.jiang
 * @date 2017/12/17
 */
public interface ConsumerProcessor {

    /**
     * 资产消息消费
     *
     * @return
     */
    @Input(ConsumerChannelName.PL_LENDING_EXCHANGE_CONSUMER)
    SubscribableChannel inputMsgRequestChannel();

    /**
     * 支付网关消费
     *
     * @return
     */
    @Input(ConsumerChannelName.PL_TRANS_RESULTS_EXCHANGE_CONSUMER)
    SubscribableChannel inputTransResultsChannel();

}
