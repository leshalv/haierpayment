package com.haiercash.pluslink.capital.manager.service.impl;

import com.haiercash.pluslink.capital.manager.service.IJedisClusterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * JedisCluster服务接口
 *
 * @author keliang.jiang
 * @date 2018/3/9
 */
@Slf4j
@Service
public class JedisClusterServiceImpl implements IJedisClusterService {

    @Value("${common.redisPrefix}")
    private String prefix;

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
        return jedisCluster.get(prefix + key);
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
        String result = jedisCluster.set(prefix + key, value);
        if ("OK".equals(result)) {
            return true;
        }
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
        Long result = jedisCluster.del(prefix + key);
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
        return jedisCluster.hget(prefix + key, field);
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
        Long result = jedisCluster.hset(prefix + key, field, value);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hdels(String key, String... field) {
        Long result = null;
        try {
            result = jedisCluster.hdel(prefix + key, field);
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            log.error("redis删除错误", e);
        }

        return false;
    }

}
