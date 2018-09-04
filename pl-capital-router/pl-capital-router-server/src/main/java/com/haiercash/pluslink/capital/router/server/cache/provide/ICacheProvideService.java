package com.haiercash.pluslink.capital.router.server.cache.provide;

import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;

import java.util.List;
import java.util.Map;

/**
 * 路由中心部分启动时将缓存存入redis
 *
 *
 * @author WDY
 * 2018-07-02
 */
public interface ICacheProvideService {

    /**路由规则明细缓存数据加载：
     * 泛型  : Map<key1,Map<key2,RouteRuleItem>>
     * key1 ：业务类型|规则内容
     * key2 : 规则编号
     */
    Map<String,Map<String,RouteRuleItem>> queryRouteRuleData();

    /**查询数据转换map公共方法,实体中存在Id**/
    Map<String,Object> queryCommonData(RedisCacheEnum typeEnum);

}
