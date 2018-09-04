package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: AssetsSplit
 * @ProjectName pl-capital
 * @Description: 资产表实体
 * @date 2018/7/1311:01
 */
@Entity
@Getter
@Setter
@Table(name = "PL_ASSETS_SPLIT")
public class AssetsSplit extends BaseModel {

    //消金业务编号
    private String applSeq;
    //客户姓名
    private String custName;
    //自主借据号
    private String loanNo1;
    //资方借据号
    private String loanNo2;
    //证件号码
    private String certCode;
    //手机号
    private BigDecimal mobile;
    //交易金额（斩头后）
    private BigDecimal tradeAmount;
    //币种
    private String currency;
    //合同号
    private String contractNo;
    //贷款本金
    private BigDecimal origPrcp;
    //产品代码
    private String subBusinessType;
    //放款账户
    private String payeeCardNo;
    //放款户名
    private String payeeName;
    //客户类型
    private String crmType;
    //客户ID
    private String crmNo;
    //证件类型
    private String certType;
    //证件号
    private String certNo;
    //银行代码(数字)
    private String bankCode;
    //银行卡类型
    private String bankCardType;
    //银行联行号
    private String bankUnionCode;
    //交易属性(对公OR对私)
    private String channelNature;
    //放款渠道
    private String interId;
    //开户行所在省
    private String openingBankProvince;
    //开户行所在市
    private String openingBankCity;
    //是否附后置余额支付请求
    private String balancePayTag;
    //交易总金额
    private BigDecimal totalAmount;
    //渠道号
    private  String channelNo;
    /**
     * 是否联合放款 PL0505Enum
     */
    private String projectType;
    //产品是否支持买断
    private String prodBuyOut;
    //放款状态
    private String loanStatus;
    /* -------------财务公司付款记账信息----------------*/
    //是否有工贸账号
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
    //记账方式
    private String actChannel;
    //请求ip
    private String requestIp;
    //客户编号
    private String crmNum;


}
