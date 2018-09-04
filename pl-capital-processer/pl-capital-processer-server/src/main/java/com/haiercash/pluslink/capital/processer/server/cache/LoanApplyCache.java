package com.haiercash.pluslink.capital.processer.server.cache;

import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-17 下午5:44
 **/
@Component
public class LoanApplyCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    private static final String KEY_ = "loan_apply_";

    public boolean check(String applSeq) {
        try {
            String response = redisCommonService.selectRedis(KEY_ + applSeq);
            if (StringUtils.isNotBlank(response)) {
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public void put(String applSeq) {
        try {
            redisCommonService.addRedis(KEY_ + applSeq, applSeq, 8, TimeEnum.second);
        } catch (Exception ignored) {
        }
    }

}
