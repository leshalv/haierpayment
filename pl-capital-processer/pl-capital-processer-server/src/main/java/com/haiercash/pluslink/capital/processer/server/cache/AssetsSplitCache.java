package com.haiercash.pluslink.capital.processer.server.cache;

import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-14 下午4:17
 **/
@Component
@Slf4j
public class AssetsSplitCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    private static final String KEY_ = "assetsSplit";

    public void put(AssetsSplit assetsSplit) {
        try {
            if (assetsSplit == null)
                return;
            redisCommonService.addRedis(KEY_ + assetsSplit.getId(), JsonUtils.writeObjectToJson(assetsSplit), 1, TimeEnum.day);
        } catch (Exception ex) {
            log.error("redis，读取资产信息出现异常：", ex);
        }
    }

    public AssetsSplit get(String id) {
        try {
            return redisCommonService.selectRedis(KEY_ + id, new TypeReference<AssetsSplit>() {
            });
        } catch (Exception ex) {
            log.error("从redis读取资产信息出现异常：", ex);
        }
        return null;
    }
}
