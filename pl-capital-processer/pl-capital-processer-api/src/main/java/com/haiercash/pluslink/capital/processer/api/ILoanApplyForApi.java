package com.haiercash.pluslink.capital.processer.api;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanApplyForResponse;

/**
 * 通知放款处理API
 *
 * @author xiaobin
 * @create 2018-07-05 下午6:31
 **/
public interface ILoanApplyForApi {

    RestResponse<LoanApplyForResponse> applyFor(String versionId,RestRequest<LoanApplyForRequest> restRequest);
}
