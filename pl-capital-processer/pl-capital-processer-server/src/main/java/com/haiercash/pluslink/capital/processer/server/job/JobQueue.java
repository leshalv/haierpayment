package com.haiercash.pluslink.capital.processer.server.job;

import com.haiercash.pluslink.capital.processer.server.job.thread.IWorker;
import com.haiercash.pluslink.capital.processer.server.job.thread.ThreadPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.LinkedList;

/**
 * @author xiaobin
 * @create 2018-07-28 上午11:00
 **/
public class JobQueue extends ThreadPool {

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
        fillQueue();
    }

    private String poolName;

    private void fillQueue() {
        LinkedList<IWorker> list = deserializePool(this.poolName);
        if (CollectionUtils.isNotEmpty(list)) {
            for (IWorker worker : list) {
                this.addWorker(worker);
            }
        }
    }

    public JobQueue(int maxCount, String poolName) {
        super(maxCount);
        this.poolName = poolName;
        fillQueue();
    }

    @Override
    protected IWorker nextWorker() {
        IWorker worker = super.nextWorker();
        serializePool(this.poolName, this.workQueue);
        return worker;
    }

    private synchronized static LinkedList<IWorker> deserializePool(String name) {
        FileInputStream fis = null;
        ObjectInputStream oin = null;
        try {
            //FileUtil.mkdir(JobExecute.DIRECTORY);
            fis = new FileInputStream(name);
            oin = new ObjectInputStream(fis);
            return (LinkedList<IWorker>) oin.readObject();
        } catch (Exception e) {
            //throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(oin);
        }
        return null;
    }

    private synchronized static final void serializePool(String name, LinkedList list) {

        if (name == null || list == null) {
            return;
        }
        synchronized (list) {
            try (
                    FileOutputStream fos = new FileOutputStream(name);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)
            ) {
                oos.writeObject(list);
                fos.flush();
                oos.flush();
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }
}
