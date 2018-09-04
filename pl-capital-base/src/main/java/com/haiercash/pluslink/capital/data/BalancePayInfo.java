package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: BalancePayInfo
 * @ProjectName pl-capital
 * @Description: 支付余额信息表实体
 * @date 2018/7/1314:38
 */
@Entity
@Getter
@Setter
@Table(name = "PL_BALANCE_PAY_INFO")
public class BalancePayInfo extends BaseModel {
    //资产表主键ID
    private String assetsSplitId;
    //客户类型
    private String crmType;
    //客户名称
    private String custName;
    //客户证件号
    private String certNo;
    //收款账户类型
    private String vaType;
    //付款金额
    private BigDecimal payAmt;
    //客户编号
    private String crmNo;
}
