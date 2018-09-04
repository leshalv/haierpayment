package com.haiercash.pluslink.capital.processer.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: LoanAssetsSplitItemResponse 放款查询资产拆分信息
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1715:50
 */
@Getter
@Setter
@ApiModel("返回查询放款明细报文体")
public class LoanAssetsSplitItemResponse implements Serializable {
    @ApiModelProperty("借据号")
    private String loanNo;

    @ApiModelProperty("借据类型")
    private String loanType;

    @ApiModelProperty("合作项目Id")
    private String projectId;

    @ApiModelProperty("资方放款占比")
    private BigDecimal agencyRate;

    @ApiModelProperty("申请金额")
    private BigDecimal applAmt;

    @ApiModelProperty("放款金额")
    private BigDecimal transAmt;

    @ApiModelProperty("明细状态")
    private String status;

    @ApiModelProperty("资方编号")
    private String agencyId;
    
    @ApiModelProperty("资方业务编号")
    private String capLoanNo;
}
