package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否支持(PL0106)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0106Enum {

    PL0106_1_NONSUPPORT("0", "不支持"),
    PL0106_2_SUPPORT("1", "支持"),
    PL0106_3_UNKNOW("2", "未知");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0106Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0106Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0106Enum.class);
        }
    }

    PL0106Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
