package com.haiercash.pluslink.capital.router.client;

import java.util.Map;

/**
 * 路由中心处理调用接口类
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
public interface ICommonSendClient{

    /**通知路由调用接口**/
    Map<String, Object> routerMatch(Object request, String server, String port, String url,String charaCode);

}
