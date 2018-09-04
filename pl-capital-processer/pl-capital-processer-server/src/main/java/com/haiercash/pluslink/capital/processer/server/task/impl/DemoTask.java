package com.haiercash.pluslink.capital.processer.server.task.impl;

import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;

/**
 * @author xiaobin
 * @create 2018-08-23 下午2:02
 **/
public class DemoTask implements IJobExecutor {

    @Override
    public boolean execute(String jobContext) throws Exception {

        System.out.println("---------" + jobContext);
        return false;
    }
}
