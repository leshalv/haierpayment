package com.haiercash.pluslink.capital.processer.server.pvm.event;

import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.processer.server.pvm.EngineEvent;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.MakeLoansRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LoanDetailResponse;
import com.haiercash.pluslink.capital.processer.server.service.LoanDetailService;
import com.haiercash.pluslink.capital.processer.server.service.QuotaService;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 放款事件
 *
 * @author xiaobin
 * @create 2018-07-25 上午10:43
 **/
@Getter
public class MakeLoansEvent extends EngineEvent {

    //资产拆分主表
    private AssetsSplit assetsSplit;
    //资产拆分明细表（资方明细）
    private AssetsSplitItem assetsSplitItem;

    /**
     * 前置放款请求体
     */
    private MakeLoansRequest makeLoansRequest;


    public MakeLoansEvent(Object source, AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem, Quota quota) {
        super(source);
        this.assetsSplit = assetsSplit;
        this.assetsSplitItem = assetsSplitItem;

        LoanDetailService loanDetailService = SpringContextHolder.getBean(LoanDetailService.class);
        ApplInfoAppRequestBody requestBody = new ApplInfoAppRequestBody(assetsSplit.getApplSeq(), assetsSplit.getCertNo(), assetsSplit.getChannelNo(), assetsSplit.getRequestIp());

        //拉取贷款详情
        LoanDetailResponse loanDetailResponse = loanDetailService.selectApplInfoApp(requestBody);


        QuotaService quotaService = SpringContextHolder.getBean(QuotaService.class);
        if (quota == null) {
            quota = quotaService.selectByAgencyIdAndCertCode(assetsSplitItem.getAgencyId(), assetsSplit.getCertCode());
        }
        makeLoansRequest = new MakeLoansRequest();
        //贷款业务序号
        makeLoansRequest.setLoanBusino(assetsSplit.getLoanNo2());
        makeLoansRequest.setCinoMemno(quota.getCinoMemno());
        makeLoansRequest.setCooprUserId(assetsSplit.getCertCode());
        makeLoansRequest.setCorpLoanAppdt(loanDetailResponse.getApplyDt());
        makeLoansRequest.setPromAmtCust(assetsSplit.getOrigPrcp());
        makeLoansRequest.setAppLoanAmt(assetsSplitItem.getTransAmt());
        makeLoansRequest.setLoanInstlNum(NumberUtils.toInt(loanDetailResponse.getApplyTnr()));
        makeLoansRequest.setAccnm(assetsSplit.getCustName());
        makeLoansRequest.setMvblno(assetsSplit.getMobile().toString());
        makeLoansRequest.setConsOdrno(loanDetailResponse.getOrderNumber());
        makeLoansRequest.setConsComd(loanDetailResponse.getGoodsName());

    }
}
