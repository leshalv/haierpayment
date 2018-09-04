package com.haiercash.pluslink.capital.processer.server.job;

import com.haiercash.pluslink.capital.data.ProcesserJob;
import com.haiercash.pluslink.capital.job.IJob;
import org.junit.Test;

public class JobExecuteTest {

    @Test
    public void execute() {
        IJob job = new ProcesserJob();
        job.setId("1");
        job.setModelName("com.haiercash.pluslink.capital.processer.server.job.TestTask");
        job.setJobConText("1");
        job.setJobStartDate("11111");
        job.setJobHeartBeat(1000 * 60);
        job.setFdKey("test");
        job.setJobStatus("1");

        //JobExecute.addWorker(job);
    }

}