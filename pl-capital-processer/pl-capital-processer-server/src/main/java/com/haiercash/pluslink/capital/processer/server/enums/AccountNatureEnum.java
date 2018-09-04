package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @Title: 支付网关交易属性枚举
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1410:50
 */
@Getter
public enum AccountNatureEnum {


    /*********************支付网关交易属性枚举*************************/
    FOR_PERSONAL("P", "对私业务"),
    FOR_CORPORATE("C", "对公业务");
    private String code;
    private String desc;

    /**
     * 根据不同属性码获取枚举.
     *
     * @param properties 枚举定义的编码
     * @return 查找到的枚举
     */
    public static boolean  getEnumByPro(String properties) {
        try {
            for (AccountNatureEnum codeEnum : values()) {
                if (codeEnum.toString().equals(properties)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    AccountNatureEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
