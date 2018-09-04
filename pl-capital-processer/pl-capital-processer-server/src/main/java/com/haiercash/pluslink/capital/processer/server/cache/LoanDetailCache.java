package com.haiercash.pluslink.capital.processer.server.cache;

import cn.jbinfo.common.utils.JsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LoanDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaobin
 * @create 2018-08-14 下午7:43
 **/
@Component
public class LoanDetailCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    private static final String KEY_ = "loan_detail_";

    public LoanDetailResponse get(ApplInfoAppRequestBody requestBody) {
        try {
            LoanDetailResponse response = redisCommonService.selectRedis(KEY_ + requestBody.toString(), new TypeReference<LoanDetailResponse>() {
            });
            if (response != null) {
                return response;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void put(ApplInfoAppRequestBody request, LoanDetailResponse response) {
        try {
            redisCommonService.addRedis(request.toString(), JsonUtils.writeObjectToJson(response), 1, TimeEnum.day);
        } catch (Exception ignored) {
        }
    }
}
