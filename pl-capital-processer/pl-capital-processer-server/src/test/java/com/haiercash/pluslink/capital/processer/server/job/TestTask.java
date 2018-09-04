package com.haiercash.pluslink.capital.processer.server.job;

import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;

/**
 * @author xiaobin
 * @create 2018-07-28 上午11:20
 **/
public class TestTask implements IJobExecutor {

    @Override
    public boolean execute(String jobContext) throws Exception {
        System.out.println(">>>>>>>>>>>TestTask<<<<<<<<<<<<");
        return false;
    }
}
