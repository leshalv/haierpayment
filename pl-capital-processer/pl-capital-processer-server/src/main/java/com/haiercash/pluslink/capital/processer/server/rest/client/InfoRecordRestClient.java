package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.InfoRecordRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 信息补录
 *
 * @author xiaobin
 * @create 2018-08-20 下午9:02
 **/
@FeignClient(value = "${rest.api.preposition}", path = "api/info")
public interface InfoRecordRestClient {

    @PostMapping(value = "/record/{versionId}", produces = "application/json; charset=UTF-8")
    RestResponse makeLoans(
            @PathVariable("versionId") String versionId, @RequestBody RestRequest<InfoRecordRequest> restRequest);
}
