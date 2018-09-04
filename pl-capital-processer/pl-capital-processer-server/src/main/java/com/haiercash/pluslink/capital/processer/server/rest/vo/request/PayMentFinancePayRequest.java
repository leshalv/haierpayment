package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: PayMentFinancePayRequest支付网关财务公司付款记账信息
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1210:54
 */
@Getter
@Setter
public class PayMentFinancePayRequest implements Serializable {

    // "是否有工贸账号不能为空  枚举类型YesNoEnum
    private String isHasMiddAcct;

    //转入账号
    private String inActno;

    //转入户名
    private String inActname;

    //再转入账号
    private String inActno2;

    //再转入户名
    private String inActname2;

    //公司代码(收款方)
    private String indCommpCode;

    //客户编码付款方编码
    private String payCode;

    //借款人姓名
    private String jkrName;

    //记账方式  枚举类型ActChannelEnum
    private String actChannel;
}
