package com.haiercash.pluslink.capital.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 路由结果记录表实体类
 * @author WDY
 * @date 20180713
 * @rmk
 */
@Entity
@Getter
@Setter
@Table(name = "PL_ROUTE_RESULT_RECORD")
public class RouteResultRecord{

    /**主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)**/
    private String id;
    /**业务编号:申请流水号,幂等唯一**/
    private String applSeq;
    /**消金客户编号**/
    private String custId;
    /**身份证号**/
    private String custIdNo;
    /**客户标签**/
    private String custSign;
    /**消金产品编号**/
    private String typCde;
    /**消金渠道编号**/
    private String channelNo;
    /**消金产品期数**/
    private Long period;
    /**合作机构表主键ID**/
    private String agencyId;
    /**合作项目表主键ID**/
    private String projectId;
    /**还款银行代码**/
    private String bankNoNum;
    /**放款金额**/
    private BigDecimal loanAmount;
    /**路由规则ID:可空(路由中心筛选规则改变)**/
    private String routeRuleId;
    /**合作机构资金占比**/
    private BigDecimal agencyRatio;
    /**消金放款金额**/
    private BigDecimal cashLoanAmount;
    /**合作机构计划放款金额**/
    private BigDecimal angencyPlanLoanAmount;
    /**合作机构放款金额**/
    private BigDecimal agencyLoanAmount;
    /**合作机构已放款金额**/
    private BigDecimal agencyAlreadyAmount;
    /**合作机构剩余放款金额**/
    private BigDecimal agencyLeftAmount;
    /**客户额度**/
    private BigDecimal custLimit;
    /**客户年龄**/
    private Integer custAge;
    /**客户性别**/
    private String custSex;
    /**客户首次用信距今天数**/
    private Integer custLastLoanDay;
    /**是否需要征信**/
    private String isCredit;
    /**合作机构征信模板路径**/
    private String creditModleUrl;
    /**是否是联合放款**/
    private String isUniteLoan;
    /**报文流水号**/
    private String serNo;
    /**系统标识**/
    private String sysFlag;
    /**创建时间**/
    private Date createDate;
    /**备注**/
    private String rmk;
    /**执行机地址**/
    private String excuteIP;
    /**执行机主机名称**/
    private String excuteHostName;
}
