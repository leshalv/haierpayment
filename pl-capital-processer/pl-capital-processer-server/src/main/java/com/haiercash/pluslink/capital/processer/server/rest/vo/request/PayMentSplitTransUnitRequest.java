package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: PayMentSplitTransUnitRequest
 * @ProjectName pl-capital
 * @Description: 支付网关分账时，放款分账请求体
 * @date 2018/7/1417:00
 */
@Setter
@Getter
public class PayMentSplitTransUnitRequest implements Serializable {
    //分账收款方客户编号
    private String splitCrmNo;

    //分账收款方客户类型 枚举类型CRMTypeEnum
    private String splitCrmType;

    //分账收款方虚拟账户号
    private String splitVaNo;

    //分账收款方虚拟账户类型
    private String splitVaType;

    //分账收款方客户证件号码
    private String splitCertNo;

    //分账收款方客户名称
    private String splitCustName;

    //是否受控 枚举 YesNoEnum
    private String isControl;

    //受控类型 枚举  ControlTypeEnum
    private String controlType;

    //受控条件
    private String controlValue;

    //分账金额
    private BigDecimal splitAmt;
    
    //分账备注
    private String splitRemark;
}
