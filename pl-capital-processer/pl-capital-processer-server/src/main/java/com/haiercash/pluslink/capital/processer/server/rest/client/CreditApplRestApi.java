package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.rest.fallback.CreditApplHystrix;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 调用前置
 * <p>
 * 授信申请 rest api
 *
 * @author xiaobin
 * @create 2018-07-17 下午5:34
 **/
@FeignClient(value = "${rest.api.preposition}", path = "api/credit",fallback = CreditApplHystrix.class)
public interface CreditApplRestApi {


    @PostMapping(value = "/appl/{versionId}", produces = "application/json; charset=UTF-8")
    RestResponse<ResCreditBackVo> appFor(
            @PathVariable("versionId") String versionId, @RequestBody RestRequest<CreditApplRequestVo> restRequest);
}
