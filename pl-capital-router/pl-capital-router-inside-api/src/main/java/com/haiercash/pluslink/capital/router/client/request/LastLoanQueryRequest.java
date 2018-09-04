package com.haiercash.pluslink.capital.router.client.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户是否30天前贷款查询接口(入参参数)
 * @author WDY
 * @date 2018-07-25
 * @rmk
 */
@Getter
@Setter
@ApiModel("上次用信距今天数回调入参")
public class LastLoanQueryRequest {

    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String idNo;
    @ApiModelProperty("贷款品种代码")
    @NotBlank(message = "贷款品种代码不能为空")
    private String loanTyp;
    @ApiModelProperty("操作人")
    private String operator;
}
