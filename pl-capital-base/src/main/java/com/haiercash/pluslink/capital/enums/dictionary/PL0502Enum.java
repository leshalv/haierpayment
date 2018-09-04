package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**银行卡类型(PL0502)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0502Enum {

    PL0502_1_DC("DC", "借记卡");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0502Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0502Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0502Enum.class);
        }
    }

    PL0502Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
