package com.haiercash.pluslink.capital.processer.server.task.context;

import cn.jbinfo.api.base.RestRequest;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.MakeLoansRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaobin
 * @create 2018-08-13 下午4:54
 **/
@Getter
@Setter
public class MakeLoanContext {

    private RestRequest<MakeLoansRequest> restRequest;

    private AssetsSplit assetsSplit;

    private AssetsSplitItem assetsSplitItem;

    public MakeLoanContext() {
    }

    public MakeLoanContext(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem, RestRequest<MakeLoansRequest> restRequest) {
        this.assetsSplit = assetsSplit;
        this.assetsSplitItem = assetsSplitItem;
        this.restRequest = restRequest;
    }
}
