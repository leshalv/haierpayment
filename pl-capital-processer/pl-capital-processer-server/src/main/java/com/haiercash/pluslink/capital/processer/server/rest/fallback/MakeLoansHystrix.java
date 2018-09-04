package com.haiercash.pluslink.capital.processer.server.rest.fallback;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.base.RestResponseHead;
import cn.jbinfo.api.context.ApiContextManager;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditSearchRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.client.MakeLoansClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditQueryRequestVo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.MakeLoansRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.ResMakeLoansResponse;
import org.springframework.stereotype.Component;

/**
 * 调用前置放款
 * <p>
 * 熔断
 *
 * @author lzh
 * @create 2018-07-27 下午4:49
 **/
@Component
public class MakeLoansHystrix implements MakeLoansClient {


    @Override
    public RestResponse<ResMakeLoansResponse> makeLoans(String versionId, RestRequest<MakeLoansRequest> restRequest) {
        RestResponseHead head = new RestResponseHead();
        head.setErrorMsg("99999", "链接前置放款服务异常", ApiContextManager.getSerNo());
        ResMakeLoansResponse resMakeLoansResponse = new ResMakeLoansResponse();
        return new RestResponse<>(head, resMakeLoansResponse);
    }
}
