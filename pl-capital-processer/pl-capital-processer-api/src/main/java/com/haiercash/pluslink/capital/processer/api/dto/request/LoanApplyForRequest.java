package com.haiercash.pluslink.capital.processer.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaobin
 * @create 2018-07-05 下午7:01
 **/
@Getter
@Setter
@ApiModel("放款处理请求消息体")
public class LoanApplyForRequest implements Serializable {

    @ApiModelProperty("业务编号")
    @NotBlank(message = "业务编号不能为空")
    private String applSeq;

    @ApiModelProperty("客户姓名")
    @NotBlank(message = "客户姓名不能为空")
    private String custName;

    @ApiModelProperty("自主借据号")
    @NotBlank(message = "自主借据号不能为空")
    private String loanNo1;

    @ApiModelProperty("资方借据号")
    @NotBlank(message = "资方借据号不能为空")
    private String loanNo2;

    @ApiModelProperty("证件号码")
    @NotBlank(message = "证件号码不能为空")
    private String certCode;

    @ApiModelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    private BigDecimal mobile;

    @ApiModelProperty("交易金额（斩头后）")
    @NotNull(message = "交易金额（斩头后)不能为空")
    private BigDecimal tradeAmount;

    @ApiModelProperty("币种")
    @NotBlank(message = "币种不能为空")
    private String currency;

    @ApiModelProperty("合同号")
    @NotBlank(message = "合同号不能为空")
    private String contractNo;

    @ApiModelProperty("贷款本金")
    @NotNull(message = "贷款本金不能为空")
    private BigDecimal origPrcp;

    @ApiModelProperty("产品代码")
    @NotBlank(message = "产品代码不能为空")
    private String subBusinessType;

    @ApiModelProperty("放款账户")
    @NotBlank(message = "放款账户不能为空")
    private String payeeCardNo;

    @ApiModelProperty("放款户名")
    @NotBlank(message = "放款户名不能为空")
    private String payeeName;

    @ApiModelProperty("客户类型")
    @NotBlank(message = "客户类型不能为空")
    private String crmType;

    @ApiModelProperty("客户ID")
    @NotBlank(message = "客户ID不能为空")
    private String crmNo;

    @ApiModelProperty("证件类型")
    private String certType;

    @ApiModelProperty("证件号")
    private String certNo;

    @ApiModelProperty("银行代码(数字)")
    private String bankCode;

    @ApiModelProperty("银行卡类型")
    private String bankCardType;


    @ApiModelProperty("银行联行号")
    private String bankUnionCode;

    @ApiModelProperty("交易属性(对公or对私)")
    @NotBlank(message = "交易属性(对公or对私)不能为空")
    private String channelNature;

    @ApiModelProperty("放款渠道")
    private String interId;

    @ApiModelProperty("开户行所在省")
    private String openingBankProvince;

    @ApiModelProperty("开户行所在市")
    private String openingBankCity;

    @ApiModelProperty("是否附后置余额支付请求")
    @NotBlank(message = "是否附后置余额支付请求不能为空")
    private String balancePayTag;

    @ApiModelProperty("交易总金额")
    @NotNull(message = "交易总金额不能为空")
    private BigDecimal totalAmount;

    //余额支付信息列表
    @ApiModelProperty("余额支付信息列表")
    private List<LoanBalancePayRequestInfo> balancePayRequest;

    //财务公司付款记账信息
    @ApiModelProperty("财务公司付款记账信息")
    private LoanFinancePayRequest financePayRequest;


}
