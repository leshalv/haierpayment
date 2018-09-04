package com.haiercash.pluslink.capital.processer.server.rest.client;


import com.haiercash.pluslink.capital.processer.server.rest.feign.annotation.FeignApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanSecondRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.RequestHead;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.RiskInfoRequestBody;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "${rest.api.esquery}", path = "/EsQuery/esquery/api")
/*@FeignClient(url= "http://10.164.204.91:8080/EsQuery/esquery/api", name = "esquery")*/
public interface RiskInfoClient {
    @FeignApi("大数据风险分析接口")
    @PostMapping(value = "/batchQueryRiskInfo")
    String getAddrIp(@RequestBody LoanRequest<LoanSecondRequest<RequestHead, RiskInfoRequestBody>> loanRequest);

}
