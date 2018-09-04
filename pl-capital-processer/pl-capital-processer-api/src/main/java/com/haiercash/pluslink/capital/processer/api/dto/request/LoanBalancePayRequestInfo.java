package com.haiercash.pluslink.capital.processer.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: LoanBalancePayRequestInfo
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1210:53
 */
@Getter
@Setter
@ApiModel("余额支付信息列表")
public class LoanBalancePayRequestInfo implements Serializable {

    @ApiModelProperty("客户类型")
    @NotBlank(message = "客户类型不能为空")
    private String crmType;

    @ApiModelProperty("客户名称")
    @NotBlank(message = "客户名称不能为空")
    private String custName;

    @ApiModelProperty("客户证件号")
    private String certNo;

    @ApiModelProperty("收款账户类型")
    @NotBlank(message = "收款账户类型不能为空")
    private String vaType;

    @ApiModelProperty("付款金额")
    @NotNull(message = "付款金额不能为空")
    private BigDecimal payAmt;

    @ApiModelProperty("客户编号")
    private String crmNo;
}
