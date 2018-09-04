package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @Description: 通知支付网关放款请求体
 * @date 2018/7/1415:44
 */
@Setter
@Getter
public class PayMentGateWayLoanRequest implements Serializable {
    // **************枚举类型参考支付网关接口文档************
    //支付工具 枚举类型PITypeEnum
    private String piType;

    // 机构标识
    private String merchantNo;

    //机构系统版本号 这个文档和报文对应不一致
    @JsonProperty("institutionVersion")
    private String institutionVersion = "V1";

    //业务类型  枚举类型BizTypeEnum
    private String bizType;

    //细分业务类型
    private String subBizType;

    // 是否受控 枚举类型 YesNoEnum
    private String isControl;

    //受控类型 枚举类型 ControlTypeEnum
    private String controlType;

    //受控条件
    private String controlValue;

    //发起账户号
    private String launchAccount;

    //凭证号
    private String elecChequeNo;

    //请求时间
    private String requestTime;

    //交易金额
    private BigDecimal tradeAmount;

    //币种 枚举类型 CurrencyEnum
    private String currency;

    //到账模式  枚举类型ArrivalModeEnum
    private String arrivalMode;

    //到账时间
    private Date bespeakDate;

    //业务流水号
    private String businessPayNo;

    //贷款本金
    private BigDecimal origPrcp;

    //应交易日期
    private Date shouldTransTime;

    //合作机构CRMID
    private String coaCrmID;

    //产品代码
    private String subBusinessType;

    //持卡人卡号
    private String payeeCardNo;

    //持卡人姓名
    private String payeeName;

    //客户类型 ：枚举类型 CRMTypeEnum
    private String crmType;

    //客户ID
    private String crmNo;

    //证件类型
    private String certType;

    //证件号
    @JsonProperty("certNO")
    private String certNo;

    //手机号
    private BigDecimal mobile;

    //银行代码(数字)
    private String bankCode;

    //银行卡类型 枚举类CardTypeEnum
    private String bankCardType;

    //银行联行号
    private String bankUnionCode;

    //交易属性(对公or对私) 枚举类  AccountNatureEnum
    private String channelNature;

    //接口ID
    private String interId;

    //分账规则ID
    private String tradeSplitId;

    //备注
    private String remark;

    //请求IP
    private String requestIp;

    //开户行所在省
    private String openingBankProvince;

    //开户行所在市
    private String openingBankCity;

    //是否分账 枚举类型  YesNoEnum
    private String splitTag;

    //是否附后置余额支付请求 枚举类型  YesNoEnum
    private String balancePayTag;

    //交易总金额
    private BigDecimal totalAmount;

    //分账请求实体
    private List<PayMentSplitTransUnitRequest> splitTransUnitRequest;

    //余额支付实体
    private List<PayMentBalancePayRequestInfo> balancePayRequest;

    //财务公司付款记账信息
    private PayMentFinancePayRequest financePayRequest;
}
