package com.haiercash.pluslink.capital.router.api.dto;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.router.api.dto.request.GeneralInfoRequest;
import com.haiercash.pluslink.capital.router.api.dto.response.GeneralInfoResponse;

/**
 * 资金方综合信息查询接口
 * @author WDY
 * @date 2018-07-13
 * @rmk
 */
public interface IGeneralInfoApi {
    RestResponse<GeneralInfoResponse> applyFor(String versionId,RestRequest<GeneralInfoRequest> restRequest);
}
