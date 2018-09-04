package com.haiercash.pluslink.capital.processer.api;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.api.dto.response.CreditBackResponse;

/**
 * @author lzh
 * @Title: ICreditCallbackApi 授信申请回调APi
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/2114:24
 */
public interface ICreditCallbackApi {

    RestResponse<CreditBackResponse> creditCallback(String versionId, RestRequest<ResCreditBackVo> restRequest);
}
