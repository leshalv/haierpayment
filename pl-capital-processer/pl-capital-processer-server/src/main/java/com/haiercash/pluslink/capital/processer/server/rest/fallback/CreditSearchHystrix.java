package com.haiercash.pluslink.capital.processer.server.rest.fallback;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.base.RestResponseHead;
import cn.jbinfo.api.context.ApiContextManager;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.server.rest.client.CreditSearchRestApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditQueryRequestVo;
import org.springframework.stereotype.Component;

/**
 * 调用前置授信查询
 * <p>
 * 熔断
 *
 * @author lzh
 * @create 2018-07-27 下午4:49
 **/
@Component
public class CreditSearchHystrix implements CreditSearchRestApi {

    @Override
    public RestResponse<ResCreditBackVo> queryCredit(String versionId, RestRequest<CreditQueryRequestVo> restRequest) {
        RestResponseHead head = new RestResponseHead();
        head.setErrorMsg("99999", "链接前置授信查询服务异常", ApiContextManager.getSerNo());
        ResCreditBackVo resCreditBackVo = new ResCreditBackVo();
        return new RestResponse<>(head, resCreditBackVo);
    }
}
