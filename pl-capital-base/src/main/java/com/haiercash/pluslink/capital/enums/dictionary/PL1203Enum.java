package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**
 * 辅助类型(PL1203)
 *
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1203Enum {

    PL1203_1_0011("PAY_CHANNEL", "0011", "支付渠道,存放同业"),
    PL1203_2_0019("PAY_SUPPLIER", "0019", "支付供应商"),
    PL1203_3_0006("PAY_PROJECT", "0006", "支联合项目辅助");
    private String code;
    private String desc;
    private String desc2;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1203Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1203Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1203Enum.class);
        }
    }

    PL1203Enum(String code, String desc, String desc2) {
        this.code = code;
        this.desc = desc;
        this.desc2 = desc2;
    }
}
