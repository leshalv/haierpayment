package com.haiercash.pluslink.capital.router.server.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 匹配规则结果
 * @author WDY
 * @date 2018-07-18
 * @rmk
 */
@Data
@Getter
@Setter
public class RouterMatchIn implements Serializable{

    /**报文流水号**/
    private String serNo;
    /**系统标识**/
    private String sysFlag;
    /**业务编号**/
    private String applSeq;
    /**消金客户编号**/
    private String custId;
    /**消金客户身份证号**/
    private String idNo;
    /**消金产品编号**/
    private String typCde;
    /**消金客户标签**/
    private String custSign;
    /**消金渠道编号**/
    private String channelNo;
    /**期数**/
    private Long period;
    /**期数类型**/
    private String periodType;
    /**还款卡银行数字编码**/
    private String repayCardBankNo;
    /**放款金额**/
    private BigDecimal origPrcp;
    /**客户额度**/
    private BigDecimal custLimit;
    /**客户年龄**/
    private Integer custAge;
    /**客户性别**/
    private String custSex;
}
