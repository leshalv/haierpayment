package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: PayMentGateWayLoanResponse 支付网关放款接口返回实体
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1910:12
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayMentGateWayLoanResponse implements Serializable {

    //交易状态
    private String tradeStatus;

    //错误码
    private String errorCode;

    //错误描述
    private String errorDesc;

    //凭证号
    private String elecChequeNo;

    //银行交易流水
    private String bankOrderNo;

}
