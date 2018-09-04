package com.haiercash.pluslink.capital.router.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 资金方综合信息查询接口(入参参数)
 * @author WDY
 * @date 2018-07-13
 * @rmk
 */
@Getter
@Setter
@ApiModel("资金方综合信息查询接口(接收)")
public class GeneralInfoRequest implements Serializable{

    @ApiModelProperty("合作机构主键ID")
    @NotBlank(message = "合作机构主键ID不能为空")
    private String agencyId;

    @ApiModelProperty("合作项目主键ID")
    @NotBlank(message = "合作项目主键ID不能为空")
    private String projectId;
}
