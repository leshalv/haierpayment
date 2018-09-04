package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 合作机构需求资料表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:05
 */
@Entity
@Getter
@Setter
@Table(name = "PL_AGENCY_DEMAND_INFO")
public class AgencyDemandInfo extends BaseModel {

    private String agencyId;
    private String demandType;
}
