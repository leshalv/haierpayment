package com.haiercash.pluslink.capital.processer.server.api.controller;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.common.rest.HttpClientUtil;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanBalancePayRequestInfo;
import org.apache.http.HttpException;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzh
 * @Title: LoanApplyForControllerTest
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/2013:45
 */
public class LoanApplyForControllerTest {

    //使用最新的
    @Test
    public void applyForPayMent() throws IOException, HttpException {
        LoanApplyForRequest loanApplyForRequest = new LoanApplyForRequest();
        loanApplyForRequest.setApplSeq("1265186");
        loanApplyForRequest.setCustName("星火教育青岛市香港中路分公司");
        loanApplyForRequest.setLoanNo1("自主借据号");
        loanApplyForRequest.setLoanNo2("资方借据号");
        loanApplyForRequest.setCertCode("110101199103040450");
        loanApplyForRequest.setMobile(new BigDecimal("13210877098"));
        loanApplyForRequest.setTradeAmount(new BigDecimal(10.00));
        loanApplyForRequest.setCurrency("CNY");
        loanApplyForRequest.setContractNo("2017090220400002027");
        loanApplyForRequest.setOrigPrcp(new BigDecimal(200.00));
        loanApplyForRequest.setSubBusinessType("产品代码001");
        loanApplyForRequest.setPayeeCardNo("6228212340012832450");
        loanApplyForRequest.setPayeeName("张成成");
        loanApplyForRequest.setCrmType("PERSONAL");
        loanApplyForRequest.setCrmNo("110101199103040450");
        loanApplyForRequest.setCertType("ID_CARD");
        loanApplyForRequest.setCertNo("110101199103040450");
        loanApplyForRequest.setBankCode("001");
        loanApplyForRequest.setBankCardType("DEBIT_CARD");
        loanApplyForRequest.setBankUnionCode("001");
        loanApplyForRequest.setChannelNature("FOR_CORPORATE");
        //loanApplyForRequest.setInterId("");
        loanApplyForRequest.setOpeningBankCity("青岛市");
        loanApplyForRequest.setOpeningBankProvince("山东省");
        loanApplyForRequest.setBalancePayTag("YES");
        loanApplyForRequest.setTotalAmount(new BigDecimal(11.00));
        //余额支付信息转化Vo
        //余额支付信息
        List<LoanBalancePayRequestInfo> balancePayList = new ArrayList<>();
        LoanBalancePayRequestInfo loanBalancePayRequestInfo = new LoanBalancePayRequestInfo();
        loanBalancePayRequestInfo.setCrmType("MERCHANT");
        loanBalancePayRequestInfo.setCustName("星火教育青岛市香港中路分公司");
        //客户编号是商户必填需要确认
        // loanBalancePayRequestInfo.setCrmNo("332017001318");
        loanBalancePayRequestInfo.setCertNo("0");
        loanBalancePayRequestInfo.setVaType("1003");
        loanBalancePayRequestInfo.setPayAmt(new BigDecimal(1.00));
        balancePayList.add(loanBalancePayRequestInfo);
        loanApplyForRequest.setBalancePayRequest(balancePayList);
        RestRequestHead head = new RestRequestHead("PLCP001", "11", "19");
        RestRequest<LoanApplyForRequest> restRequest = new RestRequest<>();
        restRequest.setRequestHead(head);
        restRequest.setBody(loanApplyForRequest);
        HttpClientUtil.sendPostByRest("http://127.0.0.1:6001/api/processer/loan/applyFor/v1", null, restRequest);
        System.out.println(JsonUtils.safeObjectToJson(restRequest));

    }


}
