package com.haiercash.pluslink.capital.processer.server.task.context;

import cn.jbinfo.api.base.RestRequest;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaobin
 * @create 2018-08-11 下午2:03
 **/
@Getter
@Setter
public class CreditApplyTaskContext {

    private AssetsSplit assetsSplit;

    private AssetsSplitItem assetsSplitItem;

    private RestRequest<CreditApplRequestVo> restRequest;

    public CreditApplyTaskContext(){}

    public CreditApplyTaskContext(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem, RestRequest<CreditApplRequestVo> restRequest) {
        this.assetsSplit = assetsSplit;
        this.assetsSplitItem = assetsSplitItem;
        this.restRequest = restRequest;
    }
}
