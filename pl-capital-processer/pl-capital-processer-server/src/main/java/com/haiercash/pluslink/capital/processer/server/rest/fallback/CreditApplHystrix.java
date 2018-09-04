package com.haiercash.pluslink.capital.processer.server.rest.fallback;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.base.RestResponseHead;
import cn.jbinfo.api.context.ApiContextManager;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditApplRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import org.springframework.stereotype.Component;

/**
 * 调用前置授信
 * <p>
 * 熔断器
 *
 * @author xiaobin
 * @create 2018-07-27 下午4:49
 **/
@Component
public class CreditApplHystrix implements CreditApplRestApi {

    @Override
    public RestResponse<ResCreditBackVo> appFor(String versionId, RestRequest<CreditApplRequestVo> restRequest) {
        RestResponseHead head = new RestResponseHead();
        head.setErrorMsg("99999", "链接前置授信服务异常", ApiContextManager.getSerNo());
        ResCreditBackVo resCreditBackVo = new ResCreditBackVo();
        return new RestResponse<>(head, resCreditBackVo);
    }
}
