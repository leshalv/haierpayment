package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**借据类型(PL0602)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0602Enum {

    PL0602_1_OWN("1", "自有"),
    PL0602_2_OUTSIDE("2", "外部资方");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0602Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0602Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0602Enum.class);
        }
    }

    PL0602Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
