package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.processer.server.enums.LoanStatusEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.event.MakeLoansEvent;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.QuotaService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * > 临时表处理
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/21 16:54
 */
@Slf4j
public class CursorLogicTask implements IJobExecutor, Serializable {
    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.CursorLogicTask";

    private AssetsSplitService assetsSplitService;
    private AssetsSplitItemService assetsSplitItemService;
    private QuotaService quotaService;

    private void init() {
        this.assetsSplitItemService = SpringContextHolder.getBean(AssetsSplitItemService.class);
        this.assetsSplitService = SpringContextHolder.getBean(AssetsSplitService.class);
        this.quotaService = SpringContextHolder.getBean(QuotaService.class);
    }

    /**
     * @param jobContext {"contractNo":"","assetsSplitItemId":"","cursorId":"","cooprAgencyId":"","certCode":""}
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(String jobContext) {
        this.init();
        Map<String, Object> map = JsonUtils.readObjectByJson(jobContext, Map.class);
        String contractNo = MapUtils.getString(map, "contractNo");
        String assetsSplitItemId = MapUtils.getString(map, "assetsSplitItemId");
        String cooprAgencyId = MapUtils.getString(map, "cooprAgencyId");
        String certCode = MapUtils.getString(map, "certCode");
        AssetsSplit assetsSplit = assetsSplitService.selectBycontractNo(contractNo);
        if (FlowWorkUtils.isFinalState(assetsSplit)) {
            return true;
        }
        log.info("临时表处理---------,业务单号：{},资金状态：{}", assetsSplit.getApplSeq(), assetsSplit.getLoanStatus());
        //网关放款成功判断
        if (LoanStatusEnum.LOAN_INDEPENDENT_SUCCESS.getCode().equals(assetsSplit.getLoanStatus())) {
            log.info("临时表处理,网关放款成功，业务单号：{},  支付网关放款成功;loanStatus：[{}]", assetsSplit.getLoanStatus());
            AssetsSplitItem assetsSplitItem = assetsSplitItemService.get(assetsSplitItemId);
            Quota quota = quotaService.selectByAgencyIdAndCertCode(cooprAgencyId, certCode);
            //1.调用工行，放款
            MakeLoansEvent makeLoansEvent = new MakeLoansEvent(this, assetsSplit, assetsSplitItem, quota);
            SpringContextHolder.getApplicationContext().publishEvent(makeLoansEvent);
            return true;
        } else {
            log.info("业务单号：{},非放款中，跳过执行临时表", assetsSplit.getApplSeq());
        }
        return false;
    }
}
