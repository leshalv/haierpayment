package com.haiercash.pluslink.capital.processer.server.pvm.handler;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import cn.jbinfo.integration.busflow.context.BusContext;
import cn.jbinfo.integration.busflow.station.BusParameter;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Cursor;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.enums.dictionary.PL0106Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0505Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.enums.CreditStatusEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.PVM;
import com.haiercash.pluslink.capital.processer.server.pvm.event.LendingResultEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.event.MakeLoansEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditApplContext;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditApplRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditSearchRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditQueryRequestVo;
import com.haiercash.pluslink.capital.processer.server.service.*;
import com.haiercash.pluslink.capital.processer.server.task.context.CreditApplyTaskContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 授信申请
 *
 * @author xiaobin
 * @create 2018-08-03 上午9:30
 **/
@Slf4j
public class CreditApplyHandler extends IHandler {

    /**
     * 系统异常
     */
    private static final String PROCESS_OF_ERROR = "PLCO3201";


    private static final String PROCESS_OF_FIELD_ERROR = "PLCO3501";

    /**
     * 通信异常
     */
    public static final String PROCESS_OF_TCP_ERROR = "PLCO3505";

    /**
     * 不在营业时间
     */
    public static final String PROCESS_OF_TIME_ERROR = "PLCO3101";

    /**
     * 正在受理中
     */
    public static final String BUSINESS_AMOUNT_APPLICATION = "PLCO3244";

    /**
     * 处理失败
     */
    public static final String PROCESS_OF_SYSTEM_ERROR = "99999";

    /**
     * 额度检测
     */
    public void checkQuota(@BusParameter(PVM.CREDIT_APPLY_CONTEXT) CreditApplContext creditApplContext, BusContext busContext) {
        AssetsSplit assetsSplit = creditApplContext.getAssetsSplit();
        if (FlowWorkUtils.isFinalState(assetsSplit)) {
            return;
        }

        Object target = busContext.getValue(PVM.TARGET);
        if (Objects.nonNull(target)) {
            log.info(">>>查询个人额度表,业务单号：{},网关放款状态:{},目标类：{}", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus(), target);
        } else {
            log.info(">>>查询个人额度表,业务单号：{},网关放款状态:{}", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus());
        }


        //网关放款中
        if (assetsSplit.getLoanStatus().equals(PL0506Enum.PL0506_2_20.getCode())) {
            processerJobService.addTradeStatusSearchJob(assetsSplit);
            assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.PL0506_2_20);
        }
        busContext.put(METHOD_NAME, "授信申请");
        busContext.put("applSeq", assetsSplit.getApplSeq());
        AssetsSplitItem assetsSplitItem = creditApplContext.getAssetsSplitItem();
        CreditApplRequestVo creditApplRequestVo = creditApplContext.getCreditApplRequestVo();
        setHandler(busContext, assetsSplit.getApplSeq(), "授信前，额度检测");

