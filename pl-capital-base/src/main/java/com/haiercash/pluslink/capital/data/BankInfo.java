package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 银行信息表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:16
 */
@Entity
@Getter
@Setter
@Table(name = "PL_BANK_INFO")
public class BankInfo extends BaseModel {

    private String bankName;
    private String bankNoEn;
    private String bankNoNum;
}
