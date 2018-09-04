package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @Title: 支付网关是否枚举
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1410:50
 */
@Getter
public enum YesNoEnum {


    /*********************支付网关是否枚举*************************/
    YES("YES", "是"),
    NO("NO", "否");

    private String code;
    private String desc;

    /**
     * 根据枚举属性取枚举.
     *
     * @param properties 枚举定义的编码
     * @return 查找到的枚举
     */
    public static boolean getEnumByPro(String properties) {
        try {
            for (YesNoEnum codeEnum : values()) {
                if (codeEnum.toString().equals(properties)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    YesNoEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
