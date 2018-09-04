package com.haiercash.pluslink.capital.router.server.cache.manage.impl;

import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.common.utils.JsonConverter;
import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import com.haiercash.pluslink.capital.router.server.cache.manage.ICacheManageService;
import com.haiercash.pluslink.capital.router.server.cache.provide.ICacheProvideService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 启动时加载缓存管理实现类
 * @author WDY
 * 2018-06-29
 */
@Service("cacheManageService")
public class CacheManageServiceImpl extends BaseService implements ICacheManageService {

    @Autowired
    private ICacheProvideService cacheProvideService;
    @Autowired
    private IRedisCommonService redisCommonService;

    @Value("${redis.expire.outTime:}")
    private String outTime;
    @Value("${redis.expire.timeType:}")
    private String timeType;
    @Value("${redis.cache.config.cacheArray:}")
    private String[] cacheArray;
    @Value("${redis.cache.flag:}")
    private String flag;

    @PostConstruct
    public void initCacheToRedis(){
        if("true".equals(flag)){
            int j = 0;
            for(int i=0;i<cacheArray.length;i++){
                dealCache(RedisCacheEnum.getEnum(cacheArray[i]));
                j ++;
            }
            logger.info("共初始化【" + j + "】组数据......");
            logger.info("");
        }else{
            logger.info("");
            logger.info("【路由中心缓存初始化】标识：" + flag + ",不执行...");
        }
    }

    private void dealCache(RedisCacheEnum typeEnum){
        Long startTime = System.currentTimeMillis();
        logger.info("");
        logger.info("====【路由中心缓存初始化】【编号：" + startTime + "】" + typeEnum.getDesc() + "到Redis【开始】...");

        //1,路由规则明细缓存数据加载
        if(RedisCacheEnum.route_rule_item.getCode().equals(typeEnum.getCode())){
            Map<String,Map<String,RouteRuleItem>> map = cacheProvideService.queryRouteRuleData();
            logger.info("【编号：" + startTime + "】共有【" + map.size() + "】条数据,数据:" + JsonConverter.object2Json(map));
            intoRedis(typeEnum.getCode(),map,Integer.parseInt(outTime),TimeEnum.getEnum(timeType));
        }else{
            Map<String,Object> map = cacheProvideService.queryCommonData(typeEnum);
            logger.info("【编号：" + startTime + "】共有【" + map.size() + "】条数据,数据:" + JsonConverter.object2Json(map));
            intoRedis(typeEnum.getCode(),map,Integer.parseInt(outTime),TimeEnum.getEnum(timeType));
        }

        Long endTime = System.currentTimeMillis();
        logger.info("====【路由中心缓存初始化】【编号：" + startTime + "】" + typeEnum.getDesc() + "到Redis【结束】...耗时【" + (endTime - startTime) + "】毫秒");
        logger.info("");
    }

    private void intoRedis(String key, Object obj, int time, TimeEnum type){
        redisCommonService.addRedis(key,obj,time,type);
    }

    /**获取路由规则明细缓存数据**/
    public Map<String,Map<String,RouteRuleItem>> queryCacheRoutRule(){
       Map<String,Map<String,RouteRuleItem>> map;
       try{
           map = redisCommonService.selectRedis(RedisCacheEnum.route_rule_item.getCode(), new TypeReference<Map<String,Map<String,RouteRuleItem>>>(){});
           if(null == map){
               map = cacheProvideService.queryRouteRuleData();
           }
       }catch(Exception e) {
           map = cacheProvideService.queryRouteRuleData();
       }
       if(map == null){
           map = new HashedMap();
       }
       return map;//
    }

}
