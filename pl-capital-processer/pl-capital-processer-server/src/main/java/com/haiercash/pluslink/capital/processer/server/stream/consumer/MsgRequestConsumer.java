//package com.haiercash.pluslink.capital.processer.server.stream.consumer;
//
//import com.haiercash.pluslink.capital.common.channel.ConsumerChannelName;
//import com.haiercash.pluslink.capital.data.MsgRequest;
//import com.haiercash.pluslink.capital.processer.server.biz.IMsgSendRecordBiz;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
///**
// * 消息发送请求消费者
// *
// * @author keliang.jiang
// * @date 2017/12/28
// */
//@Slf4j
//@Component
//public class MsgRequestConsumer {
//
//    @Autowired
//   // private IMsgSendRecordBiz msgSendRecordBiz;
//
//    /**
//     * 消息发送请求通用队列处理
//     *
//     * @param request
//     */
//    @StreamListener(ConsumerChannelName.INPUT_MSG_REQUEST_CHANNEL)
//    public void handleCommon(@Payload MsgRequest request) {
//        try {
//            log.info("消息请求处理:接收到消息发送请求内容：{}，执行处理", request.getId());
//            msgSendRecordBiz.handleRequest(request);
//        } catch (Exception e) {
//            log.error(String.format("消息请求处理:消息处理失败：%s", request), e);
//        }
//    }
//
//    /**
//     * 消息发送请求签约短信专用队列处理
//     *
//     * @param request
//     */
//    @StreamListener(ConsumerChannelName.INPUT_SIGN_VIP_REQUEST_CHANNEL)
//    public void handleSignVip(@Payload MsgRequest request) {
//        try {
//            log.info("消息请求处理（签约短信）:接收到消息发送请求内容：{}，执行处理", request.getId());
//            msgSendRecordBiz.handleRequest(request);
//        } catch (Exception e) {
//            log.error(String.format("消息请求处理:消息处理失败：%s", request), e);
//        }
//    }
//
//}
