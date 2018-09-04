package com.haiercash.pluslink.capital.router.server.cache.manage;

import com.haiercash.pluslink.capital.data.RouteRuleItem;
import java.util.Map;

/**
 * 启动时加载缓存管理实现类
 * @author WDY
 * 2018-06-29
 * (1)路由规则明细缓存数据加载
 * (2)渠道产品缓存数据加载
 * (3)渠道支持银行缓存数据加载
 * (4)渠道放款占比缓存数据加载
 * (5)渠道数据缓存数据加载
 * (6)渠道资金计划缓存数据加载
 * (7)渠道需求资料缓存数据加载
 */
public interface ICacheManageService {

    /**获取路由规则明细缓存数据**/
    Map<String,Map<String,RouteRuleItem>> queryCacheRoutRule();
}
