package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**交易属性(PL0503)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0503Enum {

    PL0503_1_P("P", "对私业务"),
    PL0503_2_C("C", "对公业务");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0503Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0503Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0503Enum.class);
        }
    }

    PL0503Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
