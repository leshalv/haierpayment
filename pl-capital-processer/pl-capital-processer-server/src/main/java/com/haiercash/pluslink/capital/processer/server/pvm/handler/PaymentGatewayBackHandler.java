package com.haiercash.pluslink.capital.processer.server.pvm.handler;

import cn.jbinfo.integration.busflow.context.BusContext;
import cn.jbinfo.integration.busflow.station.BusParameter;
import com.haiercash.pluslink.capital.common.utils.SequenceUtil;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.enums.dictionary.*;
import com.haiercash.pluslink.capital.processer.server.enums.LoanStatusEnum;
import com.haiercash.pluslink.capital.processer.server.enums.ResponseStatusEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.PVM;
import com.haiercash.pluslink.capital.processer.server.pvm.event.LendingResultEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.event.MakeLoansEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.PaymentGatewayBackContext;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.CommonDaoService;
import com.haiercash.pluslink.capital.processer.server.service.QuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 支付网关回调事件
 *
 * @author xiaobin
 * @create 2018-08-03 上午11:31
 **/
@Slf4j
public class PaymentGatewayBackHandler extends IHandler {

    /**
     * 任务状态
     * 联合处理、终态 返回 1
     */
    public static final String HANDLE_STATUS = "handle_status";


    public void doStart(@BusParameter("paymentGatewayBackContext") PaymentGatewayBackContext paymentGatewayBackContext, BusContext busContext) {
        busContext.put(METHOD_NAME, "支付网关回调");

        AssetsSplit assetsSplit = paymentGatewayBackContext.getAssetsSplit();
        String tradeStatus = paymentGatewayBackContext.getTradeStatus();
        Object target = busContext.getValue(PVM.TARGET);
        if (Objects.nonNull(target)) {
            log.info(">>>支付网关回调,业务单号：{},是否联合放款：{}<<<<<<<<，网关返回状态：{},目标类：{}", assetsSplit.getApplSeq(), assetsSplit.getProjectType(), tradeStatus, target);
        } else {
            log.info(">>>支付网关回调,业务单号：{},是否联合放款：{}<<<<<<<<，网关返回状态：{}", assetsSplit.getApplSeq(), assetsSplit.getProjectType(), tradeStatus);
        }


        if (FlowWorkUtils.isFinalState(assetsSplitService.get(assetsSplit.getId(), false))) {
            log.info("业务编号：{},数据库状态为终态，跳过后续执行步骤！", assetsSplit.getApplSeq());
            busContext.put(HANDLE_STATUS, "-1");
            return;
        }
        if (PL0505Enum.PL0505_3_UNKNOW.getCode().equals(assetsSplit.getProjectType())) {
            log.info("业务编号：{}，是否联合放款，状态未知，状态编号：{},不做任何处理", assetsSplit.getApplSeq(), assetsSplit.getProjectType());
            return;
        }


        busContext.put("applSeq", assetsSplit.getApplSeq());

        busContext.put(HANDLE_STATUS, "1");
        //联合处理中
        if (LoanStatusEnum.LOAN_UNION_HANDING.getCode().equals(assetsSplit.getLoanStatus())) {
            log.info("业务编号：{},联合处理中,tradeStatus：{}", assetsSplit.getApplSeq(), tradeStatus);
            return;
        }
        String projectType = assetsSplit.getProjectType();

        paymentGatewayBackContext.setAssetsSplit(assetsSplit);
        //处理成功
        if (ResponseStatusEnum.SUCCESS.getCode().equals(tradeStatus)) {
            //联合放款
            if (PL0505Enum.PL0505_1_UNION.getCode().equals(projectType)) {
                log.info("业务编号：{},处理成功：走联合放款，tradeStatus：{}", assetsSplit.getApplSeq(), tradeStatus);
                setHandler(busContext, assetsSplit.getApplSeq(), "支付网关回调检测：联合放款");
                unionLoan(paymentGatewayBackContext, busContext);
            } else if (PL0505Enum.PL0505_2_NOT_UNION.getCode().equals(projectType)) {//非联合放款
                log.info("业务编号：{},处理成功：非联合放款，tradeStatus：{}", assetsSplit.getApplSeq(), tradeStatus);
                setHandler(busContext, assetsSplit.getApplSeq(), "支付网关回调检测：非联合放款");
                notUnionLoan(paymentGatewayBackContext, busContext);
            }
        } else if (ResponseStatusEnum.FAIL.getCode().equals(tradeStatus)) {//放款失败
            log.info("业务编号：{},放款失败，tradeStatus：{}", assetsSplit.getApplSeq(), tradeStatus);
            setHandler(busContext, assetsSplit.getApplSeq(), "支付网关回调检测：放款失败");
            failLoan(paymentGatewayBackContext, busContext);
        } else if (ResponseStatusEnum.PROCESSING.getCode().equals(tradeStatus)) {//支付网关：处理中
            log.info("业务编号：{},支付网关：处理中,tradeStatus：{}", assetsSplit.getApplSeq(), tradeStatus);
            busContext.put(HANDLE_STATUS, "-1");
        }
    }


