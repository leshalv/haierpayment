package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * > 数据字典主表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 11:31
 */
@Entity
@Getter
@Setter
@Table(name = "PL_DICTIONARY")
public class Dictionary extends BaseModel {

    /**
     * 数据字典大类名称
     */
    private String dictionaryName;
    /**
     * 码值显示标题
     */
    private String title;
}
