package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * > 日志操作记录表
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 11:26
 */
@Entity
@Getter
@Setter
@Table(name = "PL_LOG_INFO")
public class LogInfo extends BaseModel{
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 日志名称
     */
    private String logName;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 内容
     */
    private String logContent;
}
