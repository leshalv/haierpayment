package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**规则类型(PL0901)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0901Enum {

    PL0901_1_REAL("0", "实时"),
    PL0901_2_ASYNC("1", "异步");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0901Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0901Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0901Enum.class);
        }
    }

    PL0901Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
