package com.haiercash.pluslink.capital.router.server.service;

import com.haiercash.pluslink.capital.router.server.entity.RouterMatchData;

import java.util.Map;

/**
 * 获取路由匹配参数接口类
 * @author WDY
 * @date 2018-07-18
 * @rmk
 */
public interface IRouterMatchDataService {

    /**获取路由匹配参数**/
    Map<String,Map<String,RouterMatchData>> getRouterMatchData(String serNo,String applSeq);
}
