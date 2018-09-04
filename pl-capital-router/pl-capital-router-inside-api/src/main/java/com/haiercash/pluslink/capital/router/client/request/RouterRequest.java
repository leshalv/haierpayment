package com.haiercash.pluslink.capital.router.client.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 路由中心处理接口(入参)
 * @author WDY
 * @date 2018-07-12
 * @rmk
 */
@Getter
@Setter
@ApiModel("路由中心处理接口(接收)")
public class RouterRequest implements Serializable{

    @ApiModelProperty("业务编号")
    @NotBlank(message = "业务编号不能为空")
    private String applSeq;

    @ApiModelProperty("消金客户编号")
    @NotBlank(message = "消金客户编号不能为空")
    private String custId;

    @ApiModelProperty("消金客户身份证号")
    @NotBlank(message = "消金客户身份证号不能为空")
    private String idNo;

    @ApiModelProperty("消金产品编号")
    @NotBlank(message = "消金产品编号不能为空")
    private String typCde;

    @ApiModelProperty("消金客户标签")
    @NotBlank(message = "消金客户标签不能为空")
    private String custSign;

    @ApiModelProperty("消金渠道编号")
    @NotBlank(message = "消金渠道编号不能为空")
    private String channelNo;

    @ApiModelProperty("期数")
    @NotNull(message = "期数")
    private String period;

    @ApiModelProperty("期数类型")
    @NotNull(message = "期数类型")
    private String periodType;

    @ApiModelProperty("还款卡银行数字编码")
    @NotBlank(message = "还款卡银行数字编码不能为空")
    private String repayCardBankNo;

    @ApiModelProperty("放款金额")
    @NotNull(message = "放款金额")
    private String origPrcp;

    @ApiModelProperty("客户额度")
    private String custLimit;

    @ApiModelProperty("客户年龄")
    private String custAge;//客户年龄

    @ApiModelProperty("客户性别")
    /**0-男,1-女**/
    private String custSex;
}
