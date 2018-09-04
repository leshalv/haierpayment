package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**余额方向(PL1202)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1202Enum {

    PL1202_1_WITHHOLD("WITHHOLD", "扣款"),
    PL1202_2_RECHARGE("RECHARGE", "充值");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1202Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1202Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1202Enum.class);
        }
    }

    PL1202Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
