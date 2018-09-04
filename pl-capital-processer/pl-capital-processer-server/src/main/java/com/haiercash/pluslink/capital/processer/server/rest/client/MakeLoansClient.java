package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.server.rest.fallback.MakeLoansHystrix;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.MakeLoansRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 前置放款
 *
 * @author xiaobin
 * @create 2018-07-25 上午10:27
 **/
@FeignClient(value = "${rest.api.preposition}", path = "api/loan",fallback = MakeLoansHystrix.class)
public interface MakeLoansClient {

    @PostMapping(value = "/make/{versionId}", produces = "application/json; charset=UTF-8")
    RestResponse<ResMakeLoansResponse> makeLoans(
            @PathVariable("versionId") String versionId, @RequestBody RestRequest<MakeLoansRequest> restRequest);
}
