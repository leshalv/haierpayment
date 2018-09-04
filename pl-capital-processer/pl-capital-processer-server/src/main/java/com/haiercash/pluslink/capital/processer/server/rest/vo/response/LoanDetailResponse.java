package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaobin
 * @create 2018-07-16 下午1:17
 **/
@Getter
@Setter
@ApiModel("返回放款详细信息")
public class LoanDetailResponse implements Serializable {

    @ApiModelProperty("性别")
    private String apptIndivSex;

    @ApiModelProperty("出生日期")
    private String apptStartDate;

    @ApiModelProperty("职务")
    private String indivPosition;

    @ApiModelProperty("单位性质")
    private String indivEmpTyp;

    @ApiModelProperty("个人年收入")
    private BigDecimal annualEarn;

    @ApiModelProperty("教育程度")
    private String indivEdu;

    @ApiModelProperty("婚姻")
    private String indivMarital;

    @ApiModelProperty("家庭地址")
    private String liveAddr;

    @ApiModelProperty("单位详情地址")
    private String empAddr;

    @ApiModelProperty("联系人手机")
    private String relMobile;

    @ApiModelProperty("联系人姓名 ")
    private String relName;

    @ApiModelProperty("申请人ip")
    private String addrIp;

    @ApiModelProperty("省")
    private String liveProvince;

    @ApiModelProperty("市")
    private String liveCity;

    @ApiModelProperty("区")
    private String liveArea;

    @ApiModelProperty("邮政编码")
    private String indivEmpZip;

    @ApiModelProperty("分期期数")
    private String applyTnr;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("申请日期")
    private  String applyDt;
}
