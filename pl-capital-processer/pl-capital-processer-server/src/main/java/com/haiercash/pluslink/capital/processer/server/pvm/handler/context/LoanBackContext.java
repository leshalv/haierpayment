package com.haiercash.pluslink.capital.processer.server.pvm.handler.context;

import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 放款回调上下文
 *
 * @author xiaobin
 * @create 2018-07-31 下午4:14
 **/
@Getter
@Setter
public class LoanBackContext {

    private RestResponse<ResMakeLoansResponse> restResponse;

    private AssetsSplit assetsSplit;

    private AssetsSplitItem assetsSplitItem;

    public LoanBackContext(){}

    public LoanBackContext(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem, RestResponse<ResMakeLoansResponse> restResponse) {
        this.assetsSplit = assetsSplit;
        this.assetsSplitItem = assetsSplitItem;
        this.restResponse = restResponse;
    }
}
