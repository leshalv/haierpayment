package com.haiercash.pluslink.capital.processer.server.pvm.event;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.enums.dictionary.PL0106Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0505Enum;
import com.haiercash.pluslink.capital.processer.server.pvm.EngineEvent;
import com.haiercash.pluslink.capital.processer.server.pvm.vo.LoanResultAssetsSplitItemVo;
import com.haiercash.pluslink.capital.processer.server.pvm.vo.LoanResultVo;
import lombok.Getter;

import java.util.List;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/24 14:32
 * @Description: 放款结果通知至信贷事件
 */
@Getter
public class LendingResultEvent extends EngineEvent {

    /*todo 推送的消息属性*/
    private LoanResultVo loanResultVo;

    private AssetsSplit assetsSplit;


    public LendingResultEvent(Object source, AssetsSplit assetsSplit, List<AssetsSplitItem> assetsSplitItem) {
        super(source);
        loanResultVo = new LoanResultVo();
        this.assetsSplit = assetsSplit;


        //AssetsSplitService assetsSplitService = SpringContextHolder.getBean(AssetsSplitService.class);
        //assetsSplit = assetsSplitService.get(assetsSplit.getId());
        loanResultVo.setApplSeq(assetsSplit.getApplSeq());
        loanResultVo.setContractNo(assetsSplit.getContractNo());
        loanResultVo.setProjectType(assetsSplit.getProjectType());
        loanResultVo.setProdBuyOut(assetsSplit.getProdBuyOut());

        loanResultVo.setLoanStatus(assetsSplit.getLoanStatus());

        if (assetsSplitItem != null && assetsSplitItem.size() > 0) {
            List<LoanResultAssetsSplitItemVo> loanResultAssetsSplitItemVos = Lists.newArrayList();
            for (AssetsSplitItem splitItem : assetsSplitItem) {
                LoanResultAssetsSplitItemVo splitItemVo = new LoanResultAssetsSplitItemVo();
                splitItemVo.setLoanNo(splitItem.getLoanNo());
                splitItemVo.setLoanType(splitItem.getLoanType());
                splitItemVo.setProjectId(splitItem.getProjectId());
                splitItemVo.setAgencyRate(splitItem.getAgencyRate() + "");
                splitItemVo.setApplAmt(splitItem.getApplAmt());
                splitItemVo.setTransAmt(splitItem.getTransAmt());
                splitItemVo.setStatus(splitItem.getStatus());
                splitItemVo.setAgencyId(splitItem.getAgencyId());
                splitItemVo.setCapLoanNo(splitItem.getCapLoanNo());
                loanResultAssetsSplitItemVos.add(splitItemVo);
            }
            loanResultVo.setItems(loanResultAssetsSplitItemVos);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(loanResultVo);
    }
}
