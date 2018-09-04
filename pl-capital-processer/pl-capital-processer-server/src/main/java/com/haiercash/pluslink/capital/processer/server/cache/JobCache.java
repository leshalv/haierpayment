package com.haiercash.pluslink.capital.processer.server.cache;

import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-14 上午10:06
 **/
@Component
public class JobCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    public void putJob(String jobId) {
        try {
            redisCommonService.addRedis("job_" + jobId, jobId, 3, TimeEnum.minute);
        } catch (Exception ignored) {
        }
    }

    public boolean hasJob(String jobId) {
        try {
            return StringUtils.isNotBlank(redisCommonService.selectRedis(jobId));
        } catch (Exception ex) {
            return false;
        }
    }

    public void remove(String jobId) {
        try {
            redisCommonService.deleteRedis(jobId);
        } catch (Exception ignored) {
        }
    }
}
