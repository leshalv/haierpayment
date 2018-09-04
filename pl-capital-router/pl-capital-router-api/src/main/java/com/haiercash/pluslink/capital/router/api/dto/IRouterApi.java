package com.haiercash.pluslink.capital.router.api.dto;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.router.api.dto.request.RouterRequest;
import com.haiercash.pluslink.capital.router.api.dto.response.RouterResponse;

/**
 * 路由中心处理接口
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
public interface IRouterApi {
    RestResponse<RouterResponse> applyFor(String versionId,RestRequest<RouterRequest> restRequest);
}
