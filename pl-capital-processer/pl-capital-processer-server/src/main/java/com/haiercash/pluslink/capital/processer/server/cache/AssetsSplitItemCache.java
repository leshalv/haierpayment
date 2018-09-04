package com.haiercash.pluslink.capital.processer.server.cache;

import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-14 下午4:36
 **/
@Component
@Slf4j
public class AssetsSplitItemCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    private static final String KEY_ = "assetsSplitItem";

    public void put(AssetsSplitItem item) {
        try {
            if (item == null)
                return;
            redisCommonService.addRedis(KEY_ + item.getId(), JsonUtils.writeObjectToJson(item), 1, TimeEnum.day);
            redisCommonService.addRedis(KEY_ + item.getAssetsSplitId() + item.getLoanNo(), JsonUtils.writeObjectToJson(item), 1, TimeEnum.day);
        } catch (Exception ex) {
            log.error("redis，读取资产信息出现异常：", ex);
        }
    }

    public AssetsSplitItem getByAssetSplitIdAndLoanNo(String assetsSplitId, String loanNo) {
        try {
            return redisCommonService.selectRedis(KEY_ + assetsSplitId + loanNo, new TypeReference<AssetsSplitItem>() {
            });
        } catch (Exception ex) {
            log.error("从redis读取资产信息出现异常：", ex);
        }
        return null;
    }

    public AssetsSplitItem get(String id) {
        try {
            return redisCommonService.selectRedis(KEY_ + id, new TypeReference<AssetsSplitItem>() {
            });
        } catch (Exception ex) {
            log.error("从redis读取资产信息出现异常：", ex);
        }
        return null;
    }
}
