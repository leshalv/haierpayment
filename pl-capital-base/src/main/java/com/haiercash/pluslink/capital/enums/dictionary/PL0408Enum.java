package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**支持期限(PL0408)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0408Enum {

    PL0408_1_D("D", "天"),
    PL0408_2_M("M", "月");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0408Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0408Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0408Enum.class);
        }
    }

    PL0408Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
