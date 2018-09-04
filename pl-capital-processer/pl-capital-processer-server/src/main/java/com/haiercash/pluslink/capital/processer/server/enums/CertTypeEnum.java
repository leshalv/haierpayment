package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @Title: 支付网关证件类型枚举
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1410:50
 */
@Getter
public enum CertTypeEnum {


    /*********************支付网关证件类型枚举*************************/
    ID_CARD("ID", "身份证"),
    PASSPORT("PA", "护照"),
    HOME_VISIT("HV", "回乡证"),
    TAIWAN_CERT("TW", "台胞证"),
    OFFICERS("OF", "军官证"),
    POLICE("PD", "警官证"),
    SOLDIERS("SO", "士兵证"),
    MERCHANT("MER", "商户");


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
            for (CertTypeEnum codeEnum : values()) {
                if (codeEnum.toString().equals(properties)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    CertTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
