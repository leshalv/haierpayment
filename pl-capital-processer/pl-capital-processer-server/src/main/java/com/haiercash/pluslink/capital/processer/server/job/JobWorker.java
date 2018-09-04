package com.haiercash.pluslink.capital.processer.server.job;

import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import com.haiercash.pluslink.capital.job.IJob;
import com.haiercash.pluslink.capital.processer.server.cache.JobCache;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserJobService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.util.Calendar;
import java.util.Date;

import static cn.jbinfo.cloud.core.utils.DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS;

/**
 * @author xiaobin
 * @create 2018-07-28 上午10:58
 **/
@Slf4j
public class JobWorker extends AbstractWorker {

    public JobWorker(String id) {
        super(id);
    }

    public JobWorker(IJob job) {
        super(job.getId(), job);
    }

    public JobWorker(String id, IJob job) {
        super(id, job);
    }

    @Override
    public void run() {

        String className = job.getModelName();
        IJobExecutor jobExecutor = null;
        ProcesserJobService processerJobService = SpringContextHolder.getBean(ProcesserJobService.class);
        if (job.getRunTime() == null) {
            job.setRunTime(0L);
        }
        job.setRunTime(job.getRunTime() + 1);
        try {
            jobExecutor = (IJobExecutor) BeanUtils.instantiate(ClassUtils.forName(className, null));
        } catch (ClassNotFoundException e) {
            log.error("任务目标类：{},jobId:{},参数：{}", className, job.getId(), job.getJobConText());
            log.error("类型转化异常：", e);
            job.setRunErrorStatus("1");
            job.setRunErrorMsg(StringUtils.substring(ExceptionUtils.getMessage(e), 0, 3980));
            processerJobService.updateErrorInfo(job);
        }
        if (jobExecutor == null) {
            return;
        }

        try {
            if (jobExecutor.execute(job.getJobConText())) {
                //log.info(job.getId() + ":>>>>>>>>>>true<<<<<<<<<<<<<<");
                //log.info("任务目标类：{},jobId：{}。返回true，任务停止", className, job.getId());
                processerJobService.updateStatusById(job);
                JobCache jobCache = SpringContextHolder.getBean(JobCache.class);
                jobCache.remove(job.getId());
            } else {
                try {
                    int jobHeartBeat = job.getJobHeartBeat();
                    if (jobHeartBeat <= 0) {
                        jobHeartBeat = 30;
                    }
                    String jobStartDate = job.getJobStartDate();
                    Date nextDate = DateUtils.addByDate(DateUtils.parseDate(jobStartDate, PATTERN_YYYY_MM_DD_HH_MM_SS), Calendar.SECOND, jobHeartBeat);
                    jobStartDate = DateUtils.format(nextDate, PATTERN_YYYY_MM_DD_HH_MM_SS);
                    job.setJobStartDate(jobStartDate);
                    processerJobService.updateNextDate(job);
                } catch (Exception ex) {
                    log.error("设置任务的下次执行时间出现异常：", ex);
                }
            }

        } catch (JobException e) {
            log.error("运行任务：{},jobId:{},参数：{}", className, job.getId(), job.getJobConText());
            log.error("异常信息：", e);
            job.setRunErrorStatus("1");
            job.setRunErrorMsg(e.getMessage());
            processerJobService.updateErrorInfo(job);
        } catch (Exception e) {
            log.error("运行任务：{},jobId:{},参数：{}", className, job.getId(), job.getJobConText());
            log.error("异常信息：", e);
            job.setRunErrorStatus("1");
            job.setRunErrorMsg(StringUtils.substring(ExceptionUtils.getMessage(e), 0, 3980));
            processerJobService.updateErrorInfo(job);
        }
    }

    @Override
    public IJob getEntity() {
        return job;
    }
}
