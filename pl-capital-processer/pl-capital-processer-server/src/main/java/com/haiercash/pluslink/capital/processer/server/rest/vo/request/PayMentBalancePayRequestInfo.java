package com.haiercash.pluslink.capital.processer.server.rest.vo.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: PayMentBalancePayRequestInfo"支付网关余额支付信息列表")
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1210:53
 */
@Getter
@Setter
public class PayMentBalancePayRequestInfo implements Serializable {

    //客户类型 枚举类型CRMTypeEnum
    private String crmType;

    //客户名称
    private String custName;

    //客户证件号
    private String certNo;

    //收款账户类型
    private String vaType;

    //付款金额
    private BigDecimal payAmt;

    //客户编号
    private String crmNo;

}
