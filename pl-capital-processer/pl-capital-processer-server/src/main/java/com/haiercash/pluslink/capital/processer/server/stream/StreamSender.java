package com.haiercash.pluslink.capital.processer.server.stream;

import com.haiercash.pluslink.capital.processer.server.stream.queue.ProducerProcessor;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class StreamSender {
    @Autowired
    private ProducerProcessor producerProcessor;

    public void outputAsstesSplitItem(String Message) {
        producerProcessor.outputAsstesSplitItem().send(MessageBuilder.withPayload(Message).build());
    }

    public void outputCmisLendingResult(String Message) {
        producerProcessor.outputCmisLendingResult().send(MessageBuilder.withPayload(Message).build());
    }

}
