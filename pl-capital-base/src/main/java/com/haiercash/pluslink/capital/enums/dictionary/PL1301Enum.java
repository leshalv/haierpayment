package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**客户类型(PL1301)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1301Enum {

    PL1301_1_STORE("STORE", "门店"),
    PL1301_2_MERCHANT("MERCHANT", "商户"),
    PL1301_3_PERSONAL("PERSONAL", "个人");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1301Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1301Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1301Enum.class);
        }
    }

    PL1301Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
