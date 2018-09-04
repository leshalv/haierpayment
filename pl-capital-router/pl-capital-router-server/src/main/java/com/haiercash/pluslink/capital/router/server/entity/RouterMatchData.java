package com.haiercash.pluslink.capital.router.server.entity;

import com.haiercash.pluslink.capital.data.AgencyDemandItem;
import com.haiercash.pluslink.capital.data.CooperationAgency;
import com.haiercash.pluslink.capital.data.CooperationPeriod;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.entity.PositionSplitOut;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 匹配规则结果数据
 * @author WDY
 * @date 2018-07-18
 * @rmk
 */
@Data
@Getter
@Setter
public class RouterMatchData implements Serializable{

    /**合作机构**/
    private CooperationAgency agency;
    /**合作项目**/
    private CooperationProject project;
    /**合作方资金判断**/
    private PositionSplitIn positionSplitIn;
    /**合作机构放款金额**/
    private PositionSplitOut positionSplitOut;
    /**合作项目支持银行Map<银行英文编码,银行名称>**/
    private Map<String,String> supportBank;
    /**合作期限Map<期限类型,Map<期限(Long),CooperationPeriod>>**/
    private Map<String,Map<Long,CooperationPeriod>> periodMap;
    /**合作机构需求资料Map<资料类型,Map<明细资料类型,合作资料明细>>**/
    private Map<String,Map<String,AgencyDemandItem>>  agencyDemandItemMap;
    /**参数类型P-产品,C-渠道,S-标签**/
    private String paramType;
    /**参数值**/
    private String paramValue;
}
