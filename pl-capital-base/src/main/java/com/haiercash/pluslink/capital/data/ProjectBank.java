package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import com.haiercash.pluslink.capital.enums.dictionary.PL0102Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0106Enum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * > 合作项目支持银行表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 10:14
 */
@Entity
@Getter
@Setter
@Table(name = "PL_PROJECT_BANK")
public class ProjectBank extends BaseModel {

    public ProjectBank(){
        isSupport = PL0106Enum.PL0106_2_SUPPORT.getCode();
    }

    private String projectId;
    private String bankId;
    private String isSupport;
}
