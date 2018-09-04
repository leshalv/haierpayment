package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @date 2018/2/2
 * 放款接口返回码
 */
@Getter
public enum LoanReturnCodeEnum {


    /*********************放款接口返回错误码**************************/


    BUSINESS_REFUSED("PLCP2201", "该笔业务被拒"),

    LENDERS_LIMITED("PLCP2202", "放款额度超限"),

    PARSING_KEY_FAILURE("PLCP2202", "关键要素解析失败"),

    ILLEGAL_CHARACTER("PLCP2204", "报文域值含非法字符");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static LoanReturnCodeEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (LoanReturnCodeEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + LoanReturnCodeEnum.class);
        }
    }

    LoanReturnCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
