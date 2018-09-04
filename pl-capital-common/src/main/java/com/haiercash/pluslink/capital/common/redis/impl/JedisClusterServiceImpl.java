package com.haiercash.pluslink.capital.common.redis.impl;

import com.haiercash.pluslink.capital.common.redis.IJedisClusterService;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * JedisCluster服务接口
 *
 * @author keliang.jiang
 * @date 2018/3/9
 */
@ConditionalOnBean(name = "bizRedisHandler")
@Service("jedisClusterService")
public class JedisClusterServiceImpl extends BaseService implements IJedisClusterService {

    private String prefix = "PL_CAPITAL_COMMON-";

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 获取String类型
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        String getKey = prefix + key;
        return jedisCluster.get(getKey);
    }

    /**
     * 设置String类型
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value) {
        String addKey = prefix + key;
        logger.info("Redis添加数据=====key = " + addKey);
        String result = jedisCluster.set(addKey, value);
        if ("OK".equals(result)) {
            logger.info("【" + addKey + "】Redis添加数据【成功】");
            return true;
        }
        logger.info("【" + addKey + "】Redis添加数据【失败】");
        return false;
    }

    /**
     * 根据key删除redis
     *
     * @param key
     * @return
     */
    @Override
    public boolean del(String key) {
        String delKey = prefix + key;
        Long result = jedisCluster.del(delKey);
        if (result == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取hash类型
     *
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hget(String key, String field) {
        String hgetKey = prefix + key;
        return jedisCluster.hget(hgetKey, field);
    }

    /**
     * 设置hash类型
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    @Override
    public boolean hset(String key, String field, String value) {
        String hsetKey = prefix + key;
        Long result = jedisCluster.hset(hsetKey, field, value);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hdels(String key, String... field) {
        String hdelKey = prefix + key;
        Long result;
        try {
            result = jedisCluster.hdel(hdelKey, field);
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            logger.error("redis删除错误", e);
        }

        return false;
    }

    /**设置key的超时时间**/
    public boolean expireTime(String key,int second){
        String hdelKey = prefix + key;
        Long result;
        try {
            result = jedisCluster.expire(hdelKey,second);
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            logger.error("redis设置超时时间错误", e);
        }
        return false;
    }

    /**判断key是否存在**/
    public boolean isExists(String key){
        String hdelKey = prefix + key;
        boolean result;
        try {
            result = jedisCluster.exists(hdelKey);
            return result;
        } catch (Exception e) {
            logger.error("redis判断是否存在异常", e);
        }
        return false;
    }
}
