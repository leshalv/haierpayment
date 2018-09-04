package com.haiercash.pluslink.capital.common.redis.impl;

import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.exception.RedisException;
import com.haiercash.pluslink.capital.common.redis.IJedisClusterService;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.common.utils.DateUtils;
import com.haiercash.pluslink.capital.common.utils.JsonConverter;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.enums.RedisStatusEnum;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 常规redis存取服务类
 *
 * @author WDY
 * @date 20180702
 * @rmk 提供常规增加，修改，删除，查询等
 */
@ConditionalOnBean(name = "bizRedisHandler")
@Service("redisCommonService")
public class RedisCommonServiceImpl extends BaseService implements IRedisCommonService {

    @Autowired
    private IJedisClusterService jedisClusterService;

    /**
     * 添加缓存参数标识
     **/
    private static final int ADD_TYPE = 0;
    /**
     * 修改缓存参数标识
     **/
    private static final int EDIT_TYPE = 1;
    /**
     * 删除缓存参数标识
     **/
    private static final int DEL_TYPE = 2;
    /**
     * 查询除存参数标识
     **/
    private static final int SEL_TYPE = 3;

    /**
     * 添加redis公共方法
     * Map为Map<实体ID,实体名字>
     **/
    public void addRedisMap(RedisCacheEnum redisType, Map map, int time, TimeEnum timeType) {
        addRedis(redisType.getCode(), map, time, timeType);
    }

    /**
     * 添加redis缓存数据
     *
     * @author WDY
     * @inParam TimeEnum.code为0则不设置过期时间
     * @outParam
     * @date 20180702
     * @rmk
     */
    public void addRedis(String key, Object value, int time, TimeEnum type) {
        checkParam(key, value, ADD_TYPE);
        int expireSecond = DateUtils.changeSecond(time, type);
        boolean expireFlag = false;
        if (0 == expireSecond) {
            logger.info("【" + key + "】添加redis时不设置过期时间...");
        } else {
            expireFlag = true;
            logger.info("【" + key + "】添加redis时设置过期时间为【" + time + "】" + type.getDesc() + "，折合【" + expireSecond + "】秒...");
        }
        boolean flag;
        try {
            String json = JsonConverter.object2Json(value);
            flag = jedisClusterService.set(key, json);
        } catch (Exception e) {
            logger.error("Redis添加数据异常,Message = " + e.getMessage(), e);
            throw new RedisException(RedisStatusEnum.redis_add_error,
                    ("key = " + key + ",value = " + value));
        }
        if (!flag) {
            throw new RedisException(RedisStatusEnum.redis_add_false_error,
                    ("key = " + key + ",value = " + value));
        }
        if (expireFlag) {
            boolean reFlag;
            try {
                reFlag = jedisClusterService.expireTime(key, expireSecond);
            } catch (Exception e) {
                logger.error("Redis添加时设置过期时间异常,Message = " + e.getMessage(), e);
                throw new RedisException(RedisStatusEnum.redis_add_expire_error,
                        ("key = " + key + ",value = " + value));
            }
            if (!reFlag) {
                throw new RedisException(RedisStatusEnum.redis_add_expire_error,
                        ("key = " + key + ",value = " + value));
            }
        }
    }

