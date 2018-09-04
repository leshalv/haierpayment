package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 还款方式表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:23
 */
@Entity
@Getter
@Setter
@Table(name = "PL_REPAYMENT_INFO")
public class RepaymentInfo extends BaseModel {

    private String projectId;
    private String repaymentType;
}
