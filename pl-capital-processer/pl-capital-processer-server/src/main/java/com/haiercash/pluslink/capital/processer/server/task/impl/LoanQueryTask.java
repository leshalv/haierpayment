package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.common.utils.JsonUtils;
import cn.jbinfo.integration.busflow.context.BusContext;
import com.haiercash.pluslink.capital.common.utils.SpringUtil;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.rest.client.LoanQueryClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanQueryRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * > 放款状态查询
 * 调用前置
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/25 17:52
 */
@Slf4j
public class LoanQueryTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.LoanQueryTask";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private AssetsSplitItemService assetsSplitItemService;
    private AssetsSplitService assetsSplitService;
    private LoanQueryClient loanQueryClient;

    private void init() {
        this.loanQueryClient = SpringUtil.getBean(LoanQueryClient.class);
        this.assetsSplitItemService = SpringUtil.getBean(AssetsSplitItemService.class);
        this.assetsSplitService = SpringUtil.getBean(AssetsSplitService.class);

    }

    /**
     * @param jobContext {"versionId":"v1","orgCorpMsgId":"","assetsSplitItemId":"","assetsSplitId":""}
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(String jobContext) {
        this.init();
        try {

            logger.info("LoanQueryServiceImpl.execute:前置放款状态查询;params:[" + jobContext + "]");
            Map<String, Object> map = JsonUtils.readObjectByJson(jobContext, Map.class);
            String versionId = MapUtils.getString(map, "versionId");
            String orgCorpMsgId = MapUtils.getString(map, "orgCorpMsgId");
            String assetsSplitId = MapUtils.getString(map, "assetsSplitId");
            String assetsSplitItemId = MapUtils.getString(map, "assetsSplitItemId");

            AssetsSplit assetsSplit = assetsSplitService.get(assetsSplitId);
            if (FlowWorkUtils.isFinalState(assetsSplit)) {
                log.info("job>>>>>>>>>>>>>>>>业务编号{},停止执行授授信状态查询任务,资产表状态是终态<<<<<<<<<<<<<<", assetsSplit.getApplSeq());
                return true;
            }
            RestRequest<LoanQueryRequestBody> restRequest = new RestRequest<>();
            RestRequestHead head = new RestRequestHead("PLCO009", "CAPTIAL");
            restRequest.setRequestHead(head);
            LoanQueryRequestBody loanQueryRequestBody = new LoanQueryRequestBody();
            loanQueryRequestBody.setOrgCorpMsgId(orgCorpMsgId);
            restRequest.setBody(loanQueryRequestBody);
            RestResponse<ResMakeLoansResponse> response;
            try {
                response = loanQueryClient.queryLoanInfo(versionId, restRequest);
            } catch (Exception ex) {
                log.error("业务编号，前置放款查询异常。继续任务：{}", assetsSplit.getApplSeq(), ex);
                return false;
            }
            logger.info("前置放款状态查询>>> ret:" + response.getHead().getRetFlag());

            AssetsSplitItem assetsSplitItem = assetsSplitItemService.get(assetsSplitItemId);
            LoanBackContext loanBackContext = new LoanBackContext(assetsSplit, assetsSplitItem, response);
            FlowWorkerServer flowWorkerServer = SpringContextHolder.getBean(FlowWorkerServer.class);
            BusContext busContext = flowWorkerServer.loanBack(loanBackContext,this.getClass().getName());
            return (Boolean) busContext.getValue("status");
        } catch (RetryableException ex) {
            log.error("前置放款状态查询异常，将继续执行本次任务：", ex);
            return false;
        }
    }
}
