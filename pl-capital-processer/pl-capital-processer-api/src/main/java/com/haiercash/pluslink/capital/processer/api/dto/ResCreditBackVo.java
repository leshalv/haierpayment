package com.haiercash.pluslink.capital.processer.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 授信查询 返回
 *
 * @author xiaobin
 * @create 2018-07-19 上午10:35
 **/
@Getter
@Setter
@ApiModel("授信申请回调请求体")
public class ResCreditBackVo implements Serializable {

    @ApiModelProperty("资方客户协议号")
    private String cinoMemno;

    @ApiModelProperty("消金客户ID")
    private String cooprUserId;

    @ApiModelProperty("授信币种")
    private String currtype;

    @ApiModelProperty("授信金额")
    private BigDecimal credAmt;

    @ApiModelProperty("当前可贷金额")
    private BigDecimal valffamt;

    @ApiModelProperty("协议利率")
    private BigDecimal activateRate;

    @ApiModelProperty("协议起始日期")
    private String actdate;

    @ApiModelProperty("协议截至日期")
    private String deadline;

    @ApiModelProperty("协议状态")
    private String memnoStatus;

    @ApiModelProperty("消息ID")
    private String corpMsgId;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField1;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField2;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField3;
}
