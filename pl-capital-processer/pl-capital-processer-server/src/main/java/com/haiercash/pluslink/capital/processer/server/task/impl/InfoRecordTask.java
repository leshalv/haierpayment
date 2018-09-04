package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.common.utils.JsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.processer.server.job.JobException;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.InfoRecordRestClient;
import com.haiercash.pluslink.capital.processer.server.rest.client.LcApplyFileQueryClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.InfoRecordRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LcApplyFileQueryRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LcApplyFileQueryList;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LcApplyFileQueryResponse;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import com.haiercash.pluslink.capital.processer.server.service.FtpUploadService;
import com.haiercash.pluslink.capital.processer.server.service.QuotaService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobin
 * @create 2018-08-20 下午9:07
 **/
@Slf4j
public class InfoRecordTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.InfoRecordTask";

    private void init() {
        quotaService = SpringContextHolder.getBean(QuotaService.class);
        ftpUploadService = SpringContextHolder.getBean(FtpUploadService.class);
        lcApplyFileQueryClient = SpringContextHolder.getBean(LcApplyFileQueryClient.class);
        infoRecordRestClient = SpringContextHolder.getBean(InfoRecordRestClient.class);
    }

    @Override
    public boolean execute(String jobContext) throws Exception {
        init();
        LoanBackContext loanBackContext = JsonUtils.readObjectByJson(jobContext, LoanBackContext.class);
        AssetsSplit assetsSplit = loanBackContext.getAssetsSplit();
        AssetsSplitItem assetsSplitItem = loanBackContext.getAssetsSplitItem();
        RestResponse<ResMakeLoansResponse> responseRestResponse = loanBackContext.getRestResponse();

        LcApplyFileQueryRequest queryRequest = new LcApplyFileQueryRequest(assetsSplit.getApplSeq());
        Map<String, LcApplyFileQueryRequest> mapRequest = Maps.newHashMap();
        mapRequest.put("request", queryRequest);
        Map<String, LcApplyFileQueryResponse> mapResponse;
        try {
            mapResponse = lcApplyFileQueryClient.cmisfront(mapRequest);
        } catch (Exception ex) {
            log.error("个人贷款合同信息查询出现异常:", ex);
            throw new JobException("个人贷款合同信息查询出现异常:" + ex.getMessage());
        }
        if (mapResponse == null || mapResponse.get("response") == null) {
            log.info("业务单号:{},查询音像资料为空，跳过任务", assetsSplit.getApplSeq());
            throw new JobException("个人贷款合同信息查询:查询音像资料为空");
        }
        LcApplyFileQueryResponse queryResponse = mapResponse.get("response");
        Map<String, List<LcApplyFileQueryList>> fileQueryMap = queryResponse.getBody().getList();
        if (fileQueryMap == null || fileQueryMap.get("info") == null) {
            log.info("个人贷款信息：{}", JsonUtils.safeObjectToJson(queryResponse));
            throw new JobException("个人贷款合同信息查询:无附件信息");
        }

        Quota quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
        if (quota == null) {
            log.info("业务单号:{},额度信息为空，跳过任务", assetsSplit.getApplSeq());
            throw new JobException("额度信息为空，跳过任务");
        }


        List<LcApplyFileQueryList> fileQueryLists = fileQueryMap.get("info");
        List<String> fileList = Lists.newArrayList();
        fileQueryLists.forEach(file -> fileList.add(file.getAttachPath()));
        String filePath = null;
        try {
            log.info("业务单号：{},开始执行镜像资料（物理附件）ftp上传", assetsSplit.getApplSeq());
            filePath = ftpUploadService.uploadFile(fileList, assetsSplit.getApplSeq() + ".zip");
        } catch (Exception ex) {
            log.info("业务单号:" + assetsSplit.getApplSeq() + ",上传音像资料出现异常，跳过任务", ex);
        }
        if (StringUtils.isBlank(filePath)) {
            log.info("业务单号:{},物理磁盘音像资料为空，跳过任务", assetsSplit.getApplSeq());
            log.info("个人贷款信息：{}", JsonUtils.safeObjectToJson(queryResponse));
            throw new JobException("物理磁盘音像资料为空:跳过任务");
        }

        InfoRecordRequest infoRecordRequest = new InfoRecordRequest();
        infoRecordRequest.setCooprUserId(assetsSplit.getCertCode());
        infoRecordRequest.setLoanMemno(responseRestResponse.getBody().getLoanMemno());
        infoRecordRequest.setCinoMemno(quota.getCinoMemno());
        infoRecordRequest.setOrgLoanBusino(assetsSplitItem.getLoanNo());
        infoRecordRequest.setBusiInfo("音像资料");
        infoRecordRequest.setFiles(filePath);
        RestRequestHead requestHead = new RestRequestHead("PLCO005", "CAPTIAL");

        RestRequest<InfoRecordRequest> restRequest = new RestRequest<>(requestHead, infoRecordRequest);
        try {
            log.info("业务单号：{},开始执行镜像资料（物理数据）前置传输", assetsSplit.getApplSeq());
            infoRecordRestClient.makeLoans("v1", restRequest);
        } catch (Exception ex) {
            throw new JobException("调用前置，信息补录异常");
        }
        return true;
    }

    private QuotaService quotaService;

    private FtpUploadService ftpUploadService;

    /**
     * 个人贷款合同信息查询
     */
    private LcApplyFileQueryClient lcApplyFileQueryClient;

    /**
     * 信息补录
     */
    private InfoRecordRestClient infoRecordRestClient;


}
