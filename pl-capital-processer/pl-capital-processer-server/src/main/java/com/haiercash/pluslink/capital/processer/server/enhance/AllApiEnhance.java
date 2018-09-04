package com.haiercash.pluslink.capital.processer.server.enhance;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.enhance.IActionEnhance;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-07-14 下午5:15
 **/
@Component("allApiEnhance")
public class AllApiEnhance implements IActionEnhance {

    @Override
    public boolean before(RestRequest<?> restRequest) {
        return true;
    }

    @Override
    public boolean after(RestRequest<?> restRequest, RestResponse<?> restResponse) {
        return true;
    }
}
