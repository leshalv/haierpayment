/*
package com.haiercash.pluslink.capital.processer.client;



import com.haiercash.pluslink.capital.common.ribbon.BaseRestClient;
import com.haiercash.pluslink.capital.processer.api.IBroadcastPushApi;
import com.haiercash.pluslink.capital.processer.api.dto.request.BroadcastPushRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.BroadcastPushResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

*/
/**
 * created  by jun
 * 2018-03-07 下午 7:31
 **//*

@Slf4j
@Service("messageSendClient")
@ConfigurationProperties(prefix = "app.message.info")
public class MessageSendClient extends BaseRestClient implements IBroadcastPushApi {
    */
/**
     * 创建广播消息
     *
     * @param request
     * @return
     *//*

    @Override
    public BroadcastPushResponse createBroadcastPush(BroadcastPushRequest request) {
        try {
            String methord = "/createBroadcastPush";
            BroadcastPushResponse resp = this.postForEntity(this.getRestUrl(methord), request, BroadcastPushResponse.class);
            return resp;
        } catch (Exception e) {
            log.error("消息发送失败", e);
        }
        return null;
    }
}
*/
