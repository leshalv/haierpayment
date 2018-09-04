package com.haiercash.pluslink.capital.router.server.rest.controller;

import com.haiercash.pluslink.capital.router.rest.dto.request.LastLoanQueryRequest;
import com.haiercash.pluslink.capital.router.rest.dto.response.LastLoanQueryResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户是否30天前贷款查询接口
 * @author WDY
 * @date 2018-07-25
 * @rmk
 */
@FeignClient(value = "${rest.controller.instance.lastLoanQuery}", path = "api/acrm")
public interface LastLoanQuery {

    @PostMapping(value = "/market/thirtyDaysLoan", produces = "application/json; charset=UTF-8")
    LastLoanQueryResponse queryLastLoan(@RequestBody LastLoanQueryRequest restRequest);
}
