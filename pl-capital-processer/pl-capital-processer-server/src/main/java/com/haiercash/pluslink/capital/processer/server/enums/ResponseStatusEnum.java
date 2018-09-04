package com.haiercash.pluslink.capital.processer.server.enums;

import lombok.Getter;

/**
 * @Auther: dreamer
 * @Date: 2018/7/17 14:22
 * @Description: 支付网关 交易状态
 */
@Getter
public enum ResponseStatusEnum {

    NOTEXISTS("NOTEXISTS", "不存在"),

    PROCESSING("PROCESSING", "处理中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),

    PARTSUCCESS("PARTSUCCESS", "部分成功");


    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static ResponseStatusEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (ResponseStatusEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + ResponseStatusEnum.class);
        }
    }

    ResponseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
