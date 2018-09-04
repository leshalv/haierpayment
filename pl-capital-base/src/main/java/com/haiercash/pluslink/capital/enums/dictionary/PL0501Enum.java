package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**证件类型(PL0501)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0501Enum {

    PL0501_1_ID("ID", "身份证"),
    PL0501_2_PA("PA", "护照"),
    PL0501_3_HV("HV", "回乡证"),
    PL0501_4_TW("TW", "台胞证"),
    PL0501_5_OF("OF", "军官证"),
    PL0501_6_PD("PD", "警官证"),
    PL0501_7_SO("SO", "士兵证"),
    PL0501_8_MER("MER", "商户");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0501Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0501Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0501Enum.class);
        }
    }

    PL0501Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
