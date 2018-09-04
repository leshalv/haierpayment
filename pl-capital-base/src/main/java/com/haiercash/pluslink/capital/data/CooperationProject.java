package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import com.haiercash.pluslink.capital.enums.dictionary.PL0401Enum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * > 合作项目表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:11
 */
@Entity
@Getter
@Setter
@Table(name = "PL_COOPERATION_PROJECT")
public class CooperationProject extends BaseModel {

    public CooperationProject(){
        projectStatus = PL0401Enum.PL0401_2_OPEN.getCode();
    }

    /**
     * 合作机构表主键ID
     */
    private String agencyId;
    /**
     * 合作项目名称
     */
    private String projectName;
    /**
     * 合作模式
     */
    private String projectType;
    /**
     * 合作项目状态
     */
    private String projectStatus;
    /**
     * 合作项目是否担保
     */
    private String isAssure;
    /**
     * 合作项目担保机构
     */
    private String collateralId;
    /**
     * 项目生效时间
     */
    private Date effectTime;
    /**
     * 放款总量
     */
    private BigDecimal loanAmount;
    /**
     * 放款限额(月)
     */
    private BigDecimal loanLimitMonth;
    /**
     * 放款限额(日)
     */
    private BigDecimal loanLimitDay;
    /**
     * 优先级
     */
    private String projectPriority;
    /**
     * 消金收益率
     */
    private BigDecimal cashYieldRate;
    /**
     * 合作机构资金占比
     */
    private BigDecimal agencyRatio;
    /**
     * 合作机构收益率
     */
    private BigDecimal agencyYieldRate;
    /**
     * 授信有效期限
     */
    private String creditTerm;
    /**
     * 征信查询方式
     */
    private String creditWay;
    /**
     * 用户额度区间开始
     */
    private BigDecimal custLimitStart;
    /**
     * 用户额度区间结束
     */
    private BigDecimal custLimitEnd;
    /**
     * 用户贷款区间开始
     */
    private BigDecimal custLoanStart;
    /**
     * 用户贷款区间结束
     */
    private BigDecimal custLoanEnd;
    /**
     * 用户性别维度
     */
    private String custSexDimension;
    /**
     * 用户年龄区间开始
     */
    private String custAgeStart;
    /**
     * 用户年龄区间结束
     */
    private String custAgeEnd;
    /**
     * 用户首次用信距今天数
     */
    private String custFirstCreditAgo;
    /**
     * 业务每日暂停开始时间hhmi24ss
     */
    private String noBusiTimeStart;
    /**
     * 业务每日暂停结束时间hhmi24ss
     */
    private String noBusiTimeEnd;
    /**
     * 匹配规则
     */
    private String mateRule;
    /**
     * 代偿时间
     */
    private String compensatoryTime;
    /**
     * 期限服务费率
     */
    private BigDecimal termCharge;
}
