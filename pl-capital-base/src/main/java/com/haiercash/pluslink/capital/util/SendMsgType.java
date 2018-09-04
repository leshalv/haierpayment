package com.haiercash.pluslink.capital.util;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * type enum of msg_send_request.
 * @author  liu qingxiang
 * @since v1.5.6
 */
public enum SendMsgType {

    /**
     * 支付密码.
     */
    payCode,
    /**
     * 互动金融.
     */
    hdjr,
    /**
     * 乔荣豆子.
     */
    qrdz,
    /**
     * 未知类型
     */
    UNKNOW;

    public boolean isIn(SendMsgType... sendMsgTypes) {
        return Stream.of(sendMsgTypes).anyMatch(sendMsgType -> sendMsgType.equals(this.toString()));
    }

    public static SendMsgType forName(String msgTypeStr) {
        for (SendMsgType sendMsgType : SendMsgType.values()) {
            if (Objects.equals(msgTypeStr, sendMsgType.toString())) {
                return sendMsgType;
            }
        }
        return UNKNOW;
    }
}
