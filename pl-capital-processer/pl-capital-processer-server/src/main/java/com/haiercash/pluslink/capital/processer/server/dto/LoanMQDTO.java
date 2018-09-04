package com.haiercash.pluslink.capital.processer.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: LoanMQ
 * @ProjectName pl-capital
 * @Description: MQ消息实体
 * @date 2018/7/1217:32
 */
@Setter
@Getter
public class LoanMQDTO implements Serializable {
    //资产表主键id
    private  String id;
    //业务编号
    private String businessNo;
    //交易总金额
    private BigDecimal totalAmount;
    //自主借据号
    private  String  loanNo1;
    //资方借据号
    private String loanNo2;

}
