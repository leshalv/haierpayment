package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lzh
 * @Title: PayMentGateWayLoanRequest
 * @ProjectName pl-capital
 * @Description: 查询支付网关放款信息
 * @date 2018/7/1415:44
 */
@Setter
@Getter
public class PayMentGateWaySearchRequest implements Serializable {

    //支付工具 枚举类型PITypeEnum
    private String piType;

    //支付请求号
    private String elecChequeNo;

    //用途
    private String use;

    //业务流水号
    private String businessPayNo;

}
