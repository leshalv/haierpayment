package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.common.utils.JsonUtils;
import cn.jbinfo.integration.busflow.context.BusContext;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.enums.CreditStatusEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditSearchRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditQueryRequestVo;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.QuotaService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * > 授信状态查询
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/19 11:28
 */
@Slf4j
public class CreditStatusTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.CreditStatusTask";


    private AssetsSplitItemService assetsSplitItemService;

    private AssetsSplitService assetsSplitService;

    private CreditSearchRestApi creditSearchRestApi;

    private QuotaService quotaService;

    private void init() {
        this.quotaService = SpringContextHolder.getBean(QuotaService.class);
        this.assetsSplitService = SpringContextHolder.getBean(AssetsSplitService.class);
        this.assetsSplitItemService = SpringContextHolder.getBean(AssetsSplitItemService.class);
        this.creditSearchRestApi = SpringContextHolder.getBean(CreditSearchRestApi.class);
    }

    /**
     * @param jobContext {"versionId":"v1","orgCorpMsgId":"","assetsSplitItemId":"","cinoMemno":"",}
     * @return
     */
    @Override
    public boolean execute(String jobContext) {
        log.info("job>>>>>>>>>>>>>>>>开始执行授信状态查询任务<<<<<<<<<<<<<<");
        this.init();
        Map<String, Object> map = JsonUtils.readObjectByJson(jobContext, Map.class);
        String versionId = MapUtils.getString(map, "versionId");
        String orgCorpMsgId = MapUtils.getString(map, "orgCorpMsgId");
        //资产拆分明细表主键ID
        String assetsSplitItemId = MapUtils.getString(map, "assetsSplitItemId");
        AssetsSplitItem assetsSplitItem = assetsSplitItemService.get(assetsSplitItemId);
        if (!PL0601Enum.PL0601_3_20.getCode().equals(assetsSplitItem.getStatus())) {
            log.info("job>>>>>>>>>>>>>>>>停止执行授授信状态查询任务,资产表id{}，明细表状态不是额度审批中，状态为{}<<<<<<<<<<<<<<", assetsSplitItem.getAssetsSplitId(), assetsSplitItem.getStatus());
            return true;
        }
        AssetsSplit assetsSplit = assetsSplitService.get(assetsSplitItem.getAssetsSplitId());
        if (assetsSplit == null || FlowWorkUtils.isFinalState(assetsSplit)) {
            log.info("job>>>>>>>>>>>>>>>>停止执行授授信状态查询任务,该笔业务不存在或者资产表状态是终态<<<<<<<<<<<<<<");
            return true;
        }
        Quota quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
        log.info("业务编号{}，授信申请查询，根据原消息id:{},所查询的额度信息：{}", assetsSplit.getApplSeq(), orgCorpMsgId, JsonUtils.safeObjectToJson(quota));
        if (quota == null)
            return true;
        try {
            //授信申请查询，如果原消息ID为空，则忽略此条数据
            if (StringUtils.isBlank(quota.getOrgCorpMsgId())) {
                log.info("业务编号{}，授信申请查询，原消息ID为空，则忽略此条数据，停止轮询", assetsSplit.getApplSeq());
                return true;
            }
            RestRequestHead head = new RestRequestHead("PLCO002", "CAPTIAL");
            CreditQueryRequestVo requestVo = new CreditQueryRequestVo();
            requestVo.setQryType("02");//授信申请查询
            requestVo.setOrgCorpMsgId(quota.getOrgCorpMsgId());
            requestVo.setCooprUserId(assetsSplit.getCertCode());
            RestRequest<CreditQueryRequestVo> restRequest = new RestRequest<>(head, requestVo);
            //授信申请查询
            RestResponse<ResCreditBackVo> restResponse;
            try {
                restResponse = creditSearchRestApi.queryCredit(versionId, restRequest);
            } catch (Exception ex) {
                log.error("业务编号{}，调用前置查询授信异常,继续轮询", assetsSplit.getApplSeq(), ex);
                return false;
            }

            CreditBackContext creditBackContext = new CreditBackContext(assetsSplit, assetsSplitItem, restResponse, quota, orgCorpMsgId);
            FlowWorkerServer flowWorkerServer = SpringContextHolder.getBean(FlowWorkerServer.class);
            BusContext busContext = flowWorkerServer.creditApplyBack(creditBackContext,this.getClass().getName());
            CreditStatusEnum creditStatusEnum = (CreditStatusEnum) busContext.getValue("back_status");
            if (creditStatusEnum != null) {
                return creditStatusEnum != CreditStatusEnum.CREDIT_APPROVAL;
            }
            return false;
        } catch (RetryableException ex) {
            log.error("业务编号{}，查询支授信申请异常，将继续执行本次任务：", assetsSplit.getApplSeq(), ex);
            return false;
        }
    }
}
