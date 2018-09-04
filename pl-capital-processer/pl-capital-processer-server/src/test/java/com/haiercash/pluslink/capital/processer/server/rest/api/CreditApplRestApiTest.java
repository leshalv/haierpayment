package com.haiercash.pluslink.capital.processer.server.rest.api;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditApplRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreditApplRestApiTest extends BaseTest {

    @Autowired
    private CreditApplRestApi creditApplRestApi;

    @Test
    public void appFor() {
        RestRequestHead head = new RestRequestHead();
        head.setTradeCode("PLCO001");
        head.setSerNo(IdGen.uuid());
        head.setSysFlag("");
        head.setTradeDate(DateUtils.format("yyyy-MM-dd"));
        head.setTradeTime(DateUtils.format("HH:mm:ss"));
        head.setChannelNo("000");

        CreditApplRequestVo requestVo = new CreditApplRequestVo();
        requestVo.setAppName("appName");
        RestRequest<CreditApplRequestVo> restRequest = new RestRequest<>();
        restRequest.setRequestHead(head);
        restRequest.setBody(requestVo);

        //RestResponse<ResCreditApplResponseVo> restResponse = creditApplRestApi.appFor("v1", restRequest);
        //System.out.println(JsonUtils.safeObjectToJson(restResponse));

    }
}