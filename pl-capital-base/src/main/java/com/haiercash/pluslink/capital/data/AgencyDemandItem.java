package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 合作机构需求资料明细表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:08
 */
@Entity
@Getter
@Setter
@Table(name = "PL_AGENCY_DEMAND_ITEM")
public class AgencyDemandItem extends BaseModel {

    private String demandId;
    private String materialType;
    private String materialName;
    private String materialValue;
}
