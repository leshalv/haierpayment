package com.haiercash.pluslink.capital.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 查询机构项目已放款头寸实体(出参)
 * @author WDY
 * @date 2018-07-19
 * @rmk
 */
@Data
@Getter
@Setter
public class AlreadyPositionOut implements Serializable{

    /**合作项目已放款额**/
    private BigDecimal alreadyLoanAmount;
    /**合作项目当日放已放款额**/
    private BigDecimal alreadyLoanLimitDay;
    /**合作项目当月已放款额**/
    private BigDecimal alreadyLoanLimitMonth;
}
