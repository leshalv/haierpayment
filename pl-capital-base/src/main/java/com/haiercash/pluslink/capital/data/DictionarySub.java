package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 数据字典子表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 13:39
 */
@Entity
@Getter
@Setter
@Table(name = "PL_DICTIONARY_SUB")
public class DictionarySub extends BaseModel {

    private String dictionaryId;
    private String subName;
    private String subValue;
    private String subOrder;
}
