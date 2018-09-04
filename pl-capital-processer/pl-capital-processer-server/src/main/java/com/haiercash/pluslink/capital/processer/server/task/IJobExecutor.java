package com.haiercash.pluslink.capital.processer.server.task;

/**
 * @author xiaobin
 * @create 2018-07-18 下午2:23
 **/
public interface IJobExecutor {

    /**
     * 是否停止此项任务
     *
     * @param jobContext {code:11,name:'222'}
     * @return
     * @throws Exception
     */
    boolean execute(String jobContext) throws Exception;
}
