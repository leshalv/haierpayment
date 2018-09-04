package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditQueryRequestVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 授信申请查询
 */
public class CreditSearchRestApiTest extends BaseTest {

    @Autowired
    private CreditSearchRestApi creditSearchRestApi;

    @Test
    public void testQueryCredit() {
        RestRequest<CreditQueryRequestVo> restRequest = new RestRequest<>();

        RestRequestHead head = new RestRequestHead();
        head.setTradeCode("001");
        head.setSysFlag("110");
        head.setChannelNo("000");
        restRequest.setRequestHead(head);

        CreditQueryRequestVo creditQueryRequestVo = new CreditQueryRequestVo();
        creditQueryRequestVo.setQryType("01");
        creditQueryRequestVo.setCinoMemno("110");
        restRequest.setBody(creditQueryRequestVo);

        RestResponse<ResCreditBackVo> restResponse = creditSearchRestApi.queryCredit("v1", restRequest);
        System.out.println(JsonUtils.safeObjectToJson(restResponse));

    }
}