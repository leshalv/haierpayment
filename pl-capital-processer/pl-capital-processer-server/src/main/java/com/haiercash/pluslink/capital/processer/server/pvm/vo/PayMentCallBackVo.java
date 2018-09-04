package com.haiercash.pluslink.capital.processer.server.pvm.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * @author lzh
 * @create 2018-07-16 下午1:17
 * 支付回调接口mq推送vo
 **/
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
//放款结果mq推送vo
public class PayMentCallBackVo implements Serializable {

    //交易状态
    private String tradeStatus;

    //错误码
    private String errorCode;

    //错误描述
    private String errorDesc;

    //凭证号,支付请求号
    private String elecChequeNo;

    //银行交易流水
    private String bankOrderNo;

}
