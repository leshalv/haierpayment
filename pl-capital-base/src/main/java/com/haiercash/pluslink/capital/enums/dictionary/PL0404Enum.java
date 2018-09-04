package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**匹配规则(PL0404)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0404Enum {

    PL0404_1_P("P", "产品"),
    PL0404_2_C("C", "渠道"),
    PL0404_3_S("S", "标签"),
    PL0404_4_PC("PC", "产品+渠道"),
    PL0404_5_CS("CS", "渠道+标签"),
    PL0404_6_PS("PS", "产品+标签"),
    PL0404_7_PCS("PCS", "产品+渠道+标签");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0404Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0404Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0404Enum.class);
        }
    }

    PL0404Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
