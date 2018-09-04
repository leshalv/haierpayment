package com.haiercash.pluslink.capital.router.rest.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 用户是否30天前贷款查询接口(出参参数)
 * @author WDY
 * @date 2018-07-25
 * @rmk
 */
@Getter
@Setter
@ApiModel("上次用信距今天数回调出参")
public class LastLoanQueryResponse implements Serializable{

    @ApiModelProperty("返回码")
    @NotBlank(message = "返回码不能为空")
    private String rtnCode;
    @ApiModelProperty("返回描述")
    private String rtnMsg;
    @ApiModelProperty("贷款结果")
    private String loanResult;
}
