package com.haiercash.pluslink.capital.processer.server.cache;

import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-08 上午9:43
 **/
@Component
public class LendingResultCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    public void put(String appSeq) {
        try {
            redisCommonService.addRedis("lending_" + appSeq, "1", 1, TimeEnum.minute);
        } catch (Exception ignored) {
        }
    }

    public boolean isSend(String appSeq) {
        try {
            return "1".equals(redisCommonService.selectRedis("lending_" + appSeq));
        } catch (Exception ignored) {
        }
        return false;
    }
}
