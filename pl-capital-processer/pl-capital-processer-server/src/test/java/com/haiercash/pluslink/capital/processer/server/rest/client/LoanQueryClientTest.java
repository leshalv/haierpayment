package com.haiercash.pluslink.capital.processer.server.rest.client;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.LoanQueryRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * > 放款状态查询测试类
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/31 10:22
 */
public class LoanQueryClientTest extends BaseTest {
    @Autowired
    private LoanQueryClient loanQueryClient;

    @Test
    public void testLoanQuery() {
        RestRequest<LoanQueryRequestBody> restRequest = new RestRequest<>();

        RestRequestHead head = new RestRequestHead();
        head.setTradeCode("001");
        head.setSysFlag("110");
        head.setChannelNo("000");
        restRequest.setRequestHead(head);

        LoanQueryRequestBody loanQueryRequestBody = new LoanQueryRequestBody();
        loanQueryRequestBody.setOrgCorpMsgId("");
        restRequest.setBody(loanQueryRequestBody);

        RestResponse<ResMakeLoansResponse> restResponse = loanQueryClient.queryLoanInfo("v1", restRequest);
        System.out.println(JsonUtils.safeObjectToJson(restResponse));

    }
}
