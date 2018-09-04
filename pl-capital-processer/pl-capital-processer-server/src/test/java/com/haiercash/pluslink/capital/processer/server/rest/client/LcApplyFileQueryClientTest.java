package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.common.utils.JsonUtils;
import com.google.common.collect.Maps;
import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LcApplyFileQueryRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LcApplyFileQueryResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 调用信贷合同文档
 */
public class LcApplyFileQueryClientTest extends BaseTest {

    @Autowired
    private LcApplyFileQueryClient lcApplyFileQueryClient;

    @Test
    public void testCore() {
        LcApplyFileQueryRequest queryRequest = new LcApplyFileQueryRequest("8628817");
        Map<String, LcApplyFileQueryRequest> map = Maps.newHashMap();
        map.put("request", queryRequest);
        System.out.println("json===" + JsonUtils.writeObjectToJson(map));
        //info
        Map<String, LcApplyFileQueryResponse> queryResponse = lcApplyFileQueryClient.cmisfront(map);
        System.out.println(JsonUtils.safeObjectToJson(queryResponse));
    }

}