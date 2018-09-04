package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditApplRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import com.haiercash.pluslink.capital.processer.server.task.context.CreditApplyTaskContext;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 授信申请任务
 *
 * @author xiaobin
 * @create 2018-08-11 下午1:34
 **/
@Slf4j
public class CreditApplyTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.CreditApplyTask";

    private FlowWorkerServer flowWorkerServer;

    private CreditApplRestApi creditApplRestApi;

    private AssetsSplitService assetsSplitService;

    private void init() {
        this.flowWorkerServer = SpringContextHolder.getBean(FlowWorkerServer.class);
        this.creditApplRestApi = SpringContextHolder.getBean(CreditApplRestApi.class);
        assetsSplitService = SpringContextHolder.getBean(AssetsSplitService.class);
    }


    @Override
    public boolean execute(String jobContext) {
        log.info("job>>>>>>>>>>>>>>>>开始执行授信申请任务<<<<<<<<<<<<<<");
        init();
        CreditApplyTaskContext context = JsonUtils.readObjectByJson(jobContext, CreditApplyTaskContext.class);
        AssetsSplit assetsSplit = context.getAssetsSplit();
        assetsSplit = assetsSplitService.get(assetsSplit.getId());
        if (assetsSplit == null) {
            log.info("job>>>>>>>>>>>>>>>>执行授信申请任务资产表无该笔业务{},停止轮询授信申请<<<<<<<<<<<<<<");
            return true;
        }
        if (FlowWorkUtils.isFinalState(assetsSplit)) {
            log.info("job>>>>>>>>>>>>>>>>执行授信申请任务业务编号{}，资产表状态已经是终态{},停止轮询授信申请<<<<<<<<<<<<<<", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus());
            return true;
        }
        AssetsSplitItem assetsSplitItem = context.getAssetsSplitItem();
        RestRequest<CreditApplRequestVo> restRequest = context.getRestRequest();
        RestResponse<ResCreditBackVo> restResponse;
        try {
            restResponse = creditApplRestApi.appFor("v1", restRequest);
        } catch (Exception ex) {
            log.error("业务编号{}，授信查询出现异常，继续执行授信申请任务", assetsSplit.getApplSeq(), ex);
            return false;
        }

        CreditBackContext creditBackContext = new CreditBackContext(assetsSplit, assetsSplitItem, restResponse, null, restRequest.getBody().getCorpMsgId());
        flowWorkerServer.creditApplyBacking(creditBackContext,this.getClass().getName());
        return true;
    }
}
