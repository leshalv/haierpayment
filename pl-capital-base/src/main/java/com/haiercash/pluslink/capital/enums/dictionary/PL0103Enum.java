package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**成功失败(PL0103)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0103Enum {

    PL0103_1_SUCC("0", "失败"),
    PL0103_2_FAIL("1", "成功");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0103Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0103Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0103Enum.class);
        }
    }

    PL0103Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
