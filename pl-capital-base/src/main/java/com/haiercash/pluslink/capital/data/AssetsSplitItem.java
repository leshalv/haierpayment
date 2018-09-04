package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author yu jianwei
 * @date 2018/7/12 17:36
 * PL_ASSETS_SPLIT_ITEM 表实体类
 */
@Entity
@Getter
@Setter
@Table(name = "PL_ASSETS_SPLIT_ITEM")
public class AssetsSplitItem extends BaseModel {
    /**
     * 资产表主键id
     */
    private String assetsSplitId;
    /**
     * 放款金额
     */
    private BigDecimal transAmt;
    /**
     * 状态
     */
    private String status;
    /**
     * 借据号
     */
    private String loanNo;
    /**
     * 放款占比
     */
    private BigDecimal agencyRate;
    /**
     * 合作机构主键id
     */
    private String agencyId;
    /**
     * 合作项目主键id
     */
    private String projectId;
    /**
     * 资方业务编号
     */
    private String capLoanNo;
    /**
     * 备注
     */
    private String memo;
    /**
     * 借款类型
     */
    private String loanType;
    /**
     * 申请金额
     */
    private BigDecimal applAmt;
}
