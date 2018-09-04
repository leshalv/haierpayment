package com.haiercash.pluslink.capital.processer.server.job;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaobin
 * @create 2018-07-28 上午11:14
 **/
@Slf4j
public class JobExecute {

    private static final String LOCK_KEY = "LOCK";

    private static Integer JOB_POOL_MAX_THREAD = 50;

    public static final JobQueue defaultQueue = new JobQueue(JOB_POOL_MAX_THREAD, "default_queue");

    public static JobQueue creditStatusQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_credit_status_queue");

    public static JobQueue creditApplyQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_credit_apply_queue");

    public static JobQueue cursorLogicQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_cursor_logic_queue");

    public static JobQueue loanQueryQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_loan_query_queue");

    public static JobQueue tradeStatusQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_trade_status_queue");

    public static JobQueue accountRecordQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_account_record_queue");

    public static JobQueue infoRecordQueue = new JobQueue(JOB_POOL_MAX_THREAD, "info_record_queue");

    public static JobQueue makeLoanQueue = new JobQueue(JOB_POOL_MAX_THREAD, "job_make_loan_queue");


    public synchronized static void addWorker(JobWorker worker, JobQueue jobQueue) {
        if (jobQueue == null) {
            jobQueue = defaultQueue;
        }
        if (!checkWorker(worker)) {
            return;
        }
        try {
            synchronized (LOCK_KEY) {
                jobQueue.addWorker(worker);
            }
        } catch (Exception ex) {
            //log.error("出现异常：", ex);
            try {
                jobQueue.cleanup();
            } catch (InterruptedException e) {
            }
        }
    }


    public synchronized static void shutDownWorker(JobQueue jobQueue) {
        if (jobQueue == null) {
            jobQueue = defaultQueue;
        }
        if (!jobQueue.isShutdown()) {
            jobQueue.shutdown();
        }
    }


    private synchronized static boolean checkWorker(JobWorker worker) {
        return worker.getJob() != null && "1".equals(worker.getJob().getJobStatus());
    }
}
