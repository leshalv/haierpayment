package com.haiercash.pluslink.capital.router.client.biz;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import com.haiercash.pluslink.capital.router.client.ICommonSendClient;
import com.haiercash.pluslink.capital.router.client.request.GeneralInfoRequest;
import com.haiercash.pluslink.capital.router.client.request.LastLoanQueryRequest;
import com.haiercash.pluslink.capital.router.client.request.RouterRequest;
import com.haiercash.pluslink.capital.router.client.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TestRouterApi extends BaseTest {

    @Autowired
    private ICommonSendClient commonSendClient;

    /**通过地址访问**/
    //private String server = "localhost";
    private String server = "10.164.204.103";
    private String port = "7899";
    /**************/

    private String serviceVersion = "v1";

    private String charaCode = "utf-8";

    @Test
    public void testApi(){

        //调用路由通知接口
        testRouterMatch();
        //threadRouter(10);
//        while(true){
//            testRouterMatch();
//        }
        //综合查询接口
        //testGeneralInfo();
        //查询上次贷款距今天数
        //testLastLoan();
    }


    private void testRouterMatch(){
        String url = "/api/plusLink/capital/router/routerMatch/applyFor/" + serviceVersion;
        RestRequest<RouterRequest> request = new RestRequest();
        RouterRequest routerRequest = new RouterRequest();
        routerRequest.setApplSeq("123");//业务编号
        routerRequest.setCustId("CustId");//消金客户编号
        routerRequest.setIdNo("123");//客户身份证号
        routerRequest.setTypCde("18024a");//消金产品编号
        routerRequest.setCustSign("cf92ff4d567a4503b61a1eb6f205e602");//消金客户标签
        routerRequest.setChannelNo("19");//消金渠道编号
        routerRequest.setPeriod("6");
        routerRequest.setPeriodType("M");
        routerRequest.setRepayCardBankNo("104");//还款卡银行数字编码
        routerRequest.setOrigPrcp("1000");//放款金额
        request.setBody(routerRequest);
        request.setRequestHead(getHead("PLCR001"));
        commonApi(request,url);
    }

    private void commonApi(Object request,String url){
        Map<String, Object> map = commonSendClient.routerMatch(request,server,port,url,charaCode);
        System.out.println("map = " + map);
    }

    private void testGeneralInfo(){
        String url = "/api/plusLink/capital/router/queryGeneralInfo/applyFor/" + serviceVersion;
        RestRequest<GeneralInfoRequest> request = new RestRequest();
        GeneralInfoRequest generalInfoRequest = new GeneralInfoRequest();
        generalInfoRequest.setAgencyId("GONGSHANGYINHANG");
        generalInfoRequest.setProjectId("GSYHLHFK");
        request.setBody(generalInfoRequest);
        request.setRequestHead(getHead("PLCR002"));
        commonApi(request,url);
    }

    private void testLastLoan(){
        String url = "/api/acrm/market/thirtyDaysLoan";
        LastLoanQueryRequest requestBody = new LastLoanQueryRequest();
        requestBody.setIdNo("110101196403181348");
        requestBody.setLoanTyp("17073a");
        requestBody.setOperator("SYSTEM");
        commonApi(requestBody,url);
    }

    private RestRequestHead getHead(String tradeCode){
        RestRequestHead requestHead = new RestRequestHead();
        requestHead.setChannelNo("ChannelNo");//渠道编码
        requestHead.setSerNo("SerNo");//流水号
        requestHead.setSysFlag("SysFlag");//系统标识
        requestHead.setTradeCode(tradeCode);//交易码
        requestHead.setTradeDate(getDate());//交易日期
        requestHead.setTradeTime(getTime());//交易时间
        requestHead.setTradeType("TradeType");//交易类型
        return requestHead;
    }

    /**获取当前系统日期**/
    public String getDate(){
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
        return dt.format(new Date());
    }

    /**获取当前时间**/
    public String getTime(){
        SimpleDateFormat tm = (SimpleDateFormat) DateFormat.getDateInstance();
        tm.applyPattern("HHmmss");
        return tm.format(new Date());
    }


    int i = 0;
    public void threadRouter(int num){
        for(i=0;i<num;i++){
            Thread t = new Thread(new Runnable(){
                public void run(){
                    while(true){
                        testRouterMatch();
                        System.out.println(i);
                    }
                }
            });
            t.start();
        }
    }
}
