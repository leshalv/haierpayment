package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**明细资料类型(PL0302)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0302Enum {

    PL0302_1_PROTOCOL_MODLE("CREDIT_MODLE", "征信协议");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0302Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0302Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0302Enum.class);
        }
    }

    PL0302Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
