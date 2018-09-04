package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**入件渠道(PL0407)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0407Enum {

    PL0407_1_HAIFU("19", "嗨付");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0407Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0407Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0407Enum.class);
        }
    }

    PL0407Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
