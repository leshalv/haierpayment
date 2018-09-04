package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * @author lzh
 * @Title: LoanStatusEnum
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1410:50
 */
@Getter
public enum LoanStatusEnum {


    /*********************放款端（处理中心）放款状态码定义，目前放款请求和放款查询状态一样，公用**************************/
    //支付网关单笔返回状态只有 网关放款成功SUCCESS,网关放款中PROCESSING,放款失败FAIL,数据不存在NOTEXISTS四种
    LOAN_ADMISSIBLE("10", "已受理", "LOAN_ADMISSIBLE"),
    LOAN_INDEPENDENT_HANDING("20", "网关放款中", "PROCESSING"),
    LOAN_INDEPENDENT_SUCCESS("30", "网关放款成功", "SUCCESS"),
    LOAN_UNION_HANDING("40", "联合处理中", "LOAN_UNION_HANDING"),
    LOAN_SUCCESS("50", "放款成功", "LOAN_SUCCESS"),
    LOAN_FAILED("60", "放款失败", "FAIL"),
    LOAN_INVALID("70", "已作废", "LOAN_INVALID");

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
    public static LoanStatusEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (LoanStatusEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + LoanStatusEnum.class);
        }
    }

    /**
     * 根据支付网关返回状态得到消费金融的放款状态
     *
     * @param paymentReturnStatus 枚举定义的编码
     * @return 查找到的枚举
     */
    public static String getCodeByPayMentReturnStatus(String paymentReturnStatus) {
        if (null == paymentReturnStatus) {
            return null;
        } else {
            for (LoanStatusEnum codeEnum : values()) {
                if (codeEnum.paymentReturnStatus.equals(paymentReturnStatus)) {
                    return codeEnum.getCode();
                }
            }
            throw new IllegalArgumentException("No code paymentReturnStatus '" + paymentReturnStatus + "'. " + LoanStatusEnum.class);
        }
    }

    LoanStatusEnum(String code, String desc, String paymentReturnStatus) {
        this.code = code;
        this.desc = desc;
        this.paymentReturnStatus = paymentReturnStatus;
    }
}
