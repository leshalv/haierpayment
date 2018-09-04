package com.haiercash.pluslink.capital.router.api.dto.response.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

/**
 * 资金方综合信息查询接口循环(还款方式入参参数)
 * @author WDY
 * @date 2018-07-13
 * @rmk
 */
@Getter
@Setter
@ApiModel("资金方综合信息查询接口循环(还款方式返回)")
public class RepaymentInfoResponse implements Serializable{

    @ApiModelProperty("还款方式编号")
    @NotBlank(message = "还款方式编号不能为空")
    private String repaymentTypeId;
}
