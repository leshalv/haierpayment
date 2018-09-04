package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**记账方式(PL0504)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0504Enum {

    PL0504_1_JDE("JDE", "JDE记账"),
    PL0504_2_SAP("SAP", "SAP记账");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0504Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0504Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0504Enum.class);
        }
    }

    PL0504Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