    /**
     * 修改redis缓存数据
     *
     * @author WDY
     * @inParam TimeEnum.code为0则不设置过期时间
     * @outParam
     * @date 20180702
     * @rmk
     */
    public void updateRedis(String key, Object value, int time, TimeEnum type) {
        checkParam(key, value, EDIT_TYPE);
        int expireSecond = DateUtils.changeSecond(time, type);
        boolean expireFlag = false;
        if (0 == expireSecond) {
            logger.info("【" + key + "】添加redis时不设置过期时间...");
        } else {
            expireFlag = true;
            logger.info("【" + key + "】添加redis时设置过期时间为【" + time + "】" + type.getDesc() + "...");
        }
        boolean flag;
        try {
            String json = JsonConverter.object2Json(value);
            flag = jedisClusterService.set(key, json);
        } catch (Exception e) {
            logger.error("Redis修改数据异常,Message = " + e.getMessage(), e);
            throw new RedisException(RedisStatusEnum.redis_update_error,
                    ("key = " + key + ",value = " + value));
        }
        if (!flag) {
            throw new RedisException(RedisStatusEnum.redis_update_false_error,
                    ("key = " + key + ",value = " + value));
        }
        if (expireFlag) {
            boolean reFlag;
            try {
                reFlag = jedisClusterService.expireTime(key, expireSecond);
            } catch (Exception e) {
                logger.error("Redis修改时设置过期时间异常,Message = " + e.getMessage(), e);
                throw new RedisException(RedisStatusEnum.redis_add_expire_error,
                        ("key = " + key + ",value = " + value));
            }
            if (!reFlag) {
                throw new RedisException(RedisStatusEnum.redis_update_expire_error,
                        ("key = " + key + ",value = " + value));
            }
        }
    }

    /**
     * 删除redis缓存数据
     *
     * @author WDY
     * @inParam
     * @outParam
     * @date 20180702
     * @rmk
     */
    public void deleteRedis(String key) {
        checkParam(key, null, DEL_TYPE);
        boolean flag;
        try {
            flag = jedisClusterService.del(key);
        } catch (Exception e) {
            logger.error("Redis删除数据异常,Message = " + e.getMessage(), e);
            throw new RedisException(RedisStatusEnum.redis_delete_error,
                    "key = " + key);
        }
        if (!flag) {
            throw new RedisException(RedisStatusEnum.redis_delete_false_error,
                    "key = " + key);
        }
    }

    /**
     * 查询redis缓存数据(根据Key查询，若查询不到从数据库查询逻辑业务端自行添加)
     *
     * @author WDY
     * @inParam
     * @outParam
     * @date 20180702
     * @rmk new TypeReference<T>(){}
     */
    public <T> T selectRedis(String key, TypeReference<T> type) {
        if (null == type) {
            throw new RedisException(RedisStatusEnum.redis_select_param_error,
                    "type = " + type);
        }
        checkParam(key, null, SEL_TYPE);
        T t;
        try {
            String returnJson = jedisClusterService.get(key);
            if (StringUtils.isBlank(returnJson))
                return null;
            t = JsonConverter.fastJson2Object(returnJson, type);
        } catch (Exception e) {
            logger.error("Redis查询数据异常,Message = " + e.getMessage(), e);
            throw new RedisException(RedisStatusEnum.redis_select_error,
                    "key = " + key);
        }
        return t;
    }

    public String selectRedis(String key) {
        checkParam(key, null, SEL_TYPE);
        try {
            return jedisClusterService.get(key);
        } catch (Exception e) {
            logger.error("Redis查询数据异常,Message = " + e.getMessage(), e);
            throw new RedisException(RedisStatusEnum.redis_select_error,
                    "key = " + key);
        }
    }

    private void checkParam(String key, Object obj, int type) {
        switch (type) {
            case ADD_TYPE://添加
                if (null == key || "".equals(key) || null == obj) {
                    throw new RedisException(RedisStatusEnum.redis_add_param_error,
                            ("key = " + key + ",value = " + obj));
                }
                break;
            case EDIT_TYPE://修改
                if (null == key || "".equals(key) || null == obj) {
                    throw new RedisException(RedisStatusEnum.redis_update_param_error,
                            ("key = " + key + ",value = " + obj));
                }
                break;
            case DEL_TYPE://删除
                if (null == key || "".equals(key)) {
                    throw new RedisException(RedisStatusEnum.redis_delete_param_error,
                            "key = " + key);
                }
                break;
            case SEL_TYPE: //查询
                if (null == key || "".equals(key)) {
                    throw new RedisException(RedisStatusEnum.redis_select_param_error,
                            "key = " + key);
                }
                break;
            default:
                throw new RedisException(RedisStatusEnum.redis_oper_error,
                        ("key = " + key + ",value = " + obj));
        }
    }
}
