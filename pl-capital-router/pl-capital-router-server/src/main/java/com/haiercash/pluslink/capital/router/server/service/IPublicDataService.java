package com.haiercash.pluslink.capital.router.server.service;

import com.haiercash.pluslink.capital.data.RouteResultRecord;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;

/**
 * 路由中心公共数据查询接口类
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
public interface IPublicDataService {

    /**为缓存数据提供公共查询**/
    Object queryPublicDataForCache(RedisCacheEnum typeEnum);

    /**查询序列**/
    String querySequence(String seqName);

    /**插入路由结果表**/
    void addRouteResultRecord(RouteResultRecord routeResultRecord);

    /**已使用限额**/
    PositionSplitIn getAlreadyLoan(String projectId);
}
