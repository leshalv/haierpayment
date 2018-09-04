package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 放款返回体
 *
 * @author xiaobin
 * @create 2018-07-25 上午10:31
 **/
@Getter
@Setter
public class ResMakeLoansResponse implements Serializable {

    /**
     * 合作方消息id
     */
    private String corpMsgId;

    /**
     * 原贷款业务序号
     */
    private String orgLoanBusino;
    /**
     * 资方贷款协议号
     */
    private String loanMemno;
    /**
     * 借据号
     */
    private String loanNum;
    /**
     * 实际放款金额
     */
    private BigDecimal actLoanAmt;
    /**
     * 实际放款利率
     */
    private BigDecimal actLoanRate;
    /**
     * 起息日
     */
    private String begDate;
    /**
     * 止息日
     */
    private String endDate;
    /**
     * 首期还款日
     */
    private String firstRetDate;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField1;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField2;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField3;
}
