package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanQueryRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * > 前置放款状态查询
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/25 17:40
 */
@FeignClient(value = "${rest.api.preposition}", path = "api/info")
public interface LoanQueryClient {

    @PostMapping(value = "/query/{versionId}", produces = "application/json; charset=UTF-8")
    RestResponse<ResMakeLoansResponse> queryLoanInfo(
            @PathVariable("versionId") String versionId, @RequestBody RestRequest<LoanQueryRequestBody> restRequest);

}
