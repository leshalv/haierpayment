package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**合作项目状态(PL0401)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0401Enum {

    PL0401_1_CLOSE("00", "未启用"),
    PL0401_2_OPEN("10", "启用");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0401Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0401Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0401Enum.class);
        }
    }

    PL0401Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
