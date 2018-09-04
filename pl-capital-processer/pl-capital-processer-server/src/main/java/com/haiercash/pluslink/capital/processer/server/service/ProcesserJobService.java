package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.google.common.collect.Maps;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.ProcesserJob;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.job.IJob;
import com.haiercash.pluslink.capital.processer.server.dao.ProcesserJobDao;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.pvm.vo.LoanResultVo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWaySearchRequest;
import com.haiercash.pluslink.capital.processer.server.task.context.CreditApplyTaskContext;
import com.haiercash.pluslink.capital.processer.server.task.context.MakeLoanContext;
import com.haiercash.pluslink.capital.processer.server.task.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fengxingqiang
 * 2018/7/21
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ProcesserJobService {

    @Autowired
    private ProcesserJobDao processerJobDao;

    /**
     * 延迟2分钟后执行
     */
    @Value("${processerjob.param.time:3}")
    private Integer TIME;

    /**
     * 心跳间隔：秒
     */
    @Value("${processerjob.param.job_heart_beat:120}")
    private Integer JOB_HEART_BEAT;

    /**
     * 信息补录
     */
    @Value("${processerjob.param.info_record_job_heart_beat:300}")
    private Integer INFO_RECORD_JOB_HEART_BEAT;

    /**
     * 信息补录 延迟**分钟后执行
     */
    @Value("${processerjob.param.info_record_job_time:10}")
    private Integer INFO_RECORD_JOB_TIME;

    /**
     * 添加授信状态任务查询
     * <p>
     * fd_key:CREDIT_STATUS_SEARCH
     * job_context: {"versionId":"v1","orgCorpMsgId":"","assetsSplitItemId":"","cinoMemno":"",}
     *
     * @param quota orgCorpMsgId      授信申请消息ID
     *              assetsSplitItemId 资产拆分明细ID
     *              cinoMemno         额度编号
     *              applSeq           业务标识
     */
    @Transactional
    public void addCreditStatusSearchJob(Quota quota, String applSeq) {
        Map<String, String> jobContext = Maps.newHashMap();
        jobContext.put("versionId", "v1");
        jobContext.put("orgCorpMsgId", quota.getOrgCorpMsgId());
        jobContext.put("assetsSplitItemId", quota.getAssetsSplitItemId());
        jobContext.put("cinoMemno", quota.getCinoMemno());
        jobContext.put("agencyId", quota.getCooprAgencyId());
        jobContext.put("certCode", quota.getCertCode());

        Date jobStartDate = DateUtils.addMinutes(new Date(), TIME);

        ProcesserJob job = new ProcesserJob();
        job.setModelName(CreditStatusTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(jobContext));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(applSeq);
        job.setMemo1("授信状态任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加前置工行放款状态任务查询
     * <p>
     * fd_key:CREDIT_STATUS_SEARCH
     * job_context: {"versionId":"v1","orgCorpMsgId":"","assetsSplitItemId":"","assetsSplitId":""}
     *
     * @param orgCorpMsgId      原消息ID
     * @param assetsSplitItemId 资产拆分明细表主键ID
     * @param applSeq           业务标识
     * @param assetsSplitId     资产表主键ID
     */
    @Transactional
    public void addLoanQueryJob(String orgCorpMsgId, String assetsSplitItemId, String applSeq, String assetsSplitId) {
        Map<String, String> jobContext = Maps.newHashMap();
        jobContext.put("versionId", "v1");
        jobContext.put("orgCorpMsgId", orgCorpMsgId);
        jobContext.put("assetsSplitItemId", assetsSplitItemId);
        jobContext.put("assetsSplitId", assetsSplitId);
        Date jobStartDate = DateUtils.addMinutes(new Date(), TIME);
        ProcesserJob job = new ProcesserJob();
        job.setModelName(LoanQueryTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(jobContext));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(applSeq);
        job.setMemo1("前置工行放款状态任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加临时表处理相关任务
     * <p>
     * fd_key:CREDIT_STATUS_SEARCH
     * job_context: {"contractNo":"","assetsSplitItemId":"","cursorId":"","cooprAgencyId":"","certCode":""}
     *
     * @param contractNo        合同号
     * @param assetsSplitItemId 资产拆分明细表主键ID
     * @param cursorId          临时表主键ID
     * @param cooprAgencyId     合作机构编号
     * @param certCode          身份证号
     * @param applSeq           业务标识
     */
    @Transactional
    public void addCursorLogicJob(String contractNo, String assetsSplitItemId, String cursorId,
                                  String cooprAgencyId, String certCode, String applSeq) {
        Map<String, String> jobContext = Maps.newHashMap();
        jobContext.put("contractNo", contractNo);
        jobContext.put("assetsSplitItemId", assetsSplitItemId);
        jobContext.put("cursorId", cursorId);
        jobContext.put("cooprAgencyId", cooprAgencyId);
        jobContext.put("certCode", certCode);

        Date jobStartDate = DateUtils.addMinutes(new Date(), TIME);

        ProcesserJob job = new ProcesserJob();
        job.setModelName(CursorLogicTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(jobContext));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(applSeq);
        job.setMemo1("临时表处理相关任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加支付网关单笔代收付结果任务查询
     * <p>
     * fd_key: TRADE_STATUS_SEARCH
     * job_context:{"piType":"COLLECTION","elecChequeNo":"req_no_n1","use":"test use","businessPayNo":""}
     *
     * @param assetsSplit piType            支付工具 默认
     *                    elecChequeNo    支付请求号
     *                    use         用途 非必填
     *                    businessPayNo           业务流水号
     */
    @Transactional
    public void addTradeStatusSearchJob(AssetsSplit assetsSplit) {
        PayMentGateWaySearchRequest payMentGateWaySearchRequest = new PayMentGateWaySearchRequest();
        payMentGateWaySearchRequest.setPiType("TRANSFER");
        payMentGateWaySearchRequest.setElecChequeNo(assetsSplit.getContractNo());
        payMentGateWaySearchRequest.setBusinessPayNo(assetsSplit.getContractNo());
        //5分钟轮询
        Date jobStartDate = DateUtils.addMinutes(new Date(), TIME);
        ProcesserJob job = new ProcesserJob();
        job.setModelName(TradeStatusTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(payMentGateWaySearchRequest));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(assetsSplit.getApplSeq());
        job.setMemo1("支付网关单笔代收付结果任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加授信申请任务
     */
    public void addCreditApplyJob(CreditApplyTaskContext context, String applSeq) {
        Date jobStartDate = DateUtils.addMinutes(new Date(), TIME);
        ProcesserJob job = new ProcesserJob();
        job.setModelName(CreditApplyTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(context));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(applSeq);
        job.setMemo1("授信申请任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加记账任务
     *
     * @param loanResultVo
     */
    public void addAccountRecordJob(LoanResultVo loanResultVo) {
        Date jobStartDate = DateUtils.addByDate(new Date(), Calendar.SECOND, 13);
        ProcesserJob job = new ProcesserJob();
        job.setModelName(AccountRecordTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(loanResultVo));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(loanResultVo.getApplSeq());
        job.setMemo1("记账任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            log.error("添加记账任务异常：", e);
            //throw new RuntimeException(e);
        }
    }

    /**
     * 添加前置放款任务
     */
    public void addMakeLoanJob(MakeLoanContext makeLoanContext) {
        Date jobStartDate = DateUtils.addMinutes(new Date(), TIME);
        ProcesserJob job = new ProcesserJob();
        job.setModelName(MakeLoansTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(makeLoanContext));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(makeLoanContext.getAssetsSplit().getApplSeq());
        job.setMemo1("前置放款任务");
        try {
            addProcesserJob(job);
        } catch (Exception e) {
            log.error("添加前置放款任务异常：", e);
            //throw new RuntimeException(e);
        }
    }


    /**
     * 添加信息补录任务
     */
    public void addInfoRecordJob(LoanBackContext loanBackContext) {
        Date jobStartDate = DateUtils.addMinutes(new Date(), INFO_RECORD_JOB_TIME);
        ProcesserJob job = new ProcesserJob();
        job.setModelName(InfoRecordTask.MODEL_NAME);
        job.setJobConText(JsonUtils.writeObjectToJson(loanBackContext));
        job.setJobStartDate(cn.jbinfo.cloud.core.utils.DateUtils.format(jobStartDate, "yyyy-MM-dd HH:mm:ss"));
        job.setJobHeartBeat(INFO_RECORD_JOB_HEART_BEAT);
        job.setJobStatus("1");
        job.setFdKey(loanBackContext.getAssetsSplit().getApplSeq());
        job.setMemo1("信息补录任务");
        try {
            //addProcesserJob(job);
        } catch (Exception e) {
            log.error("添加信息补录任务异常：", e);
            //throw new RuntimeException(e);
        }
    }


    @Transactional
    public void updateStatusById(IJob job) {
        processerJobDao.updateStatusById(job.getId(), job.getRunTime(), PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    @Transactional
    public void updateErrorInfo(IJob job) {
        processerJobDao.updateErrorInfo(job.getRunErrorStatus(), job.getRunTime(), job.getRunErrorMsg(), job.getId(), PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    @Transactional
    public void updateNextDate(IJob job) {
        processerJobDao.updateNextDateById(job.getId(), job.getRunTime(), job.getJobStartDate());
    }

    @Transactional
    public void addProcesserJob(ProcesserJob job) {
        if (StringUtils.isBlank(job.getId()))
            job.setId(IdGen.uuid());
        job.setRunTime(0L);
        job.setCreateDate(new Date());
        job.setUpdateDate(new Date());
        job.setCreateBy("SYSTEM");
        job.setUpdateBy("SYSTEM");
        processerJobDao.insertProcesserJob(job);
    }

    public List<ProcesserJob> selectByJobStatus(String modelName) {
        Date date = new Date();
        String jobStartDate = DateUtils.format(DateUtils.addDay(date, -2), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
        String jobEndDate = DateUtils.format(date, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
        return processerJobDao.selectByJobStatus(jobStartDate, jobEndDate, modelName, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

}
