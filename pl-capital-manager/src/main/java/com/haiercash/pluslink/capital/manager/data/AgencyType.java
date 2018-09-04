package com.haiercash.pluslink.capital.manager.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * > 机构类别配置
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 14:13
 */
@Getter
@Setter
@Entity
public class AgencyType {
    /**
     * 类别ID
     */
    @Id
    private String agencyTypeId;
    /**
     * 类别名称
     */
    private String agencyTypeName;
    /**
     * 合作机构数量
     */
    private Integer agencyNum;
    /**
     * 是否删除
     */
    private String delFlag;
    /**
     * 父ID
     */
    private String dictionaryId;
}
