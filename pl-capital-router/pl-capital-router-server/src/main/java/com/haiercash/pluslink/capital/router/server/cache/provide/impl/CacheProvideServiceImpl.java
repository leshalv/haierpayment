package com.haiercash.pluslink.capital.router.server.cache.provide.impl;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.router.server.cache.provide.ICacheProvideService;
import com.haiercash.pluslink.capital.router.server.service.IPublicDataService;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 路由中心部分启动时将缓存存入redis
 *
 * @author WDY
 * 2018-07-02
 */
@Service("cacheProvideService")
public class CacheProvideServiceImpl extends BaseService implements ICacheProvideService {

    /**注入参数获取接口**/
    @Autowired
    private IPublicDataService publicDataService;

    /**路由规则明细缓存数据加载：
     * 泛型Map<key1,Map<key2,RouteRuleItem>>
     * key1 ：业务类型|规则内容
     * key2 : 规则编号
     */
    public Map<String,Map<String,RouteRuleItem>> queryRouteRuleData(){
        Map<String,Map<String,RouteRuleItem>> routeRuleCacheMap = new HashMap();
        Object obj = publicDataService.queryPublicDataForCache(RedisCacheEnum.route_rule_item);
        if(null == obj){
            return routeRuleCacheMap;
        }
        List<RouteRuleItem> list = (List<RouteRuleItem>)obj;
        for(RouteRuleItem op: list){
            StringBuilder routeRuleKey = new StringBuilder();
            routeRuleKey.append(op.getFdType()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(op.getRuleContent());
            Map<String,RouteRuleItem>  map = routeRuleCacheMap.get(routeRuleKey.toString());
            if(null == map){
                map = new HashMap();
            }
            map.put(op.getFdNum(),op);
            routeRuleCacheMap.put(routeRuleKey.toString(),map);
        }
        return routeRuleCacheMap;
    }


    /**查询数据转换map公共方法,实体中存在Id**/
    public Map<String,Object> queryCommonData(RedisCacheEnum typeEnum){
        Map<String,Object> map = new HashMap();

        Object obj = publicDataService.queryPublicDataForCache(typeEnum);
        if(null == obj){
            return map;
        }
        List list = (List)obj;
        map = changeMapById(list);
        return map;
    }


    /**提取数据中的id当主键返回map**/
    private Map<String,Object> changeMapById(List<?> list){
        Map<String,Object> map = new HashMap();
        for(Object obj : list){
            String id = ParamUtil.getIdByData(obj);
            if(null == id){
                continue;
            }
            map.put(id,obj);
       }
       return map;
    }
}
