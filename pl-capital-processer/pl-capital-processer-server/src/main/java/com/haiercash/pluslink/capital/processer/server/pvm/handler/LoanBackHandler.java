package com.haiercash.pluslink.capital.processer.server.pvm.handler;

import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.integration.busflow.context.BusContext;
import cn.jbinfo.integration.busflow.station.BusParameter;
import com.haiercash.pluslink.capital.common.utils.SequenceUtil;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.enums.LoanStatusQueryEnum;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0505Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.PVM;
import com.haiercash.pluslink.capital.processer.server.pvm.event.LendingResultEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.CommonDaoService;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Objects;

/**
 * 前置放款回调
 *
 * @author xiaobin
 * @create 2018-08-03 上午10:49
 **/
@Slf4j
public class LoanBackHandler extends IHandler {

    public void doStart(@BusParameter("loanBackContext") LoanBackContext loanBackContext, BusContext busContext) {
        busContext.put(METHOD_NAME, "前置放款回调");
        AssetsSplit assetsSplit = loanBackContext.getAssetsSplit();

        Object target = busContext.getValue(PVM.TARGET);
        if (Objects.nonNull(target)) {
            log.info(">>>前置放款回调,业务单号：{},放款状态:{},目标类：{}", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus(), target);
        } else {
            log.info(">>>前置放款回调,业务单号：{},放款状态:{}", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus());
        }

        if (FlowWorkUtils.isFinalState(assetsSplit)) {
            busContext.put("status", true);
            return;
        }
        busContext.put("applSeq", assetsSplit.getApplSeq());
        RestResponse<ResMakeLoansResponse> response = loanBackContext.getRestResponse();

        if (LoanStatusQueryEnum.LOAN_STATUS_SUCCESS.getCode().equals(response.getHead().getRetFlag())) {
            //放款成功
            //busContext.setRoutingKey(PVM.PASS);
            loanBackSuccess(loanBackContext, busContext);
        } else if (LoanStatusQueryEnum.LOAN_STATUS_EXCEPTION.getCode().equals(response.getHead().getRetFlag())) {
            //通信异常
            busContext.put("status", false);
        } else {
            //放款失败
            //busContext.setRoutingKey(PVM.NOT_PASS);
            loanBackFail(loanBackContext, busContext);
        }
    }

    /**
     * 放款成功
     *
     * @param loanBackContext
     * @param busContext
     */
    public void loanBackSuccess(@BusParameter("loanBackContext") LoanBackContext loanBackContext, BusContext busContext) {
        AssetsSplit assetsSplit = loanBackContext.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = loanBackContext.getAssetsSplitItem();
        String capLoanNo = loanBackContext.getRestResponse().getBody().getLoanMemno();
        busContext.put("status", true);
        log.info(">>>>>>>>>>>>>>>>>>>>>业务单号:{},前置放款成功，返回贷款协议号：{}", assetsSplit.getApplSeq(), capLoanNo);
        assetsSplitItemService.updateStatusAndCapLoanNoById(
                loanBackContext.getAssetsSplitItem(), PL0601Enum.PL0601_11_31, capLoanNo);
        log.info("===============<处理中心>-业务单号：{},调用前置放款成功修改资产明细表状态:{}", assetsSplit.getApplSeq(), PL0601Enum.PL0601_11_31.getCode());
        assetsSplitService.updateLoanStatusById(loanBackContext.getAssetsSplit(), PL0506Enum.PL0506_5_50);
        log.info("===============<处理中心>-业务单号：{},调用前置放款成功修改资产表状态:{}", assetsSplit.getApplSeq(), PL0506Enum.PL0506_5_50.getCode());
        setHandler(busContext, assetsSplit.getApplSeq(), "前置放款回调检测:前置放款成功");

        //自有明细
        AssetsSplitItem assetsSplitItemDTO = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
        if (assetsSplitItemDTO == null) {
            assetsSplitItemService.insertUnionAssetsSplitItemInternal(assetsSplit, assetsSplitItem, PL0601Enum.PL0601_11_31, "前置放款成功，插入一条自有明细");
        } else {
            assetsSplitItemService.updateStatusById(assetsSplitItemDTO, PL0601Enum.PL0601_11_31, "");
        }

        //添加信息补录定时任务
        processerJobService.addInfoRecordJob(loanBackContext);

        //通知信贷
        assetsSplit = assetsSplitService.get(assetsSplit.getId());
        List<AssetsSplitItem> assetsSplitItems =
                assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplitItem.getAssetsSplitId());
        LendingResultEvent lendingResultEvent = new LendingResultEvent(this, assetsSplit, assetsSplitItems);
        applicationEventPublisher.publishEvent(lendingResultEvent);
    }

    /**
     * 放款失败
     *
     * @param loanBackContext
     * @param busContext
     */
    public void loanBackFail(@BusParameter("loanBackContext") LoanBackContext loanBackContext, BusContext busContext) {
        busContext.put("status", true);
        AssetsSplit assetsSplit = loanBackContext.getAssetsSplit();
        ResMakeLoansResponse response = loanBackContext.getRestResponse().getBody();
        String corpMsgId = "";
        if (response != null) {
            corpMsgId = response.getCorpMsgId();
        }
        log.info(">>>>>>>>>>>>>业务单号：{},前置放款状态 失败处理,前置消息Id:{}<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq(), corpMsgId);
        AssetsSplitItem assetsSplitItem = loanBackContext.getAssetsSplitItem();
        setHandler(busContext, assetsSplit.getApplSeq(), "前置放款回调检测:前置放款失败");
        assetsSplitService.updateLoanStatusAndProjectTypeAndProdBuyOutById(
                assetsSplit, PL0506Enum.PL0506_5_50.getCode(), PL0505Enum.PL0505_2_NOT_UNION.getCode(), "");
        log.info("===============前置放款失败,业务单号：{}, 修改资产表放款状态为:{};修改资产表是否联合放款状态为：{}", assetsSplit.getApplSeq(), PL0506Enum.PL0506_5_50.getCode(), PL0505Enum.PL0505_2_NOT_UNION.getCode());
        assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_12_32, "");
        AssetsSplitItem assetsSplitItemDTO = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());

        if (assetsSplitItemDTO == null) {
            assetsSplitItemDTO = new AssetsSplitItem();
            String assetsSplitItemID = SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT.getTypeName(), commonDaoService.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getSeqName()));
            assetsSplitItemDTO.setId(assetsSplitItemID);
            assetsSplitItemService.insertAssetsSplitItemInternal(assetsSplit, PL0601Enum.PL0601_11_31, "网关放款失败,插入一条自有明细");
        } else {
            assetsSplitItemService.updateBackData(assetsSplit, assetsSplitItemDTO, "前置放款失败，合并数据");
        }

        //通知信贷
        assetsSplit = assetsSplitService.get(assetsSplit.getId());
        List<AssetsSplitItem> assetsSplitItems = assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplitItem.getAssetsSplitId());
        LendingResultEvent lendingResultEvent = new LendingResultEvent(this, assetsSplit, assetsSplitItems);
        applicationEventPublisher.publishEvent(lendingResultEvent);

    }

    @Autowired
    private AssetsSplitService assetsSplitService;

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private CommonDaoService commonDaoService;

    @Autowired
    private ProcesserJobService processerJobService;
}
