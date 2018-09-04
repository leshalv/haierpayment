package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否(PL0102)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0102Enum {

    PL0102_1_FALSE("0", "否"),
    PL0102_2_TRUE("1", "是");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0102Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0102Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0102Enum.class);
        }
    }

    PL0102Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
