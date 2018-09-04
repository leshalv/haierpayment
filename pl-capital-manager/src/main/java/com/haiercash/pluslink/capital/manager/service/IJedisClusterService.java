package com.haiercash.pluslink.capital.manager.service;

/**
 * JedisCluster服务接口
 *
 * @author keliang.jiang
 * @date 2018/3/9
 */
public interface IJedisClusterService {


    String get(String key);

    boolean set(String key, String value);

    boolean del(String key);

    String hget(String key, String field);

    boolean hset(String key, String field, String value);

    boolean hdels(String key, String...field);
}
