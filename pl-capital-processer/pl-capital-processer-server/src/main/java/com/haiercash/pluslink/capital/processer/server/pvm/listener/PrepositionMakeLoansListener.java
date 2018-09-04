package com.haiercash.pluslink.capital.processer.server.pvm.listener;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.ProcessMakeLoans;
import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.event.MakeLoansEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.MakeLoansClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.MakeLoansRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.ProcessMakeLoansService;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserJobService;
import com.haiercash.pluslink.capital.processer.server.task.context.MakeLoanContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 前置放款监听器
 *
 * @author xiaobin
 * @create 2018-07-25 上午10:46
 **/
@Component
@Slf4j
public class PrepositionMakeLoansListener implements SmartApplicationListener, Serializable {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return MakeLoansEvent.class == aClass;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        MakeLoansEvent makeLoansEvent = (MakeLoansEvent) applicationEvent;
        AssetsSplit assetsSplit = makeLoansEvent.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = makeLoansEvent.getAssetsSplitItem();

        //查询放款记录
        ProcessMakeLoans processMakeLoans = processMakeLoansService.selectByApplSeq(assetsSplit.getApplSeq());
        if (processMakeLoans != null) {
            log.info("此笔业务：{} 已经放款，将跳过前置放款。", assetsSplit.getApplSeq());
            return;
        }
        if (FlowWorkUtils.isFail(assetsSplit)) {
            return;
        }
        String corpMsgId = IdGen.uuid();
        MakeLoansRequest makeLoansRequest = makeLoansEvent.getMakeLoansRequest();
        //设置放款消息id
        makeLoansRequest.setCorpMsgId(corpMsgId);
        //调用工行放款接口
        RestRequestHead head = new RestRequestHead("PLCO004", "CAPTIAL");
        RestRequest<MakeLoansRequest> restRequest = new RestRequest<>(head, makeLoansRequest);
        RestResponse<ResMakeLoansResponse> restResponse;
        try {
            restResponse = makeLoansClient.makeLoans("v1", restRequest);
        } catch (Exception ex) {
            log.error("调用前置放款异常,此业务添加到任务队列中。", ex);
            MakeLoanContext makeLoanContext = new MakeLoanContext(assetsSplit, assetsSplitItem, restRequest);
            processerJobService.addMakeLoanJob(makeLoanContext);
            return;
        }
        AssetsSplitItem assetsSplitItemSelf = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
        if (assetsSplitItemSelf == null) {
            assetsSplitItemService.insertUnionAssetsSplitItemInternal(assetsSplit, assetsSplitItem, PL0601Enum.PL0601_11_31,"");
        } else {
            assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_11_31, "");
        }

        LoanBackContext loanBackContext = new LoanBackContext(assetsSplit, assetsSplitItem, restResponse);
        if (restResponse.getHead().getRetFlag().equals("00000") || restResponse.getHead().getRetFlag().equals("PLCO3201")) {
            flowWorkerServer.loanBack(loanBackContext,this.getClass().getName());
        } else {
            log.info("业务单号：{},调用前置放款，入参：{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(restRequest));
            log.info("业务单号：{},调用前置放款，出参：{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(restResponse));

            processMakeLoans = new ProcessMakeLoans();
            processMakeLoans.setId(IdGen.uuid());
            processMakeLoans.setApplSeq(assetsSplit.getApplSeq());
            processMakeLoans.setAssetsSplitId(assetsSplit.getId());
            processMakeLoans.setAssetsSplitItemId(assetsSplitItem.getId());
            processMakeLoans.setOrgCorpMsgId(corpMsgId);
            processMakeLoans.setApplyDt(makeLoansRequest.getCorpLoanAppdt());
            processMakeLoans.setStatus("0");
            processMakeLoansService.insert(processMakeLoans);

            log.info(">>>>>>>>>>>>>>>>>>>>>>开始启用(前置工行放款状态任务查询),业务单号：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
            assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_10_30, "");
            processerJobService.addLoanQueryJob(corpMsgId, assetsSplitItem.getId(), assetsSplit.getApplSeq(), assetsSplit.getId());
        }

    }

    @Override
    public int getOrder() {
        return 100;
    }

    @Autowired
    private MakeLoansClient makeLoansClient;

    @Autowired
    private FlowWorkerServer flowWorkerServer;

    @Autowired
    private ProcesserJobService processerJobService;

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    @Autowired
    private ProcessMakeLoansService processMakeLoansService;
}
