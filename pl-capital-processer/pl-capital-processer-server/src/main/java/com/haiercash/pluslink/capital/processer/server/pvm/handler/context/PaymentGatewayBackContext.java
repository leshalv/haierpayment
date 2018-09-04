package com.haiercash.pluslink.capital.processer.server.pvm.handler.context;

import com.haiercash.pluslink.capital.data.AssetsSplit;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付网关回调上下文
 *
 * @author xiaobin
 * @create 2018-08-03 上午11:25
 **/
@Getter
@Setter
public class PaymentGatewayBackContext {

    /**
     * 资产详情
     */
    private AssetsSplit assetsSplit;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 凭证号
     */
    private String elecChequeNo;

    /**
     * 银行交易流水
     */
    private String bankOrderNo;

    public PaymentGatewayBackContext(AssetsSplit assetsSplit, String tradeStatus, String elecChequeNo, String bankOrderNo) {
        this.assetsSplit = assetsSplit;
        this.elecChequeNo = elecChequeNo;
        this.tradeStatus = tradeStatus;
        this.bankOrderNo = bankOrderNo;
    }
}
