package com.haiercash.pluslink.capital.processer.server.job.thread;

/**
 * @author xiaobin
 * @create 2018-07-28 上午10:50
 **/
public interface IWorker<T> extends Runnable {

    /**
     * 任务Id
     * <p>
     * 唯一标识
     *
     * @return
     */
    String getId();

    /**
     * 任务上下文
     *
     * @return
     */
    T getEntity();
}
