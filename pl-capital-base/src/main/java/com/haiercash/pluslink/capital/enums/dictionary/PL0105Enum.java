package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否(PL0105)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0105Enum {

    PL0105_1_YES("YES", "是"),
    PL0105_2_NO("NO", "否");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0105Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0105Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0105Enum.class);
        }
    }

    PL0105Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
