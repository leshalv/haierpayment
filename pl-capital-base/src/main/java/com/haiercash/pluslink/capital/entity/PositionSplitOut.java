package com.haiercash.pluslink.capital.entity;

import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 头寸拆分逻辑(出参)
 * @author keliang.jiang
 * @date 2018/02/01
 */
@Getter
@Setter
public class PositionSplitOut implements Serializable{

    /**消金放款额**/
    private BigDecimal cashLoanAmount;
    /**合作方放款额**/
    private BigDecimal agencyLoanAmount;
    /**消金资金占比**/
    private BigDecimal cashRatio;
    /**返回状态**/
    private PL0601Enum status;
    /**返回信息**/
    private String message;
    /**返回记录标识**/
    private String flagMessage;
}
