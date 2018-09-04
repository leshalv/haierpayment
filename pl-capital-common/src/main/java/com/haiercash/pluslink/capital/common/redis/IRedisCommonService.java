package com.haiercash.pluslink.capital.common.redis;

import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.enums.TimeEnum;

import java.util.Map;

/**
 * 常规redis存取接口类
 *
 * @author WDY
 * @date 20180702
 * @rmk 提供常规增加，修改，删除，查询等
 */
public interface IRedisCommonService {

    /**
     * 添加redis公共方法
     * Map为Map<实体ID,实体名字>
     **/
    void addRedisMap(RedisCacheEnum redisType, Map map, int time, TimeEnum timeType);

    /**
     * 添加redis缓存数据
     *
     * @author WDY
     * @inParam TimeEnum.code为0则不设置过期时间
     * @outParam
     * @date 20180702
     * @rmk
     */
    void addRedis(String key, Object value, int time, TimeEnum type);

    /**
     * 修改redis缓存数据
     *
     * @author WDY
     * @inParam TimeEnum.code为0则不设置过期时间
     * @outParam
     * @date 20180702
     * @rmk
     */
    void updateRedis(String key, Object value, int time, TimeEnum type);

    /**
     * 删除redis缓存数据
     *
     * @author WDY
     * @inParam
     * @outParam
     * @date 20180702
     * @rmk
     */
    void deleteRedis(String key);

    /**
     * 查询redis缓存数据(根据Key查询，若查询不到从数据库查询逻辑业务端自行添加)
     *
     * @author WDY
     * @inParam
     * @outParam
     * @date 20180702
     * @rmk new TypeReference<T>(){}
     */
    <T> T selectRedis(String key, TypeReference<T> type);

    String selectRedis(String key);
}
