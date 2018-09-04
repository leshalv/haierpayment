package com.haiercash.pluslink.capital.processer.server.pvm.event;


import com.alibaba.fastjson.JSON;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.processer.server.pvm.EngineEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

/**
 * 放款申请事件
 *
 * @author lzh
 * @Title: LendingEvent
 * @ProjectName pl-capital
 * @Description: 处理中心放款接口传mq的事件
 * @date 2018/7/915:12
 */
@Setter
@Getter
public class LoanApplicationEvent extends EngineEvent {

    //请求属性
    private AssetsSplit assetsSplit;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LoanApplicationEvent(Object source, AssetsSplit assetsSplit) {
        super(source);
        this.assetsSplit = assetsSplit;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(assetsSplit);
    }

    public Message getMessage() {
        String json = toString();
        MessageProperties properties = new MessageProperties();
        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        properties.setContentLength(json.length() + 200);
        properties.setContentEncoding("UTF-8");
        return new Message(json.getBytes(), properties);
    }
}
