package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.rest.client.PayMentGateWayClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWaySearchRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PayMentGateWayLoanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lzh
 * @Title: PayMentGateWayLoanService 支付网关放款接口调用
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1910:28
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class PayMentGateWayLoanService extends BaseService {
    @Autowired
    PayMentGateWayClient payMentGateWayClient;

    //通知支付网关放款
    public PayMentGateWayLoanResponse notifyLoan(PayMentGateWayLoanRequest payMentGateWayLoanRequest) {

        PayMentGateWayLoanResponse payMentGateWayLoanResponse;
        try {
            log.info("<处理中心>-合同号:{},支付网关放款请求，调用支付网关请求信息:{}", payMentGateWayLoanRequest.getElecChequeNo(), JsonUtils.writeObjectToJson(payMentGateWayLoanRequest));
            payMentGateWayLoanResponse = payMentGateWayClient.notifyLoan(payMentGateWayLoanRequest);
        } catch (Exception e) {
            log.error("<处理中心>-支付网关放款失败:{}", e);
            throw new SystemException(CommonReturnCodeEnum.DATA_COMM_EXCEPTION.getCode(), "【失败描述" + e.getMessage() + "】", null, e);
        }
        return payMentGateWayLoanResponse;
    }

    //查询支付网关放款结果
    public PayMentGateWayLoanResponse searchLoan(PayMentGateWaySearchRequest payMentGateWaySearchRequest) {
        return payMentGateWayClient.searchLoan(payMentGateWaySearchRequest);
    }

}
