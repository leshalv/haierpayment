package com.haiercash.pluslink.capital.processer.server.rest.client;

import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LcApplyFileQueryRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LcApplyFileQueryResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 个人贷款合同信息查询
 *
 * @author xiaobin
 * @create 2018-08-20 下午4:07
 **/
@FeignClient(value = "${rest.api.cmis}")
//@FeignClient(url = "http://10.164.194.102:8081", name = "pub")
public interface LcApplyFileQueryClient {

    /**
     * @param queryRequest map.key request
     * @return map.key response
     */
    @PostMapping(value = "/pub/cmisfront", produces = "application/json; charset=UTF-8")
    Map<String, LcApplyFileQueryResponse> cmisfront(@RequestBody Map<String, LcApplyFileQueryRequest> queryRequest);
}
