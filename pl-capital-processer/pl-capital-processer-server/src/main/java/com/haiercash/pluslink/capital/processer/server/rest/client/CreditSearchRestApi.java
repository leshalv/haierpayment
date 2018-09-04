package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.rest.fallback.CreditSearchHystrix;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditQueryRequestVo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 授信查询
 * <p>
 * 01：普通查询
 * 02：授信申请查询
 *
 * @author xiaobin
 * @create 2018-07-19 上午10:31
 **/
@FeignClient(value = "${rest.api.preposition}", path = "api/credit",fallback = CreditSearchHystrix.class)
public interface CreditSearchRestApi {

    @PostMapping(value = "/query/{versionId}", produces = "application/json; charset=UTF-8")
    RestResponse<ResCreditBackVo> queryCredit(
            @PathVariable("versionId") String versionId, @RequestBody RestRequest<CreditQueryRequestVo> restRequest);
}
