package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 入账事务表
 * Created by hp on 2017/1/4.
 */
@Setter
@Getter
@Entity
@Table(name = "PL_ACCOUNT_TRANSACTION")
public class AccountTransaction extends BaseModel implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private String id;
    /**
     * 报账事务号
     **/
    private String transNo;
    /**
     * 关联报账事务号
     **/
    private String contextTransNo;
    /**
     * 系统跟踪号
     **/
    private String orderId;
    /**
     * 业务类型 枚举类型 PL1205Enum
     **/
    private String bizType;
    /**
     * 交易环节
     **/
    private String tradeLink;
    /**
     * 应用ID PL1101Enum
     **/
    private String appId;
    /**
     * 录入人
     **/
    private String operatorId;
    /**
     * 业务单号
     **/
    private String bizId;
    /**
     * 是否为冲正 PL0109Enum
     **/
    private String isCorrect;
    /**
     * 请求时间
     **/
    private Date requestTime;
    /**
     * 是否退票 PL0109Enum
     */
    private String isBounced;
    /**
     * 入账状态 PL1102Enum
     */
    private String accountingStatus;
    /**
     * 客户类型
     */
    private String crmType;
    /**
     * 客户编号
     */
    private String crmNo;

    /**
     * 合同号
     */
    private String contractNo;

    public String toString() {
        return JSON.toJSONString(this);
    }
}
