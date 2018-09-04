package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import com.haiercash.pluslink.capital.job.IJob;
import lombok.Getter;
import lombok.Setter;


/**
 * > 任务调度表
 * author : fengxingqiang
 * date : 2018/7/20
 */
@Getter
@Setter
public class ProcesserJob extends BaseModel implements IJob {

    /**
     * 主键
     */
    private String id;

    /**
     * 类路径
     */
    private String modelName;

    /**
     * 上下文
     */
    private String jobConText;

    /**
     * 启动时间 到秒(yyyy-MM-dd HH:mm:ss)
     */
    private String jobStartDate;

    /**
     * 心跳频率
     */
    private Integer jobHeartBeat;
    /**
     * 任务状态  1有效   0无效
     */
    private String jobStatus;

    /**
     * 调用支付网关PAY_GATEWAY、授信状态查询：CREDIT_STATUS_SEARCH
     */
    private String fdKey;

    /**
     * 运行异常状态
     */
    private String runErrorStatus;

    /**
     * 运行异常说明
     */
    private String runErrorMsg;

    /**
     * 执行次数
     */
    private Long runTime;


    private String memo1;
}
