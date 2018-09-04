package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 入账分录表
 * Created by hp on 2017/1/5.
 */
@Setter
@Getter
@Entity
@Table(name = "PL_ACCOUNT_ENTRY")
public class AccountEntry extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;
    /* TODO "assCheckType":"PAY_CHANNEL",　辅助类型  "assCheckCode":"9007" 辅助代码 是否传递*/
    @Id
    private String id;
    /**
     * 入账事务ID
     **/
    private String transId;
    /**
     * 账户ID
     **/
    private String accountId;
    /**
     * 账户类型
     **/
    private String accountType;
    /**
     * 是否受控 PL0109Enum
     **/
    private String isControl;
    /**
     * 受控类型 PL1301Enum
     **/
    private String controlType;
    /**
     * 受控条件
     **/
    private String controlValue;
    /**
     * 凭证号
     **/
    private String voucherNo;

    /**
     * 金额
     **/
    private BigDecimal amount;
    /**
     * 余额方向 PL1202Enum
     **/
    private String balanceDirection;
    /**
     * 是否冲正 PL0109Enum
     **/
    private String isCorrect;
    /**
     * 冻结流水号
     **/
    private String freezeId;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 摘要
     **/
    private String summary;
    /**
     * 交易时间
     **/
    private Date tradeFinishTime;
    /**
     * 辅助类型 PL1203Enum
     */
    private String assCheckType;
    /**
     * 辅助代码
     */
    private String assCheckCode;

    public String toString() {
        return JSON.toJSONString(this);
    }
}
