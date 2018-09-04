package com.haiercash.pluslink.capital.manager.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * > 机构类别明细
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/13 19:05
 */
@Getter
@Setter
@Entity
public class AgencyTypeDetail {
    /**
     * 合作机构编码
     */
    private String agencyId;
    /**
     * 合作机构名称
     */
    private String agencyName;
    /**
     * 合作项目数量
     */
    private String projectNum;
}
