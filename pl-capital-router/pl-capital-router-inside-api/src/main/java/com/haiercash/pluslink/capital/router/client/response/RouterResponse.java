package com.haiercash.pluslink.capital.router.client.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 路由中心处理接口(出参参数)
 * @author WDY
 * @date 2018-07-12
 * @rmk
 */
@Getter
@Setter
@ApiModel("路由中心处理接口(返回)")
public class RouterResponse implements Serializable {

    @ApiModelProperty("业务编号")
    @NotBlank(message = "业务编号不能为空")
    private String applSeq;

    @ApiModelProperty("合作机构编码")
    private String agencyId;

    @ApiModelProperty("合作机构名称")
    private String agencyIdName;

    @ApiModelProperty("合作项目编码")
    private String projectId;

    @ApiModelProperty("合作项目名称")
    private String projectName;

    @ApiModelProperty("是否联合放款")
    @NotBlank(message = "是否需要征信不能为空")
    private String isUniteLoan;

    @ApiModelProperty("是否需要征信")
    @NotBlank(message = "是否需要征信不能为空")
    private String isCredit;

    @ApiModelProperty("合作机构征信模板路径")
    private String creditModleUrl;
}