        Quota quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), creditApplRequestVo.getCertCode());

        if (quota != null && StringUtils.isNotBlank(quota.getCinoMemno())) {
            RestRequestHead head = new RestRequestHead("PLCO002", "CAPTIAL");
            CreditQueryRequestVo requestVo = new CreditQueryRequestVo();
            requestVo.setQryType("01");//普通查询
            requestVo.setCooprUserId(assetsSplit.getCertCode());
            requestVo.setCinoMemno(quota.getCinoMemno());
            RestRequest<CreditQueryRequestVo> restRequest = new RestRequest<>(head, requestVo);
            //授信申请查询
            RestResponse<ResCreditBackVo> restResponse;
            try {
                restResponse = creditSearchRestApi.queryCredit("v1", restRequest);
            } catch (Exception ex) {
                log.error("授信查询出现异常,走授信申请，异常信息：", ex);
                creditApply(creditApplContext, busContext);
                return;
            }
            //校验授信返回后：
            //1:额度是否充足
            CreditBackContext creditBackContext = new CreditBackContext(assetsSplit, assetsSplitItem, restResponse, null, null);
            busContext.put(PVM.CREDIT_BACK_CONTEXT, creditBackContext);
            if (CommonReturnCodeEnum.TRADE_SUCCESS.getCode().equals(restResponse.getHead().getRetFlag())) {
                ResCreditBackVo resCreditBackVo = restResponse.getBody();
                if (resCreditBackVo == null) {
                    creditApply(creditApplContext, busContext);
                    return;
                }
                busContext.put(PVM.CREDIT_BACK_CONTEXT, resCreditBackVo);
                BigDecimal valffamt = resCreditBackVo.getValffamt();
                if (assetsSplitItem.getTransAmt().compareTo(valffamt) > 0) {
                    log.info(">>>授信查询：>>>>>{}，额度不足，可贷额度：{},申请额度：{}", assetsSplit.getApplSeq(), valffamt, assetsSplitItem.getTransAmt());
                    //approvalNotPass(creditBackContext, busContext);
                    creditApply(creditApplContext, busContext);
                } else {
                    log.info(">>>>>>>>>>>>>>>>>{},额度充足,业务单号<<<<<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
                    approvalQuotaPassAdequate(creditBackContext, busContext);
                }
            } else {
                creditApply(creditApplContext, busContext);
            }
        } else {

            creditApply(creditApplContext, busContext);
        }
    }

    /**
     * 授信申请
     */
    public void creditApply(@BusParameter(PVM.CREDIT_APPLY_CONTEXT) CreditApplContext creditApplContext, BusContext busContext) {
        AssetsSplit assetsSplit = creditApplContext.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = creditApplContext.getAssetsSplitItem();
        CreditApplRequestVo creditApplRequestVo = creditApplContext.getCreditApplRequestVo();
        setHandler(busContext, assetsSplit.getApplSeq(), "授信申请");
        //----------------1:更新资产、资产明细状态----------------
        log.info(">>>>>>>>>>>>>>>>更新资产、资产明细状态,业务单号：{}<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());

        RestRequestHead head = new RestRequestHead("PLCO001", "CAPTIAL");
        RestRequest<CreditApplRequestVo> restRequest = new RestRequest<>(head, creditApplRequestVo);
        //授信申请
        RestResponse<ResCreditBackVo> restResponse;
        try {
            restResponse = creditApplRestApi.appFor("v1", restRequest);
        } catch (Exception ex) {
            log.info("调用前置授信出现异常，将添加到任务调度，业务单号：{}", assetsSplit.getApplSeq());
            log.error("调用前置授信出现异常：", ex);
            CreditApplyTaskContext creditApplyTaskContext = new CreditApplyTaskContext(assetsSplit, assetsSplitItem, restRequest);
            processerJobService.addCreditApplyJob(creditApplyTaskContext, assetsSplit.getApplSeq());
            return;

        }
        CreditBackContext creditBackContext = new CreditBackContext(assetsSplit, assetsSplitItem, restResponse, null, creditApplRequestVo.getCorpMsgId());
        creditApplyBacking(creditBackContext, busContext);
    }

    /**
     * 授信返回结果检测
     */
    public void creditApplyBacking(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext,
                                   BusContext busContext) {
        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        //AssetsSplitItem assetsSplitItem = creditBackContext.getAssetsSplitItem();

        RestResponse<ResCreditBackVo> restResponse = creditBackContext.getCreditBackVoRestResponseVo();

        //处理失败
        if (restResponse == null) {
            log.info("业务单号:{},前置数据返回空，执行:{}", assetsSplit.getApplSeq(), PVM.FAIL);
            busContext.setRoutingKey(PVM.FAIL);
            return;
        }
        log.info("业务单号：{},前置授信返回：{}", assetsSplit.getApplSeq(), JsonUtils.safeObjectToJson(restResponse));
        //ResCreditBackVo applyResponseVo = restResponse.getBody();
        //处理失败、不在营业时间、系统异常

        busContext.put(PVM.CREDIT_BACK_CONTEXT, creditBackContext);

        //
        creditApplyBackTo(creditBackContext, restResponse, busContext);
    }

    /**
     * 链接前置授信后，等待事件
     */
    public void creditApplyWaitFor(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {
        RestResponse<ResCreditBackVo> restResponse = creditBackContext.getCreditBackVoRestResponseVo();
        ResCreditBackVo resCreditBackVo = restResponse.getBody();
        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = creditBackContext.getAssetsSplitItem();
        setHandler(busContext, assetsSplit.getApplSeq(), "链接前置授信后，挂起事件");
        String cinoMemno = "", cooprUserId = "", corpMsgId = creditBackContext.getCorpMsgId();
        if (resCreditBackVo != null) {
            cinoMemno = resCreditBackVo.getCinoMemno();
            cooprUserId = resCreditBackVo.getCooprUserId();
            if (StringUtils.isBlank(corpMsgId))
                corpMsgId = resCreditBackVo.getCorpMsgId();
        }
        assetsSplitItemService.updateStatusById(assetsSplitItem, PL0601Enum.PL0601_3_20, "");
        log.info(">>>>>>>>>链接前置授信后，等待事件,合作机构id:{},证件号:{},业务单号：{},额度编号:{}",
                assetsSplitItem.getAgencyId(), assetsSplit.getCertCode(),
                assetsSplit.getApplSeq(), cinoMemno);

        Quota quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
        if (quota == null || StringUtils.isBlank(quota.getId())) {
            quota = new Quota();
            quota.setCinoMemno(cinoMemno);
            quota.setCooprUserId(cooprUserId);
            quota.setAssetsSplitItemId(assetsSplitItem.getId());
            quota.setApplDate(DateUtils.format(DateUtils.PATTERN_YYYY_MM_DD));
            quota.setCooprAgencyId(assetsSplitItem.getAgencyId());
            quota.setCertCode(assetsSplit.getCertCode());
            quota.setOrgCorpMsgId(corpMsgId);
            quota.setStatus(Quota.APPLYING);
            quotaService.insert(quota);
        } else {
            quota.setAssetsSplitItemId(assetsSplitItem.getId());
            if (StringUtils.isNotBlank(cinoMemno)) {
                quota.setCinoMemno(cinoMemno);
                quota.setCooprUserId(cooprUserId);
                quota.setStatus(Quota.BACK_OK);
                quotaService.updateByCallBackData(quota);
            }
        }
        //授信查询 ：定时任务
        log.info(">>>>>>>>>>>>>>>业务单号：{},添加授信查询定时任务<<<<<<<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
        processerJobService.addCreditStatusSearchJob(quota, assetsSplit.getApplSeq());
    }

    /**
     * 授信回调检测
     */
    public void creditApplyBackCheck(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {
        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        if (FlowWorkUtils.isFinalState(assetsSplit)) {
            busContext.put("back_status", CreditStatusEnum.CREDIT_HEAD_SUCCESS);
            return;
        }
        if (PL0506Enum.PL0506_4_40.getCode().equals(assetsSplit.getLoanStatus())) {
            busContext.put("back_status", CreditStatusEnum.CREDIT_HEAD_SUCCESS);
            return;
        }
        busContext.put(METHOD_NAME, "授信回调检测");

        setHandler(busContext, assetsSplit.getApplSeq(), "授信回调检测");

        RestResponse<ResCreditBackVo> restResponse = creditBackContext.getCreditBackVoRestResponseVo();

        ResCreditBackVo backResponseVoDTO = creditBackContext.getCreditBackVoRestResponseVo().getBody();

        busContext.put(PVM.CREDIT_BACK_CONTEXT, creditBackContext);

        busContext.put("back_status", CreditStatusEnum.CREDIT_FAILED);

        log.info("业务单号：{},授信回调检测,前置返回状态码：{}，额度编号：{}", assetsSplit.getApplSeq(), restResponse.getHead().getRetFlag(), backResponseVoDTO.getCinoMemno());

        if (CommonReturnCodeEnum.TRADE_SUCCESS.getCode().equals(restResponse.getHead().getRetFlag())) {
            log.info("业务单号：{},授信成功,授信金额：{}", assetsSplit.getApplSeq(), backResponseVoDTO.getValffamt());
            if (StringUtils.isBlank(backResponseVoDTO.getCinoMemno())) {
                busContext.put("back_status", CreditStatusEnum.CREDIT_APPROVAL);
            } else if (backResponseVoDTO.getValffamt() == null) {
                approvalNotPass(creditBackContext, busContext);
            } else {
                creditApplyPass(creditBackContext, busContext);
                busContext.put("back_status", CreditStatusEnum.CREDIT_HEAD_SUCCESS);
            }
        } else if (BUSINESS_AMOUNT_APPLICATION.equals(restResponse.getHead().getRetFlag())) {
            busContext.put("back_status", CreditStatusEnum.CREDIT_APPROVAL);
            log.info("业务单号：{},正在授信申请中---------", assetsSplit.getApplSeq());
        } else if (PROCESS_OF_TCP_ERROR.equals(restResponse.getHead().getRetFlag())) {
            //网络异常
            busContext.put("back_status", CreditStatusEnum.CREDIT_APPROVAL);
            log.info("业务单号：{},网络异常---------", assetsSplit.getApplSeq());
        } else {
            busContext.put(CREDIT_APPLY_BACK_ERROR, "处理失败、不在营业时间、系统异常");
            approvalNotPass(creditBackContext, busContext);
        }
    }

    private void creditApplyBackTo(CreditBackContext creditBackContext, RestResponse<ResCreditBackVo> restResponse, BusContext busContext) {
        switch (restResponse.getHead().getRetFlag()) {
            case PROCESS_OF_TIME_ERROR: {//非营业时间
                log.info("业务单号:{},前置数据返回状态：{}，执行:{}", creditBackContext.getAssetsSplit().getApplSeq(), restResponse.getHead().getRetFlag(), PVM.FAIL);
                busContext.put(CREDIT_APPLY_BACK_ERROR, "处理失败、不在营业时间、系统异常");
                creditApplyToPrepositionFail(creditBackContext, busContext, PL0601Enum.PL0601_14_12);
                break;
            }
            case PROCESS_OF_FIELD_ERROR: {//属性不合法
                creditApplyToPrepositionFail(creditBackContext, busContext, PL0601Enum.PL0601_15_13);
                break;
            }
            case PROCESS_OF_TCP_ERROR: {//通信异常
                creditApplyWaitFor(creditBackContext, busContext);
                break;
            }
            case PROCESS_OF_ERROR: {//系统繁忙
                creditApplyToPrepositionFail(creditBackContext, busContext, PL0601Enum.PL0601_15_13);
                break;
            }
            case PROCESS_OF_SYSTEM_ERROR: {//系统异常
                creditApplyToPrepositionFail(creditBackContext, busContext, PL0601Enum.PL0601_15_13);
                break;
            }
            case BUSINESS_AMOUNT_APPLICATION: {//正在受理中
                creditApplyWaitFor(creditBackContext, busContext);
                break;
            }
            case "00000": {
                if (restResponse.getBody() == null) {
                    log.info("业务单号：{},前置数据返回授信成功，消息id：{},包体为空，走系统等待事件", creditBackContext.getAssetsSplit().getApplSeq(), creditBackContext.getCorpMsgId());
                    creditApplyWaitFor(creditBackContext, busContext);
                } else {
                    if (restResponse.getBody().getValffamt() == null) {
                        creditApplyWaitFor(creditBackContext, busContext);
                    } else {
                        creditApplyPass(creditBackContext, busContext);
                    }
                }
                break;
            }
            default: {
                log.info("业务单号：{},前置数据返回状态：{}，执行:{}", creditBackContext.getAssetsSplit().getApplSeq(), restResponse.getHead().getRetFlag(), PVM.WAIT);
                creditApplyWaitFor(creditBackContext, busContext);
            }
        }
    }

    /**
     * 授信拒绝监听
     *
     * @param creditBackContext
     * @param busContext
     */
    public void approvalNotPass(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {
        busContext.put(CREDIT_APPLY_BACK_ERROR, "授信拒绝");

        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        log.info(">>>>>>>>>>>>>>>>审批拒绝监听器,业务单号:{}<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
        AssetsSplitItem splitItemDTO = creditBackContext.getAssetsSplitItem();
        setHandler(busContext, assetsSplit.getApplSeq(), "审批拒绝");
        RestResponse<ResCreditBackVo> creditBackVoRestResponseVo = creditBackContext.getCreditBackVoRestResponseVo();

        ResCreditBackVo backResponseVoDTO = creditBackVoRestResponseVo.getBody();
        if (backResponseVoDTO != null && StringUtils.isNotBlank(backResponseVoDTO.getCorpMsgId())) {
            //String corpMsgId = backResponseVoDTO.getCorpMsgId();
            Quota quota = quotaService.selectByAgencyIdAndCertCode(splitItemDTO.getAgencyId(), assetsSplit.getCertCode());
            quota.setCinoMemno(backResponseVoDTO.getCinoMemno());
            quota.setCooprUserId(backResponseVoDTO.getCooprUserId());
            quota.setAssetsSplitItemId(splitItemDTO.getId());
            quota.setStatus(Quota.BACK_OK);
            quotaService.updateByCallBackData(quota);
        }

        AssetsSplitItem assetsSplitItem = assetsSplitItemService.get(splitItemDTO.getId());
        AssetsSplit assetsSplitDto = assetsSplitService.get(assetsSplit.getId());
        if (FlowWorkUtils.isFinalState(assetsSplitDto)) {
            return;
        }
        //更新审批状态：审批拒绝
        assetsSplitItem.setMemo(PL0601Enum.PL0601_4_21.getDesc());
        assetsSplitItemService.updateStatusAndDelFlagById(splitItemDTO, PL0601Enum.PL0601_4_21,
                creditBackVoRestResponseVo.getHead().getRetMsg());
        approvalQuotaMergeHandler(creditBackContext, busContext);
        log.info("===============<处理中心>-,业务单号：{}，授信审批拒绝,修改资产明细表状态:{}", assetsSplit.getApplSeq(), PL0601Enum.PL0601_4_21.getCode());
    }

    /**
     * 授信失败
     */
    public void creditApplyToPrepositionFail(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext,
                                             BusContext busContext, PL0601Enum pl0601Enum) {
        busContext.put(CREDIT_APPLY_BACK_ERROR, "授信失败");

        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        log.info(">>>>>>>>>>>>>>>>审批拒绝监听器,业务单号：{}<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());

        setHandler(busContext, assetsSplit.getApplSeq(), "授信失败");
        AssetsSplitItem assetsSplitItem = creditBackContext.getAssetsSplitItem();
        /**
         * 更新资产表状态为
         */
        assetsSplitService.updateProjectTypeAndProdBuyOutById(
                assetsSplit, PL0505Enum.PL0505_2_NOT_UNION.getCode(), PL0106Enum.PL0106_1_NONSUPPORT.getCode());
        //更新审批状态：审批拒绝
        assetsSplitItem.setMemo(pl0601Enum.getDesc());
        assetsSplitItemService.updateStatusAndDelFlagById(assetsSplitItem, pl0601Enum, "工行额度审批异常");
        log.info("===============<处理中心>-业务单号：{},调用前置授信申请修改明细表状态额度审批拒绝,", assetsSplit.getApplSeq(), pl0601Enum.getCode());
        Quota quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
        CreditBackContext creditBackEvent = new CreditBackContext(assetsSplit, assetsSplitItem, null, quota, creditBackContext.getCorpMsgId());
        busContext.put(PVM.CREDIT_BACK_CONTEXT, creditBackEvent);

        approvalQuotaMergeHandler(creditBackEvent, busContext);
    }

    /**
     * 授信通过
     */
    public void creditApplyPass(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {

        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = creditBackContext.getAssetsSplitItem();
        RestResponse<ResCreditBackVo> resCreditBackVoRestResponse = creditBackContext.getCreditBackVoRestResponseVo();
        BigDecimal valffamt = resCreditBackVoRestResponse.getBody().getValffamt();

        setHandler(busContext, assetsSplit.getApplSeq(), "授信通过");
        ResCreditBackVo backResponseVoDTO = creditBackContext.getCreditBackVoRestResponseVo().getBody();

        String corpMsgId = backResponseVoDTO.getCorpMsgId();

        Quota quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
        log.info("授信通过，授信额度信息返回，业务单号：{},消息ID：{},资方客户协议号：{},消金客户ID:{}", assetsSplit.getApplSeq(), corpMsgId, backResponseVoDTO.getCinoMemno(), backResponseVoDTO.getCooprUserId());
        if (quota != null) {
            quota.setCinoMemno(backResponseVoDTO.getCinoMemno());
            quota.setCooprUserId(backResponseVoDTO.getCooprUserId());
            quota.setStatus(Quota.BACK_OK);
            quota.setAssetsSplitItemId(assetsSplitItem.getId());
            quotaService.updateByCallBackData(quota);
        } else {
            quota = new Quota();
            quota.setId(IdGen.uuid());
            quota.setAssetsSplitItemId(assetsSplitItem.getId());
            quota.setCinoMemno(backResponseVoDTO.getCinoMemno());
            quota.setApplDate(backResponseVoDTO.getActdate());
            quota.setCooprAgencyId(assetsSplitItem.getAgencyId());
            quota.setCertCode(assetsSplit.getCertCode());
            quota.setCooprUserId(backResponseVoDTO.getCooprUserId());
            quota.setStatus("1");
            quotaService.insert(quota);
        }

        //申请放款金额 > 可贷金额，则审批额度不足
        log.info(">>>业务号：{},申请人：{}>>>>>>>>授信通过,开始校验授信额度<<<，可贷金额：{},申请金额：{}", assetsSplit.getApplSeq(), assetsSplit.getCustName(),
                valffamt, assetsSplitItem.getTransAmt());
        if (assetsSplitItem.getTransAmt().compareTo(valffamt) > 0) {
            //额度不足
            log.info(">>>>>>>>>>>>>>>>>额度不足,业务单号：{}<<<<<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
            //busContext.setRoutingKey(PVM.QUOTA_NOT_PASS);
            approvalQuotaNotAdequate(creditBackContext, busContext);
        } else {
            log.info(">>>>>>>>>>>>>>>>>额度充足,业务单号：{}<<<<<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
            //busContext.setRoutingKey(PVM.QUOTA_PASS);
            approvalQuotaPassAdequate(creditBackContext, busContext);
        }
    }

    /**
     * 授信异常、授信驳回、额度不足、放款失败
     * 返回后事件
     *
     * @param creditBackContext
     * @param busContext
     */
    public void approvalQuotaMergeHandler(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {
        AssetsSplit assetsSplitDTO = creditBackContext.getAssetsSplit();
        log.info(">>>>>>>>>>>>>业务单号：{},授信异常、授信驳回、额度不足返回，开始校验网关是否放款成功<<<<<<<<<<<<<<<<", assetsSplitDTO.getApplSeq());
        AssetsSplitItem splitItemDTO = creditBackContext.getAssetsSplitItem();

        updateLoanStatus(assetsSplitDTO, splitItemDTO);
    }


    private void updateLoanStatus(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem) {
        //网关放款成功
        if (PL0506Enum.PL0506_3_30.getCode().equals(assetsSplit.getLoanStatus())) {
            AssetsSplitItem assetsSplitItemSelf = assetsSplitItemService.selectByAssetsSpiltId(assetsSplit.getId(), assetsSplit.getLoanNo1());
            if (assetsSplitItemSelf == null) {
                log.info("业务单号：{}，授信成功回调，网关放款成功，插入一条自有明细", assetsSplit.getApplSeq());
                assetsSplitItemService.insertAssetsSplitItemInternal(assetsSplit, PL0601Enum.PL0601_11_31, "网关放款成功,授信异常、不足、驳回，插入一条自有明细---");
            } else {
                assetsSplitItemService.updateBackData(assetsSplit, assetsSplitItemSelf, "网关放款成功，前置放款失败,更新自有明细");
            }
            log.info(">>>>>>>>>>>>>业务单号：{},网关放款成功<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
            assetsSplitService.updateLoanStatusAndProjectTypeAndProdBuyOutById(
                    assetsSplit, PL0506Enum.PL0506_5_50.getCode(), PL0505Enum.PL0505_2_NOT_UNION.getCode(), "");
            log.info("===============<处理中心>-业务单号：{},授信异常、授信驳回、额度不足返回，支付网关放款成功，非联合放款，修改资产表状态:{}", assetsSplit.getApplSeq(), PL0506Enum.PL0506_5_50.getCode());
            assetsSplit = assetsSplitService.get(assetsSplit.getId());
            //通知信贷
            List<AssetsSplitItem> assetsSplitItems = assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplitItem.getAssetsSplitId());
            LendingResultEvent lendingResultEvent = new LendingResultEvent(this, assetsSplit, assetsSplitItems);
            applicationEventPublisher.publishEvent(lendingResultEvent);
        } else if (PL0506Enum.PL0506_5_50.getCode().equals(assetsSplit.getLoanStatus())) {
            //通知信贷
            assetsSplit = assetsSplitService.get(assetsSplit.getId());
            List<AssetsSplitItem> assetsSplitItems = assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplitItem.getAssetsSplitId());
            LendingResultEvent lendingResultEvent = new LendingResultEvent(this, assetsSplit, assetsSplitItems);
            applicationEventPublisher.publishEvent(lendingResultEvent);

        } else {
            log.info(">>>>>>>>>>>>>业务单号：{},网关放款状态：{}<<<<<<<<<<<<<<<<", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus());
            assetsSplitService.updateProjectTypeAndProdBuyOutById(
                    assetsSplit, PL0505Enum.PL0505_2_NOT_UNION.getCode(), "");
        }
    }

    /**
     * 授信额度充足
     */
    public void approvalQuotaPassAdequate(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {
        busContext.put(METHOD_NAME, "授信额度充足");

        //额度充足--->审批通过
        //---------------审批通过，修改状态-----------------
        assetsSplitItemService.updateStatusById(creditBackContext.getAssetsSplitItem(), PL0601Enum.PL0601_5_22, "");
        AssetsSplit assetsSplit = assetsSplitService.get(creditBackContext.getAssetsSplit().getId());

        setHandler(busContext, assetsSplit.getApplSeq(), "授信额度充足");
        AssetsSplitItem assetsSplitItem = creditBackContext.getAssetsSplitItem();
        log.info("===<处理中心>-,业务单号：{}，调用授信回调额度充足修改资产明细表状态额度审批通过:{}", assetsSplit.getApplSeq(), PL0601Enum.PL0601_5_22.getCode());

        //网关放款成功
        if (assetsSplit.getLoanStatus().equals(PL0506Enum.PL0506_3_30.getCode())) {
            //更新资产状态为：联合放款中
            assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.PL0506_4_40, PL0505Enum.PL0505_1_UNION);
            log.info("===<处理中心>-业务单号：{},调用授信回调额度充足网关放款成功修改资产明状态联合付款中:{}", assetsSplit.getApplSeq(), PL0506Enum.PL0506_4_40.getCode());
            //----------------调用工行，放款----------------
            MakeLoansEvent makeLoansEvent = new MakeLoansEvent(this, creditBackContext.getAssetsSplit(), creditBackContext.getAssetsSplitItem(),
                    creditBackContext.getQuota());
            applicationEventPublisher.publishEvent(makeLoansEvent);
        } else {
            //插入临时表
            Cursor cursor = new Cursor();
            cursor.setId(IdGen.uuid());
            cursor.setContractNo(creditBackContext.getAssetsSplit().getContractNo());
            cursor.setAssetsSplitItemId(creditBackContext.getAssetsSplitItem().getId());
            cursor.setStatus("0");
            cursor.setSystemDate(new Date());
            cursorService.insert(cursor);
            //添加临时表处理相关任务,网关回调后，处理工行放款
            processerJobService.addCursorLogicJob(assetsSplit.getContractNo(), assetsSplitItem.getId(), cursor.getId(), assetsSplitItem.getAgencyId(), assetsSplit.getCertCode(), assetsSplit.getApplSeq());
        }
    }

    /**
     * 授信回调后
     * <p>
     * 额度不足事件
     *
     * @param creditBackContext
     * @param busContext
     */
    public void approvalQuotaNotAdequate(@BusParameter(PVM.CREDIT_BACK_CONTEXT) CreditBackContext creditBackContext, BusContext busContext) {
        busContext.put(METHOD_NAME, "授信额度不足");
        AssetsSplit assetsSplit = creditBackContext.getAssetsSplit();
        AssetsSplitItem splitItemDTO = creditBackContext.getAssetsSplitItem();
        setHandler(busContext, assetsSplit.getApplSeq(), "授信额度不足");
        AssetsSplitItem assetsSplitItem = assetsSplitItemService.get(splitItemDTO.getId());

        //更新审批状态：额度不足
        assetsSplitItem.setMemo(PL0601Enum.PL0601_6_23.getDesc());
        assetsSplitItemService.updateStatusAndDelFlagById(splitItemDTO, PL0601Enum.PL0601_6_23, "");
        approvalQuotaMergeHandler(creditBackContext, busContext);
        log.info("===============<处理中心>-,业务单号：{}，调用授信回调额度不足修改资产明细表状态:{}", assetsSplit.getApplSeq(), PL0601Enum.PL0601_6_23.getCode());
    }

    /**
     * 调用前置
     * <p>
     * 授信申请
     */
    @Autowired
    private CreditApplRestApi creditApplRestApi;

    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    /**
     * 临时表
     */
    @Autowired
    private CursorService cursorService;

    /**
     * 授信查询
     */
    @Autowired
    private CreditSearchRestApi creditSearchRestApi;

    @Autowired
    private ProcesserJobService processerJobService;

    /**
     * 额度信息
     */
    @Autowired
    private QuotaService quotaService;

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    @Autowired
    private AssetsSplitService assetsSplitService;
}
