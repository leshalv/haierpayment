package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.integration.busflow.context.BusContext;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.ProcessMakeLoans;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.MakeLoansClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.MakeLoansRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.ProcessMakeLoansService;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserJobService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import com.haiercash.pluslink.capital.processer.server.task.context.MakeLoanContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

/**
 * 前置放款任务
 *
 * @author xiaobin
 * @create 2018-08-13 下午4:51
 **/
@Slf4j
public class MakeLoansTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.MakeLoansTask";


    private void init() {
        makeLoansClient = SpringContextHolder.getBean(MakeLoansClient.class);
        processerJobService = SpringContextHolder.getBean(ProcesserJobService.class);
        assetsSplitItemService = SpringContextHolder.getBean(AssetsSplitItemService.class);
        processMakeLoansService = SpringContextHolder.getBean(ProcessMakeLoansService.class);
        assetsSplitService = SpringContextHolder.getBean(AssetsSplitService.class);
        flowWorkerServer = SpringContextHolder.getBean(FlowWorkerServer.class);
    }

    @Override
    public boolean execute(String jobContext) {
        init();
        MakeLoanContext makeLoanContext = JsonUtils.readObjectByJson(jobContext, MakeLoanContext.class);
        AssetsSplit assetsSplit = makeLoanContext.getAssetsSplit();
        assetsSplit = assetsSplitService.get(assetsSplit.getId());
        AssetsSplitItem assetsSplitItem = makeLoanContext.getAssetsSplitItem();
        RestRequest<MakeLoansRequest> restRequest = makeLoanContext.getRestRequest();
        ProcessMakeLoans processMakeLoans = processMakeLoansService.selectByApplSeq(assetsSplit.getApplSeq());
        if (processMakeLoans != null) {
            log.info("此笔业务：{} 已经放款，将跳过前置放款。", assetsSplit.getApplSeq());
            return true;
        }
        if (FlowWorkUtils.isFinalState(assetsSplit)) {
            log.info("此笔业务：{} 已经资产表状态是终态，停止前置放款任务。", assetsSplit.getApplSeq());
            return true;
        }

        RestResponse<ResMakeLoansResponse> restResponse;
        try {
            restResponse = makeLoansClient.makeLoans("v1", restRequest);
        } catch (Exception ex) {
            log.error("此笔业务：{} ，调用前置放款异常：", assetsSplit.getApplSeq(), ex);
            return false;
        }

        LoanBackContext loanBackContext = new LoanBackContext(assetsSplit, assetsSplitItem, restResponse);
        if (restResponse.getHead().getRetFlag().equals("00000") || restResponse.getHead().getRetFlag().equals("PLCO3201")) {
            BusContext busContext = flowWorkerServer.loanBack(loanBackContext, this.getClass().getName());
            if (Objects.equals(busContext.getValue("status"), true)) {
                log.info("业务单号：{},调用前置放款，入参：{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(restRequest));
                log.info("业务单号：{},调用前置放款，出参：{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(restResponse));
                processMakeLoans = new ProcessMakeLoans();
                processMakeLoans.setId(IdGen.uuid());
                processMakeLoans.setApplSeq(assetsSplit.getApplSeq());
                processMakeLoans.setAssetsSplitId(assetsSplit.getId());
                processMakeLoans.setAssetsSplitItemId(assetsSplitItem.getId());
                processMakeLoans.setOrgCorpMsgId(restRequest.getBody().getCorpMsgId());
                processMakeLoans.setApplyDt(restRequest.getBody().getCorpLoanAppdt());
                processMakeLoans.setStatus("0");
                processMakeLoansService.insert(processMakeLoans);
                return true;
            } else {
                log.info(">>>>>>>>>>>>>>>>>>>>>>开始启用(前置工行放款状态任务查询),业务单号：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
                processerJobService.addLoanQueryJob(restRequest.getBody().getCorpMsgId(), assetsSplitItem.getId(), assetsSplit.getApplSeq(), assetsSplit.getId());
            }
        }
        return false;
    }

    private MakeLoansClient makeLoansClient;

    private AssetsSplitService assetsSplitService;

    private FlowWorkerServer flowWorkerServer;

    private ProcesserJobService processerJobService;

    private AssetsSplitItemService assetsSplitItemService;

    private ProcessMakeLoansService processMakeLoansService;
}
