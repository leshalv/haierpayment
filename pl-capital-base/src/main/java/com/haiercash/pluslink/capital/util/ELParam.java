package com.haiercash.pluslink.capital.util;

import java.util.Map;

/**
 * 计算AND 回调 EL 表达式上下文参数
 *
 * @author 许崇雷
 * @date 2017/6/6
 */
public class ELParam {
    private String channel;
    private String pushType;
    private String msgType;
    private Map<String, Object> custom;

    //region property

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Map<String, Object> getCustom() {
        return custom;
    }

    public void setCustom(Map<String, Object> custom) {
        this.custom = custom;
    }

//endregion
}
