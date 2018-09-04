package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否(PL0109)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0109Enum {

    PL0109_1_Y("Y", "是"),
    PL0109_2_N("N", "否");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0109Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0109Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0109Enum.class);
        }
    }

    PL0109Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
