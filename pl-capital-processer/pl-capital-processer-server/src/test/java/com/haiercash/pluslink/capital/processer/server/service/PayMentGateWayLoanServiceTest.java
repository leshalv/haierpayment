package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.cloud.core.copier.BeanTemplate;
import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanBalancePayRequestInfo;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanSearchResponse;
import com.haiercash.pluslink.capital.processer.server.rest.client.PayMentGateWayClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWayLoanRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PayMentGateWaySearchRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PayMentGateWayLoanResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Administrator
 * @Title: PayMentGateWayLoanServiceTest
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/207:43
 */
public class PayMentGateWayLoanServiceTest extends BaseTest {
    @Autowired
    PayMentGateWayLoanService payMentGateWayLoanService;
    @Autowired
    PayMentGateWayClient payMentGateWayClient;

    @Test
    public void notifyLoan() {
        LoanApplyForRequest loanApplyForRequest = new LoanApplyForRequest();
        loanApplyForRequest.setApplSeq("8624108");
        loanApplyForRequest.setCustName("张成成");
        loanApplyForRequest.setLoanNo1("自主借据号");
        loanApplyForRequest.setLoanNo2("资方借据号");
        loanApplyForRequest.setCertCode("34222219900425405X");
        loanApplyForRequest.setMobile(new BigDecimal("13210877098"));
        loanApplyForRequest.setTradeAmount(new BigDecimal(10.00));
        loanApplyForRequest.setCurrency("CNY");
        loanApplyForRequest.setContractNo("20170902203000003003");
        loanApplyForRequest.setOrigPrcp(new BigDecimal(200.00));
        loanApplyForRequest.setSubBusinessType("产品代码9003");
        loanApplyForRequest.setPayeeCardNo("6228212340012832450");
        loanApplyForRequest.setPayeeName("张成成");
        loanApplyForRequest.setCrmType("PERSONAL");
        loanApplyForRequest.setCrmNo("34222219900425405X");
        loanApplyForRequest.setCertType("ID_CARD");
        loanApplyForRequest.setCertNo("34222219900425405X");
        loanApplyForRequest.setBankCode("309");
        loanApplyForRequest.setBankCardType("DEBIT_CARD");
        loanApplyForRequest.setBankUnionCode("308100005256");
        loanApplyForRequest.setChannelNature("FOR_CORPORATE");
        //loanApplyForRequest.setInterId("");
        loanApplyForRequest.setOpeningBankCity("青岛市");
        loanApplyForRequest.setOpeningBankProvince("山东省");
        loanApplyForRequest.setBalancePayTag("YES");
        loanApplyForRequest.setTotalAmount(new BigDecimal(11.00));
        //余额支付信息
        List<LoanBalancePayRequestInfo> balancePayList = new ArrayList<>();
        LoanBalancePayRequestInfo loanBalancePayRequestInfo = new LoanBalancePayRequestInfo();
        loanBalancePayRequestInfo.setCrmType("MERCHANT");
        loanBalancePayRequestInfo.setCustName("星火教育青岛市香港中路分公司");
        loanBalancePayRequestInfo.setCertNo("0");
        loanBalancePayRequestInfo.setVaType("1003");
        loanBalancePayRequestInfo.setPayAmt(new BigDecimal(1.00));
        balancePayList.add(loanBalancePayRequestInfo);
        loanApplyForRequest.setBalancePayRequest(balancePayList);
        RestRequest<LoanApplyForRequest> restRequest = new RestRequest();
        restRequest.setBody(loanApplyForRequest);
        //放款请求的消息转化为支付网关放款请求
        PayMentGateWayLoanRequest payMentGateWayLoanRequest = BeanTemplate.executeBean(loanApplyForRequest, PayMentGateWayLoanRequest.class);
        //固定值
        payMentGateWayLoanRequest.setPiType("TRANSFER");
        //我们自己的系统标识
        payMentGateWayLoanRequest.setMerchantNo("loan_core");
        //凭证号填合同id
        payMentGateWayLoanRequest.setElecChequeNo(loanApplyForRequest.getContractNo());
        payMentGateWayLoanRequest.setRequestTime(DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        //到账模式固定实时
        payMentGateWayLoanRequest.setArrivalMode("REAL");
        payMentGateWayLoanRequest.setRequestIp("127.0.0.1");
        //业务流水号传合同编号
        payMentGateWayLoanRequest.setBusinessPayNo("贷款业务流水03");
        payMentGateWayLoanRequest.setInstitutionVersion("V1");
        loanBalancePayRequestInfo.setCrmNo("332017001318");
        //PayMentGateWayLoanResponse payMentGateWayLoanResponse=payMentGateWayLoanService.notifyLoan(payMentGateWayLoanRequest);

        PayMentGateWayLoanResponse payMentGateWayLoanResponse = payMentGateWayClient.notifyLoan(payMentGateWayLoanRequest);


        System.out.println("11111");
    }

    @Test
    public void searchLoan() {
        PayMentGateWaySearchRequest payMentGateWaySearchRequest = new PayMentGateWaySearchRequest();
        //合同id
        payMentGateWaySearchRequest.setBusinessPayNo("req_no_o1");
        //支付工具
        payMentGateWaySearchRequest.setPiType("TRANSFER");
        //合同id
        payMentGateWaySearchRequest.setElecChequeNo("req_no_o1");
        payMentGateWayLoanService.searchLoan(payMentGateWaySearchRequest);
    }
}