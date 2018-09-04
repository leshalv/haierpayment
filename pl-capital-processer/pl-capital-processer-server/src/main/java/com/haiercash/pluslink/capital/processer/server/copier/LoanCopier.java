package com.haiercash.pluslink.capital.processer.server.copier;

import cn.jbinfo.cloud.core.copier.BeanTemplate;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.BalancePayInfo;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanBalancePayRequestInfo;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanFinancePayRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanAssetsSplitItemResponse;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanSearchResponse;

import java.util.List;

import static cn.jbinfo.cloud.core.copier.BeanTemplate.execute;

/**
 * 放款信息
 * <p>
 * vo-do
 * <p>
 * do-vo
 *
 * @author xiaobin
 * @create 2018-07-13 下午5:24
 **/
public class LoanCopier {

    /**
     * 放款请求Vo转资产表Do
     *
     * @param loanApplyForRequest,id主键
     * @return
     */
    public static AssetsSplit loanRequestVoToAssetsSplitDo(LoanApplyForRequest loanApplyForRequest) {
        AssetsSplit assetsSplit = BeanTemplate.executeBean(loanApplyForRequest, AssetsSplit.class);
        LoanFinancePayRequest loanFinancePayRequest = loanApplyForRequest.getFinancePayRequest();
        if (loanFinancePayRequest != null) {
            //财务公司付款记账信息
            assetsSplit.setIsHasMiddAcct(loanFinancePayRequest.getIsHasMiddAcct());
            assetsSplit.setInActno(loanFinancePayRequest.getInActno());
            assetsSplit.setInActname(loanFinancePayRequest.getInActname());
            assetsSplit.setInActno2(loanFinancePayRequest.getInActno2());
            assetsSplit.setInActname2(loanFinancePayRequest.getInActname2());
            assetsSplit.setIndCommpCode(loanFinancePayRequest.getIndCommpCode());
            assetsSplit.setPayCode(loanFinancePayRequest.getPayCode());
            assetsSplit.setJkrName(loanFinancePayRequest.getJkrName());
            assetsSplit.setActChannel(loanFinancePayRequest.getActChannel());
        }
        return assetsSplit;
    }

    /**
     * 放款请求余额支付信息Vo转余额支付信息Do
     *
     * @param balancePayList ,id主键
     * @return
     */
    public static List<BalancePayInfo> loanRequestVoToBalancePayInfoDo(List<LoanBalancePayRequestInfo> balancePayList) {
        if(balancePayList==null){
            return null;
        }
        return execute(balancePayList, BalancePayInfo.class);
    }

    /**
     * 放款查询结果DO转Vo
     */
    public static LoanSearchResponse loanSearchDoToVo(AssetsSplit assetsSplit, List<AssetsSplitItem> assetsSplitItems) {
        LoanSearchResponse loanSearchResponse = BeanTemplate.executeBean(assetsSplit, LoanSearchResponse.class);
        List<LoanAssetsSplitItemResponse> loanAssetsSplitItemResponses = BeanTemplate.execute(assetsSplitItems, LoanAssetsSplitItemResponse.class);
        loanSearchResponse.setItems(loanAssetsSplitItemResponses);
        return loanSearchResponse;

    }
}
