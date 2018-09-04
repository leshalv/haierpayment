package com.haiercash.pluslink.capital.processer.server.copier;

import cn.jbinfo.cloud.core.copier.BeanTemplate;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentBalancePayRequestInfo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentFinancePayRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 放款处理请求消息体--->通知支付网关放款请求体
 * <p>
 * 转换器
 *
 * @author xiaobin
 * @create 2018-07-31 上午10:54
 **/
public class LoanApplyForCopier {

    public static PayMentGateWayLoanRequest convert(LoanApplyForRequest loanApplyForRequest) {
        PayMentGateWayLoanRequest payMentGateWayLoanRequest = BeanTemplate.executeBean(loanApplyForRequest, PayMentGateWayLoanRequest.class);

        if (CollectionUtils.isNotEmpty(loanApplyForRequest.getBalancePayRequest())) {
            List<PayMentBalancePayRequestInfo> balancePayRequest = BeanTemplate.execute(loanApplyForRequest.getBalancePayRequest(), PayMentBalancePayRequestInfo.class);
            payMentGateWayLoanRequest.setBalancePayRequest(balancePayRequest);
        }
        if (loanApplyForRequest.getFinancePayRequest() != null) {
            PayMentFinancePayRequest financePayRequest = BeanTemplate.executeBean(loanApplyForRequest.getFinancePayRequest(), PayMentFinancePayRequest.class);
            payMentGateWayLoanRequest.setFinancePayRequest(financePayRequest);
        }
        return payMentGateWayLoanRequest;
    }
}
