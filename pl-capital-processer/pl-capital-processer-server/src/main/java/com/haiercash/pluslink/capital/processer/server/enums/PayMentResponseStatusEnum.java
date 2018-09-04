package com.haiercash.pluslink.capital.processer.server.enums;

import lombok.Getter;

/**
 * @author Administrator
 * @Title: PayMentResponseStatusEnum
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/2110:23
 */


@Getter
public enum PayMentResponseStatusEnum {
    /*********************支付网关返回交易状态码*单笔接口返回值只有不存在，处理中，失败，成功4个状态************************/
    NOTEXISTS("NOTEXISTS", "不存在"),

    PROCESSING("PROCESSING", "处理中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功");

   // PARTSUCCESS("PARTSUCCESS", "部分成功");


    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PayMentResponseStatusEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PayMentResponseStatusEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PayMentResponseStatusEnum.class);
        }
    }

    PayMentResponseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
