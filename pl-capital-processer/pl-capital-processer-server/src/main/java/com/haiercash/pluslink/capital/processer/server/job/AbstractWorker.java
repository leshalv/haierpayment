package com.haiercash.pluslink.capital.processer.server.job;

import com.haiercash.pluslink.capital.job.IJob;
import com.haiercash.pluslink.capital.processer.server.job.thread.IWorker;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiaobin
 * @create 2018-07-28 上午10:51
 **/
@Getter
@Setter
public abstract class AbstractWorker implements IWorker<IJob>, Serializable {

    protected String id;

    protected IJob job;

    public AbstractWorker(String id) {
        this.id = id;
    }

    public AbstractWorker(String id, IJob job) {
        this.id = id;
        this.job = job;

    }


    public abstract void run();
}
