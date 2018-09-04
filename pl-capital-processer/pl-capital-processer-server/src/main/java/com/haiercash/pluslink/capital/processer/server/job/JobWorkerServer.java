package com.haiercash.pluslink.capital.processer.server.job;

import com.haiercash.pluslink.capital.data.ProcesserJob;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserJobService;
import com.haiercash.pluslink.capital.processer.server.task.impl.*;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaobin
 * @create 2018-07-28 上午11:35
 **/
@Component
@Slf4j
public class JobWorkerServer {

    private static final String SCHEDULED_CRON = "0/50 * * * * ?";

    /**
     * 锁的时长
     */
    private static final long LOCK_AT_MOST_FOR = 1000 * 55;

    /**
     * 保留锁的最短时间
     */
    private static final long LOCK_AT_LEAST_FOR = 1000 * 55;

    @Autowired
    private ProcesserJobService processerJobService;

    /**
     * 授信状态查询
     */
    @Scheduled(cron = SCHEDULED_CRON)
    @SchedulerLock(name = "creditStatusScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void creditStatusScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(CreditStatusTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        log.info(">>>>>>>>>>>>>>>>>>启动授信状态查询任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        // processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.creditStatusQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }

    /**
     * 授信申请
     */
    @Scheduled(cron = SCHEDULED_CRON)
    @SchedulerLock(name = "creditApplyScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void creditApplyScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(CreditApplyTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        log.info(">>>>>>>>>>>>>>>>>>启动授信申请任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        //processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.creditApplyQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });
    }

    /**
     * 临时表处理
     */
    @Scheduled(cron = SCHEDULED_CRON)
    @SchedulerLock(name = "cursorLogicScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void cursorLogicScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(CursorLogicTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        log.info(">>>>>>>>>>>>>>>>>>启动临时表处理任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        //processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.cursorLogicQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }

    /**
     * 放款状态查询
     */
    @Scheduled(cron = SCHEDULED_CRON)
    @SchedulerLock(name = "loanQueryScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void loanQueryScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(LoanQueryTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        log.info(">>>>>>>>>>>>>>>>>>启动放款状态查询任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        //processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.loanQueryQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }

    /**
     * 放款状态查询
     */
    @Scheduled(cron = SCHEDULED_CRON)
    @SchedulerLock(name = "tradeStatusScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void tradeStatusScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(TradeStatusTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        log.info(">>>>>>>>>>>>>>>>>>启动查询支付网关代付状态任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }

    /**
     * 放款任务
     */
    @Scheduled(cron = SCHEDULED_CRON)
    @SchedulerLock(name = "makeLoanScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void makeLoanScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(MakeLoansTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        //log.info(">>>>>>>>>>>>>>>>>>启动查询支付网关代付状态任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        //processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.makeLoanQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }

    /**
     * 记账任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    @SchedulerLock(name = "accountRecordScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void accountRecordScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(AccountRecordTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        //log.info(">>>>>>>>>>>>>>>>>>启动查询支付网关代付状态任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        //processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.accountRecordQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }


    /**
     * 信息补录
     */
    //@Scheduled(cron = "0/20 * * * * ?")
    //@SchedulerLock(name = "infoRecordTaskScheduled", lockAtMostFor = LOCK_AT_MOST_FOR, lockAtLeastFor = LOCK_AT_LEAST_FOR)
    public void infoRecordScheduled() {
        List<ProcesserJob> processerJobList = processerJobService.selectByJobStatus(InfoRecordTask.MODEL_NAME);
        if (CollectionUtils.isEmpty(processerJobList))
            return;
        //log.info(">>>>>>>>>>>>>>>>>>启动查询支付网关代付状态任务服务<<<<<<<<<<<<<<<<<<<,条数：{}", processerJobList.size());
        //processerJobList.forEach(job -> JobExecute.addWorker(new JobWorker(job), JobExecute.infoRecordQueue));
        processerJobList.forEach(job -> {
            JobWorker jobWorker = new JobWorker(job);
            jobWorker.run();
        });

    }
}
