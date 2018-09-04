package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 消金产品表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:27
 */
@Entity
@Getter
@Setter
@Table(name = "PL_CASH_PRODUCT")
public class CashProduct extends BaseModel {

    private String projectId;
    private String cashProductNo;
    private String cashProductName;
    private String annualizedRate;
}
