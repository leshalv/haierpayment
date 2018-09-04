package com.haiercash.pluslink.capital.processer.server.pvm.handler.context;

import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 授信回调、放款状态查询
 * <p>
 * 事件
 *
 * @author xiaobin
 * @create 2018-07-21 下午4:04
 **/
@Getter
@Setter
public class CreditBackContext {

    //资产拆分主表
    private AssetsSplit assetsSplit;
    //资产拆分明细表（资方明细）
    private AssetsSplitItem assetsSplitItem;

    //额度信息
    private Quota quota;

    private String corpMsgId;

    private RestResponse<ResCreditBackVo> creditBackVoRestResponseVo;

    public CreditBackContext(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem,
                             RestResponse<ResCreditBackVo> restResponse, Quota quota, String corpMsgId) {
        this.assetsSplit = assetsSplit;
        this.assetsSplitItem = assetsSplitItem;
        this.creditBackVoRestResponseVo = restResponse;
        this.quota = quota;
        this.corpMsgId = corpMsgId;
    }
}
