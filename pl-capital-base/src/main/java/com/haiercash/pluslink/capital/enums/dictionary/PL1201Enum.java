package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**受控标识(PL1201)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1201Enum {

    PL1201_1_LOANRECEIPT("LOANRECEIPT", "借据号");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1201Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1201Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1201Enum.class);
        }
    }

    PL1201Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
