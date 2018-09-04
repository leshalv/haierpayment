package com.haiercash.pluslink.capital.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 头寸拆分逻辑(入参)
 * @author WDY
 * @date 2018-07-16
 */
@Getter
@Setter
public class PositionSplitIn implements Serializable{

    /**业务编号**/
    private String applSeq;
    /**放款金额**/
    private BigDecimal origPrcp;
    /**资方占比**/
    private BigDecimal agencyRatio;
    /**合作机构ID**/
    private String agencyId;
    /**合作项目ID**/
    private String projectId;
    /**合作项目放款总额**/
    private BigDecimal loanAmount;
    /**合作项目已放款额**/
    private BigDecimal alreadyLoanAmount;
    /**合作项目限额(日)**/
    private BigDecimal loanLimitDay;
    /**合作项目当日放已放款额**/
    private BigDecimal alreadyLoanLimitDay;
    /**合作项目限额(月)**/
    private BigDecimal loanLimitMonth;
    /**合作项目当月已放款额**/
    private BigDecimal alreadyLoanLimitMonth;
}
