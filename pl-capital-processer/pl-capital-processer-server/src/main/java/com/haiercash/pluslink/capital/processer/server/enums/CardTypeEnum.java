package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @Title: 支付网关银行卡类型枚举
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1410:50
 */
@Getter
public enum CardTypeEnum {


    /**
     * 借记卡
     */
    DEBIT_CARD("DC", "借记卡"),
    /**
     * 信用卡
     */
    CREDIT_CARD("CC", "信用卡"),
    /**
     * 混合卡
     */
    MIX_CARD("XC", "混合卡"),
    /**
     * 准贷记卡
     */
    SEMI_CREDIT_CARD("SC", "准贷"),
    /**
     * 预付费卡
     */
    PREPAID_CARD("PC", "预付费"),
    /**
     * 银行账户
     */
    BANK_ACCOUNT("BC", "银行账户");


    private String code;
    private String desc;

    /**
     * 支付网关银行卡类型枚举.
     *
     * @param properties 枚举定义的编码
     * @return 查找到的枚举
     */
    public static boolean getEnumByPro(String properties) {
        try {
            for (CardTypeEnum codeEnum : values()) {
                if (codeEnum.toString().equals(properties)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    CardTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
