package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**支付工具(PL1204)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1204Enum {

    PL1204_1_CASH("CASH", "现金"),
    PL1204_2_NETB("NETB", "网银"),
    PL1204_3_PPCD("PPCD", "预付费卡"),
    PL1204_4_POST("POST", "汇款"),
    PL1204_5_CARD("CARD", "银行卡"),
    PL1204_6_EXPR("EXPR", "快捷"),
    PL1204_7_COLL("COLL", "代收"),
    PL1204_8_TRAN("TRAN", "代付/付款"),
    PL1204_9_ACT_COLL("ACT_COLL", "余额代收"),
    PL1204_10_ACT_TRAN("ACT_TRAN", "余额代付");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1204Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1204Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1204Enum.class);
        }
    }

    PL1204Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
