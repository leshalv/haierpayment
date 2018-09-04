package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 客户标签表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:30
 */
@Entity
@Getter
@Setter
@Table(name = "PL_CASH_CUST_SIGN")
public class CashCustSign extends BaseModel {

    /**消金产品表主键ID**/
    private String productId;
    /**合作项目表主键ID**/
    private String projectId;
    /**客户标签**/
    private String cashCustSign;
    /**客户标签名称**/
    private String cashCustSignName;

}
