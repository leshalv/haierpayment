package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import com.haiercash.pluslink.capital.enums.dictionary.PL0201Enum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 合作机构表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 9:59
 */
@Entity
@Getter
@Setter
@Table(name = "PL_COOPERATION_AGENCY")
public class CooperationAgency extends BaseModel {

    public CooperationAgency(){
        cooperationStatus = PL0201Enum.PL0201_2_OPEN.getCode();
    }

    /**
     * 合作机构名称
     */
    private String agencyName;
    /**
     * 合作机构联系人
     */
    private String agencyLiaisons;
    /**
     * 合作机构联系人电话
     */
    private String agencyLiaisonsMobile;
    /**
     * 优先级
     */
    private String agencyPriority;
    /**
     * 合作状态
     */
    private String cooperationStatus;
    /**
     * 机构类型
     */
    private String agencyType;
}
