package com.haiercash.pluslink.capital.router.api.dto.response.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

/**
 * 资金方综合信息查询接口循环(支持期限入参参数)
 * @author WDY
 * @date 2018-07-13
 * @rmk
 */
@Getter
@Setter
@ApiModel("资金方综合信息查询接口循环(支持期限返回)")
public class CooperationPeriodResponse implements Serializable{

    @ApiModelProperty("期数")
    @NotBlank(message = "期数不能为空")
    private Long period;

    @ApiModelProperty("期数类型")
    @NotBlank(message = "期数类型不能为空")
    private String periodType;

}
