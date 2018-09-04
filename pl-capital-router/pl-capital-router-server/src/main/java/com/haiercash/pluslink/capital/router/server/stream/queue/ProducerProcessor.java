package com.haiercash.pluslink.capital.router.server.stream.queue;

import com.haiercash.pluslink.capital.common.channel.ProducerChannelName;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 生产者处理器
 *
 * @author keliang.jiang
 * @date 2018/1/2
 */
public interface ProducerProcessor {

    /**
     * 消息发送请求输出channel（发布者）
     *
     * @return
     */
    @Output(ProducerChannelName.OUTPUT_MSG_REQUEST_CHANNEL)
    MessageChannel outputMsgRequestChannel();

}
