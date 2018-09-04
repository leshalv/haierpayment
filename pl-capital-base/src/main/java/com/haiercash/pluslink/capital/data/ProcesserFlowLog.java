package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 处理中心流程日志表
 *
 * @author xiaobin
 * @create 2018-08-06 下午1:22
 **/
@Getter
@Setter
public class ProcesserFlowLog extends BaseModel {

    /**
     * 业务编号
     */
    private String applSeq;

    /**
     * 名称
     */
    private String handlerName;

    /**
     * 即将走向
     */
    private String handlerRouting;

    /**
     * 是否异常（1：异常）
     */
    private String isError;

    /**
     * 异常说明
     */
    private String exception;

    private Date createDate;

    private Integer fdIndex;
}
