package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**应用ID(PL1101)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1101Enum {

    PL1101_1_PL("pl", "资金平台");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1101Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1101Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1101Enum.class);
        }
    }

    PL1101Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
