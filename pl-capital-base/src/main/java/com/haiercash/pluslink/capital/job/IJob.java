package com.haiercash.pluslink.capital.job;

/**
 * @author xiaobin
 * @create 2018-07-28 上午11:07
 **/
public interface IJob {

    /**
     * 任务Id
     *
     * @return
     */
    String getId();

    void setId(String id);

    String getModelName();

    void setModelName(String modelName);

    /**
     * 任务上下文
     *
     * @return
     */
    String getJobConText();

    void setJobConText(String jobConText);

    /**
     * 任务启动时间
     *
     * @return
     */
    String getJobStartDate();

    void setJobStartDate(String jobStartDate);

    /**
     * 任务心跳频率
     *
     * @return
     */
    Integer getJobHeartBeat();


    void setJobHeartBeat(Integer jobHeartBeat);

    /**
     * 任务状态
     *
     * @return
     */
    String getJobStatus();

    void setJobStatus(String jobStatus);

    /**
     * 任务标识
     *
     * @return
     */
    String getFdKey();


    void setFdKey(String fdKey);

    /**
     * 运行异常状态：1：异常
     *
     * @return
     */
    String getRunErrorStatus();

    void setRunErrorStatus(String runErrorStatus);

    /**
     * 运行异常说明
     *
     * @return
     */
    String getRunErrorMsg();

    void setRunErrorMsg(String runErrorMsg);

    /**
     * 执行次数
     */
    Long getRunTime();

    void setRunTime(Long runTime);

    String getMemo1();

    void setMemo1(String memo1);

}

