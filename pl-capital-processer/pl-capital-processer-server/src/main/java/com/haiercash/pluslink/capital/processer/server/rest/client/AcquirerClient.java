package com.haiercash.pluslink.capital.processer.server.rest.client;


import com.haiercash.pluslink.capital.processer.server.rest.feign.annotation.FeignApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanSecondRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.RequestHead;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ApplInfoAppApptReponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 贷款详情信息
 */
@FeignClient(name = "${rest.api.acquirer}", path = "/api/appl")
/*@FeignClient(url = "http://10.164.197.238:8090/api/appl", name = "acquirer")*/
public interface AcquirerClient {

    @FeignApi("贷款详情查询（供APP后台使用）")
    @PostMapping(value = "/selectApplInfoApp",produces = "application/json; charset=UTF-8")
    ApplInfoAppApptReponse selectApplInfoApp(@RequestBody LoanRequest<LoanSecondRequest<RequestHead, ApplInfoAppRequestBody>> loanRequest);

}
