package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 合作项目期限表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:19
 */
@Entity
@Getter
@Setter
@Table(name = "PL_COOPERATION_PERIOD")
public class CooperationPeriod extends BaseModel {

    private String projectId;
    private Long cooperationPeriodValue;
    private String cooperationPeriodType;
}
