package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**
 * 放款状态(PL0506)
 *
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0506Enum {

    /*********************放款端（处理中心）放款状态码定义，目前放款请求和放款查询状态一样，公用**************************/
    PL0506_1_10("10", "已受理", "LOAN_ADMISSIBLE"),
    PL0506_2_20("20", "网关放款中", "PROCESSING"),
    PL0506_3_30("30", "网关放款成功", "SUCCESS"),
    PL0506_4_40("40", "联合处理中", "LOAN_UNION_HANDING"),
    PL0506_5_50("50", "放款成功", "LOAN_SUCCESS"),
    PL0506_6_60("60", "放款失败", "FAIL"),
    PL0506_7_70("70", "已作废", "LOAN_INVALID");

    private String code;
    private String desc;
    //支付网关返回状态标识枚举
    private String paymentReturnStatus;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0506Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0506Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0506Enum.class);
        }
    }

    /**
     * 根据支付网关返回状态得到消费金融的放款状态枚举
     *
     * @param paymentReturnStatus 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0506Enum getEnumByPayMentReturnStatus(String paymentReturnStatus) {
        if (null == paymentReturnStatus) {
            return null;
        } else {
            for (PL0506Enum codeEnum : values()) {
                if (codeEnum.paymentReturnStatus.equals(paymentReturnStatus)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No code paymentReturnStatus '" + paymentReturnStatus + "'. " + PL0506Enum.class);
        }
    }

    PL0506Enum(String code, String desc, String paymentReturnStatus) {
        this.code = code;
        this.desc = desc;
        this.paymentReturnStatus = paymentReturnStatus;
    }
}
