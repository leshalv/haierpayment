package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 规则阶段任务表实体类
 *
 * @author WDY
 * @date 20180713
 */
@Getter
@Setter
@Table(name = "PL_SCHEDULE_RULE_PHASE")
public class ScheduleRulePhase extends BaseModel {

    /**
     * 调度规则表主键ID
     **/
    private Integer scheduleRuleId;
    /**
     * 流转顺序1-消金放款,2-合作机构放款
     **/
    private String transferOeder;
    /**
     * 数据字典:pl00121;事件类型:1-主动执行,2-驱动执行
     **/
    private String eventType;
    /**
     * 数据字典:pl00122;事件结束方式:1-自动完成;2-根据数据返回的元数据执行完成;
     **/
    private String eventExecutionModel;
    /**
     * 上一任务执行完多久执行本次任务:秒
     **/
    private Integer previousAfterWorkTime;
    /**
     * 异常重试次数:次
     **/
    private Integer exceptionRetryTime;
    /**
     * 异常超时时间:秒
     **/
    private Integer exceptionOvertime;
    /**
     * 异常通知
     **/
    private String exceptionNotifyApi;
}
