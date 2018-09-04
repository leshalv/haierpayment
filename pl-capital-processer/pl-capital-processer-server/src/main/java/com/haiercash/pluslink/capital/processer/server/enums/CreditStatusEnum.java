package com.haiercash.pluslink.capital.processer.server.enums;

import lombok.Getter;

/**
 * > 授信状态枚举
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/23 13:53
 */
@Getter
public enum CreditStatusEnum {

    CREDIT_FAILED("PLCO3201", "失败"),
    PROCESS_OF_FIELD_ERROR("PLCO3501","属性异常"),
    CREDIT_APPROVAL("PLCO3244", "额度审批中"),
    CREDIT_HEAD_SUCCESS("000000","成功");

    private String code;
    private String desc;
    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static CreditStatusEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (CreditStatusEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + CreditStatusEnum.class);
        }
    }

    CreditStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
