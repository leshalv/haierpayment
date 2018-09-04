package com.haiercash.pluslink.capital.processer.server.rest.client;


import com.haiercash.pluslink.capital.processer.server.rest.feign.annotation.FeignApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.CrmCustResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "${rest.api.crm}", path = "/app/crm/cust")
/*@FeignClient(url= "http://10.164.204.70:8000/app/crm/cust", name = "crmcust")*/
public interface CrmCustClient {

    @FeignApi("查询实名认证客户信息,证件号码")
    @GetMapping(value = "/getCustInfoByCertNo", produces = "application/json; charset=UTF-8")
    CrmCustResponse queryMerchCustInfo(@RequestParam("certNo") String certNo);

}
