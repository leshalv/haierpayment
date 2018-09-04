package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @Title: 支付网关客户类型枚举
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1410:50
 */
@Getter
public enum CRMTypeEnum {


    /*********************支付网关客户类型枚举*************************/
    PERSONAL("PERSONAL", "个人"),
    MERCHANT("MERCHANT", "商户"),
    STORE("STORE", "门店"),
    COOPERATION("COOPERATION", "合作机构");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static CRMTypeEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (CRMTypeEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + CRMTypeEnum.class);
        }
    }


    /**
     * 根据属性判断枚举是否存在.
     *
     * @param properties 枚举定义的编码
     * @return 查找到的枚举
     */
    public static boolean getEnumByPro(String properties) {
        try {
            for (CRMTypeEnum codeEnum : values()) {
                if (codeEnum.toString().equals(properties)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    CRMTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
