package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**交易类型(PL1205)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1205Enum {

    PL1205_1_CARD_COLLECTION("CARD_COLLECTION", "还款"),
    PL1205_2_ACT_COLLECTION("ACT_COLLECTION", "余额还款"),
    PL1205_3_ACT_310_COLLECTION("ACT_310_COLLECTION", "310溢缴款还款"),
    PL1205_4_CARD_TRANSFER("CARD_TRANSFER", "放款"),
    PL1205_5_RTN_TRANSFER("RTN_TRANSFER", "放款撤销"),
    PL1205_6_ACT_TRANSFER("ACT_TRANSFER", "放款至余额"),
    PL1205_7_RTN_FEE_TRANSFER("RTN_FEE_TRANSFER", "退货手续费"),
    PL1205_8_WITHDRAW("WITHDRAW", "未到账提现"),
    PL1205_9_SETTLEMENTWITHDRAW("SETTLEMENTWITHDRAW", "已到账提现"),
    PL1205_10_RECHARGE("RECHARGE", "未到账充值"),
    PL1205_11_ARRIVALRECHARGE("ARRIVALRECHARGE", "已到账充值"),
    PL1205_12_FINISHED_AUTO_COLLECTION("FINISHED_AUTO_COLLECTION", "已到账主动还款"),
    PL1205_13_COO_CARD_COLLECTION("COO_CARD_COLLECTION", "第三方银行卡代收"),
    PL1205_14_COO_CARD_TRANSFER("COO_CARD_TRANSFER", "第三方银行卡代付"),
    PL1205_15_COO_RTN_CARD_TRANSFER("COO_RTN_CARD_TRANSFER", "第三方银行卡代付撤销"),
    PL1205_16_COO_COMPENSATORY_TRANSFER("COO_COMPENSATORY_TRANSFER", "合作机构代偿款转账"),
    PL1205_17_PLR_CARD_TRANSFER("PLR_CARD_TRANSFER", "资金平台银行卡代付");
    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1205Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1205Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1205Enum.class);
        }
    }

    PL1205Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
