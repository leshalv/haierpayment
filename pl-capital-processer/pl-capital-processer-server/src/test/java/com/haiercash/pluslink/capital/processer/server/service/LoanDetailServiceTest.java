package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.server.pvm.vo.LoanResultVo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.RiskInfoRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.RiskInfoRequestBodyDetail;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LoanDetailResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LoanDetailServiceTest extends BaseTest {

    @Autowired
    LoanDetailService loanDetailService;

    @Test
    public void selectApplInfoApp() {
        //调用详情接口
        LoanDetailResponse loanDetailResponse =loanDetailService.selectApplInfoApp(new ApplInfoAppRequestBody("8623758", "123", "19","127.0.0.1"));
        System.out.println("1111");
    }

    @Test
    public void getAddrIp() {
        //定义大数据风控的请求body
        RiskInfoRequestBody riskInfoBody = new RiskInfoRequestBody();
        List<RiskInfoRequestBodyDetail> list = new ArrayList<RiskInfoRequestBodyDetail>();
        RiskInfoRequestBodyDetail riskInfoBodyDetail = new RiskInfoRequestBodyDetail();
        //固定传1
        riskInfoBodyDetail.setSortNo("1");
        riskInfoBodyDetail.setIdTyp("20");
        riskInfoBodyDetail.setIdNo("371083198712092524");
        riskInfoBodyDetail.setDataTyp("01");
        list.add(riskInfoBodyDetail);
        riskInfoBody.setList(list);
        //loanDetailService.getAddrIp(riskInfoBody);
    }
}