package com.haiercash.pluslink.capital.processer.server.pvm.listener;

import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.event.LendingResultEvent;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.CursorService;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserJobService;
import com.haiercash.pluslink.capital.processer.server.stream.StreamSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 通知信贷
 *
 * @Auther: yu jianwei
 * @Date: 2018/7/24 14:36
 * @Description:放款结果通知至信贷监听
 */
@Slf4j
@Component
public class LendingResultSendListener implements SmartApplicationListener {

    @Autowired
    private StreamSender streamSender;

    @Autowired
    private ProcesserJobService processerJobService;

    @Autowired
    private CursorService cursorService;

    @Autowired
    private AssetsSplitService assetsSplitService;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == LendingResultEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        /*todo 消息类型*/
        LendingResultEvent lendingResultEvent = (LendingResultEvent) event;
        if (!FlowWorkUtils.isFinalState(lendingResultEvent.getAssetsSplit())) {
            return;
        }
        log.info("进入放款结果通知信贷程序:业务单号:{}", lendingResultEvent.getLoanResultVo().getApplSeq());
        //String appSeq = lendingResultEvent.getLoanResultVo().getApplSeq();
        if (FlowWorkUtils.isSuccess(lendingResultEvent.getLoanResultVo().getLoanStatus())) {
            log.info(">>>>>>>>>>>>>添加记账任务<<<<<<<<<<<<<<<:{}", lendingResultEvent.getLoanResultVo().getApplSeq());
            processerJobService.addAccountRecordJob(lendingResultEvent.getLoanResultVo());
        }
        try {
            cursorService.deleteByContractNo(lendingResultEvent.getAssetsSplit().getContractNo());
        } catch (Exception ex) {
            log.error("删除临时表出现异常：", ex);
        }
        try {
            AssetsSplit assetsSplit = assetsSplitService.get(lendingResultEvent.getAssetsSplit().getId(),false);
            AssetsSplit assetsSplitCache = assetsSplitService.get(lendingResultEvent.getAssetsSplit().getId());
            if(!FlowWorkUtils.isFinalState(assetsSplit)) {
                log.info("业务单号：{} 状态：{}",assetsSplit.getApplSeq(),assetsSplit.getLoanStatus());
                if(FlowWorkUtils.isSuccess(assetsSplitCache)){
                    assetsSplitService.updateLoanStatusById(assetsSplit,PL0506Enum.PL0506_5_50);
                }else if(FlowWorkUtils.isFail(assetsSplitCache)){
                    assetsSplitService.updateLoanStatusById(assetsSplit,PL0506Enum.PL0506_6_60);
                }
            }
            streamSender.outputCmisLendingResult(lendingResultEvent.toString());
            //lendingResultCache.put(appSeq);
            log.info("放款结果成功通知信贷，LoanListener.execute; params:" + lendingResultEvent.toString());
        } catch (Exception ex) {
            log.error("推送信贷时，链接MQ异常：", ex);
        }
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
