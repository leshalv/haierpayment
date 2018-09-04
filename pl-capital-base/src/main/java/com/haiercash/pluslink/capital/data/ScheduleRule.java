package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 调度规则表实体类
 *
 * @author WDY
 * @date 20180713
 */
@Entity
@Getter
@Setter
@Table(name = "PL_SCHEDULE_RULE")
public class ScheduleRule extends BaseModel {

    /**
     * 合作项目表主键ID
     **/
    private String projectId;
    /**
     * 数据字典:pl00111;规则类型:0-实时,1-异步
     **/
    private String ruleType;
    /**
     * 使用方
     **/
    private String ruleUser;
    /**
     * 使用方产品编号
     **/
    private String ruleUserProductNo;
    /**
     * 客户标签
     **/
    private String customerTag;

}
