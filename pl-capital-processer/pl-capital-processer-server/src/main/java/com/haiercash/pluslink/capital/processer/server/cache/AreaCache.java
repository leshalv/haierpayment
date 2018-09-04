package com.haiercash.pluslink.capital.processer.server.cache;

import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.data.Area;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import com.haiercash.pluslink.capital.processer.server.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-08 上午9:06
 **/
@Component
public class AreaCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    @Autowired
    private AreaService areaService;

    public Area getByCode(String code) {
        try {
            Area area = redisCommonService.selectRedis("area_" + code, new TypeReference<Area>() {
            });
            if (area != null) {
                return area;
            }
        } catch (Exception ignored) {
        }
        Area area = areaService.get(code);
        if (area != null) {
            redisCommonService.addRedis("area_" + code, JsonUtils.writeObjectToJson(area), 1, TimeEnum.month);
        }
        return area;
    }


}
