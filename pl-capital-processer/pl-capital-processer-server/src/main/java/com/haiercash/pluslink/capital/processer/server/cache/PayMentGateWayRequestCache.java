package com.haiercash.pluslink.capital.processer.server.cache;

import cn.jbinfo.cloud.core.utils.JsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lzh    通知支付网关放款请求信息缓存
 * @create 2018-08-14 下午4:17
 **/
@Component
@Slf4j
public class PayMentGateWayRequestCache {

    @Autowired
    private IRedisCommonService redisCommonService;

    private static final String KEY_ = "payCache";

    public void put(PayMentGateWayLoanRequest payMentGateWayLoanRequest) {
        try {
            if (payMentGateWayLoanRequest == null)
                return;
            redisCommonService.addRedis(KEY_ + payMentGateWayLoanRequest.getElecChequeNo(), JsonUtils.writeObjectToJson(payMentGateWayLoanRequest), 2, TimeEnum.day);
        } catch (Exception ex) {
            log.error("redis，读取支付网关放款请求信息出现异常：", ex);
        }
    }

    public PayMentGateWayLoanRequest get(String elecChequeNo) {
        try {
            return redisCommonService.selectRedis(KEY_ + elecChequeNo, new TypeReference<PayMentGateWayLoanRequest>() {
            });
        } catch (Exception ex) {
            log.error("从redis读取支付网关放款请求信息出现异常：", ex);
        }
        return null;
    }
}
