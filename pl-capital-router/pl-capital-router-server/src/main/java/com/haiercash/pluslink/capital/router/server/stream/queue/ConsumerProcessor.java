package com.haiercash.pluslink.capital.router.server.stream.queue;

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
     * 消息发送请求输入channel（通用）
     *
     * @return
     */
    @Input(ConsumerChannelName.INPUT_MSG_REQUEST_CHANNEL)
    SubscribableChannel inputMsgRequestChannel();



}
