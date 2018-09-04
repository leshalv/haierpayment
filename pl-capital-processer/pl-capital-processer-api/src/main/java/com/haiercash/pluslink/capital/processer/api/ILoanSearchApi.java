package com.haiercash.pluslink.capital.processer.api;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanSearchRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanSearchResponse;

/**
 * 放款查询
 *
 * @author xiaobin
 * @create 2018-07-16 下午1:13
 **/
public interface ILoanSearchApi {

    RestResponse<LoanSearchResponse> search(String versionId, RestRequest<LoanSearchRequest> restRequest);

}