    /**
     * 放款成功
     * <p>
     * 联合放款
     */
    public void unionLoan(@BusParameter("paymentGatewayBackContext") PaymentGatewayBackContext paymentGatewayBackContext, BusContext busContext) {
        AssetsSplit assetsSplit = paymentGatewayBackContext.getAssetsSplit();

        AssetsSplitItem assetsSplitItem = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo2());
        //插入一条自有放款明细（联合放款）
        AssetsSplitItem assetsSplitItemSelf = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
        if (assetsSplitItemSelf == null) {
            assetsSplitItemService.insertUnionAssetsSplitItemInternal(assetsSplit, assetsSplitItem, PL0601Enum.PL0601_11_31, "网关放款成功，联合放款，插入一条自有明细");
        } else {
            assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_11_31, "网关放款成功，联合放款，更新自有明细");
        }
        log.info("联合放款成功插入自有放款明细，明细数据：{}", assetsSplitItem);
        //额度审批通过,调用工行放款
        if (PL0601Enum.PL0601_5_22.getCode().equals(assetsSplitItem.getStatus())) {
            assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.PL0506_4_40, PL0505Enum.PL0505_1_UNION);
            Quota quota =
                    quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
            //调用工行，放款
            log.info("业务单号：{},通知工行放款", assetsSplit.getApplSeq());
            MakeLoansEvent makeLoansEvent = new MakeLoansEvent(this, assetsSplit, assetsSplitItem, quota);
            applicationEventPublisher.publishEvent(makeLoansEvent);
        }
    }

    /**
     * 放款成功
     * <p>
     * 非联合放款
     */
    public void notUnionLoan(@BusParameter("paymentGatewayBackContext") PaymentGatewayBackContext paymentGatewayBackContext, BusContext busContext) {
        AssetsSplit assetsSplit = paymentGatewayBackContext.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
        if (assetsSplitItem == null) {
            assetsSplitItem = assetsSplitItemService.insertAssetsSplitItemInternal(assetsSplit, PL0601Enum.PL0601_11_31, "网关放款成功,非联合放款，插入一条自有明细");
        } else {
            assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_11_31, "");
        }
        log.info("非联合放款，更新资产表状态，资产明细表状态，业务编号：{},资产表状态：{}", assetsSplit.getApplSeq(), PL0506Enum.PL0506_5_50.getCode());
        //通知信贷
        List<AssetsSplitItem> assetsSplitItems = assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplitItem.getAssetsSplitId());
        assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.PL0506_5_50, PL0505Enum.PL0505_2_NOT_UNION);
        LendingResultEvent lendingResultEvent = new LendingResultEvent(this, assetsSplit, assetsSplitItems);
        applicationEventPublisher.publishEvent(lendingResultEvent);
    }

    /**
     * 放款失败
     */
    public void failLoan(@BusParameter("paymentGatewayBackContext") PaymentGatewayBackContext paymentGatewayBackContext, BusContext busContext) {
        AssetsSplit assetsSplit = paymentGatewayBackContext.getAssetsSplit();
        //联合放款
        if (assetsSplit.getProjectType().equals(PL0505Enum.PL0505_1_UNION.getCode())) {
            AssetsSplitItem assetsSplitItem = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo2());
            AssetsSplitItem assetsSplitItemSelf = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
            if (assetsSplitItemSelf == null) {
                log.info("联合放款，放款失败，插入一条自有明细，业务编号{}", assetsSplit.getApplSeq());
                assetsSplitItemService.insertUnionAssetsSplitItemInternal(assetsSplit, assetsSplitItem, PL0601Enum.PL0601_12_32, "网关放款失败，联合放款，插入一条自有明细");
            } else {
                log.info("联合放款，放款失败，更新自有明细，业务编号{}", assetsSplit.getApplSeq());
                assetsSplitItemService.updateStatusById(assetsSplitItemSelf, PL0601Enum.PL0601_12_32, "网关放款失败，联合放款，更新自有明细");
            }
            assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.PL0506_6_60, PL0505Enum.PL0505_1_UNION);
        } else {//非联合放款
            AssetsSplitItem assetsSplitItem = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
            if (assetsSplitItem == null) {
                assetsSplitItem = new AssetsSplitItem();
                String assetsSplitItemID = SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT.getTypeName(), commonDaoService.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getSeqName()));
                assetsSplitItem.setId(assetsSplitItemID);
                assetsSplitItem.setCreateDate(new Date());
                assetsSplitItem.setStatus(PL0601Enum.PL0601_12_32.getCode());
                assetsSplitItem.setTransAmt(assetsSplit.getTradeAmount());
                assetsSplitItem.setApplAmt(assetsSplit.getTradeAmount());
                assetsSplitItem.setAgencyRate(BigDecimal.ONE);
                assetsSplitItem.setLoanNo(assetsSplit.getLoanNo1());
                assetsSplitItem.setUpdateDate(new Date());
                assetsSplitItem.setCapLoanNo("");
                assetsSplitItem.setMemo(PL0601Enum.PL0601_12_32.getDesc());
                assetsSplitItem.setLoanType(PL0602Enum.PL0602_1_OWN.getCode());
                assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
                assetsSplitItem.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
                assetsSplitItemService.insertAssetsSplitItem(assetsSplitItem);
                log.info("非联合放款，放款失败，插入一条自有明细，业务编号:{}", assetsSplit.getApplSeq());
            } else {
                assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_12_32, "");
                log.info("非联合放款，放款失败，更新明细表状态，业务编号:{}，状态:{}", assetsSplit.getApplSeq(), PL0601Enum.PL0601_12_32.getCode());
            }
            assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.PL0506_6_60, PL0505Enum.PL0505_2_NOT_UNION);
        }


        log.info("放款失败，更新资产表状态，业务编号:{}，状态:{}", assetsSplit.getApplSeq(), PL0506Enum.PL0506_6_60.getCode());
        //通知信贷
        List<AssetsSplitItem> assetsSplitItems = assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplit.getId());
        LendingResultEvent lendingResultEvent = new LendingResultEvent(this, assetsSplit, assetsSplitItems);
        applicationEventPublisher.publishEvent(lendingResultEvent);
    }

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    @Autowired
    private AssetsSplitService assetsSplitService;

    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private CommonDaoService commonDaoService;
}
