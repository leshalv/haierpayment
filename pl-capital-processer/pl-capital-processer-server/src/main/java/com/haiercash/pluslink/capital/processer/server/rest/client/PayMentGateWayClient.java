package com.haiercash.pluslink.capital.processer.server.rest.client;


import com.haiercash.pluslink.capital.processer.server.rest.feign.annotation.FeignApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWaySearchRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PayMentGateWayLoanResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 支付网关接口类http://10.164.197.208:8083
 */
@FeignClient(name = "${rest.api.receipt}", path = "/api/pgw/trade/trans/agent")
/*@FeignClient(url= "http://10.164.197.208:8083/api/pgw/trade/trans/agent", name = "receipt")*/
public interface PayMentGateWayClient {
    
    @FeignApi("代收付单笔收单接口")
    @PostMapping(value = "/singleAgentTrans", produces = "application/json; charset=UTF-8")
    PayMentGateWayLoanResponse notifyLoan(@RequestBody PayMentGateWayLoanRequest payMentGateWayLoanRequest);

    @FeignApi("单笔代收付查询")
    @PostMapping(value = "/singleAgentTransQuery", produces = "application/json; charset=UTF-8")
    PayMentGateWayLoanResponse searchLoan(@RequestBody PayMentGateWaySearchRequest payMentGateWaySearchRequest);


}
