package com.haiercash.pluslink.capital.job.task;

import cn.jbinfo.common.utils.DateUtils;
import com.haiercash.pluslink.capital.data.ProcesserJob;
import com.haiercash.pluslink.capital.processer.server.job.JobExecute;
import com.haiercash.pluslink.capital.processer.server.job.JobWorker;
import org.assertj.core.util.Lists;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobin
 * @create 2018-08-23 下午2:05
 **/
public class ProcesserJobTest {

    public static void main(String[] args) {

        ProcesserJob processerJob;

        List<ProcesserJob> list = Lists.newArrayList();

        for (int i = 0; i < 1000; i++) {
            processerJob = new ProcesserJob();
            processerJob.setId("id--" + i);
            processerJob.setModelName("com.haiercash.pluslink.capital.processer.server.task.impl.DemoTask");
            processerJob.setJobConText("CONTEXT----" + i);
            processerJob.setJobStartDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            processerJob.setJobHeartBeat(10);
            processerJob.setFdKey("0");
            processerJob.setJobStatus("1");
            list.add(processerJob);
        }

        list.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.defaultQueue));

        list.clear();

        for (int i = 1000; i < 2000; i++) {
            processerJob = new ProcesserJob();
            processerJob.setId("id--" + i);
            processerJob.setModelName("com.haiercash.pluslink.capital.processer.server.task.impl.DemoTask");
            processerJob.setJobConText("CONTEXT----" + i);
            processerJob.setJobStartDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            processerJob.setJobHeartBeat(10);
            processerJob.setFdKey("0");
            processerJob.setJobStatus("1");
            list.add(processerJob);
        }

        list.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.defaultQueue));

    }
}
