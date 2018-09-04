package com.haiercash.pluslink.capital.processer.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: LoanFinancePayRequest
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1210:54
 */
@Getter
@Setter
@ApiModel("财务公司付款记账信息")
public class LoanFinancePayRequest implements Serializable {

    @ApiModelProperty("是否有工贸账号")
    @NotBlank(message = "是否有工贸账号不能为空")
    private String isHasMiddAcct;

    @ApiModelProperty("转入账号")
    @NotBlank(message = "转入账号不能为空")
    private String inActno;

    @ApiModelProperty("转入户名")
    @NotBlank(message = "转入户名不能为空")
    private String inActname;

    @ApiModelProperty("再转入账号")
    private String inActno2;

    @ApiModelProperty("再转入户名")
    private String inActname2;

    @ApiModelProperty("公司代码(收款方)")
    @NotBlank(message = "公司代码(收款方)不能为空")
    private String indCommpCode;

    @ApiModelProperty("客户编码付款方编码")
    @NotBlank(message = "客户编码付款方编码不能为空")
    private String payCode;

    @ApiModelProperty("借款人姓名")
    @NotBlank(message = "借款人姓名不能为空")
    private String jkrName;

    @ApiModelProperty("记账方式")
    @NotBlank(message = "记账方式不能为空")
    private String actChannel;
}
