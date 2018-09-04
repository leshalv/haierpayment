package com.haiercash.pluslink.capital.router.server.biz;

import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.common.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.haiercash.pluslink.capital.common.redis.IJedisClusterService;
import com.haiercash.pluslink.capital.common.utils.JsonConverter;
import com.haiercash.pluslink.capital.common.utils.PositionSplitUtils;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.entity.PositionSplitOut;
import com.haiercash.pluslink.capital.router.server.BaseTest;
import com.haiercash.pluslink.capital.router.server.entity.LastLoanIn;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchData;
import com.haiercash.pluslink.capital.router.server.rest.controller.ILastLoanController;
import com.haiercash.pluslink.capital.router.server.service.IPublicDataService;
import com.haiercash.pluslink.capital.router.server.service.IRouterMatchDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.*;

public class TestRouterMatch extends BaseTest {

    @Autowired
    private IFundRouteRuleBiz fundRouteRuleBiz;
    @Autowired
    private IJedisClusterService jedisClusterService;
    @Autowired
    private IPublicDataService publicDataService;
    @Autowired
    private ILastLoanController lastLoanController;
    @Autowired
    IRouterMatchDataService routerMatchDataServiceImpl;

    //测试资金路由匹配规则
    @Test
    public void testRuleRuleMatch() {
//        MqParam mq = new MqParam();
//        mq.setBusiReqId("1111");
//        mq.setFundAmount(new BigDecimal("2000"));
//        mq.setRepayCard("201002505126");
//        mq.setCashProId("140b");
//        mq.setCashBusiChannel("38");
//        mq.setCashCustSign("B");
//        fundRouteRuleBiz.dealFundRouteMatch(mq);
    }

    @Test
    public void testMethod(){
        List list = new ArrayList();
        list.add( "first" );
        list.add( "second" );
        Map map = new HashMap();
        map.put("name", "json");
        map.put("bool", Boolean.TRUE);
        map.put("int", new Integer(1));
        map.put("arr", new String[] { "a", "b" });
        map.put("func", "function(i){ return this.arr[i]; }");
        String json = JSON.toJSON(map).toString();
        System.out.println("======" + json);
    }

    @Test
    public void testRedis(){
       logger.info("创建redis结果。。。。" + jedisClusterService);
    }

    @Test
    public void testPositionSplit(){
        PositionSplitIn in = new PositionSplitIn();
        in.setApplSeq("Seq");//业务编号
        in.setOrigPrcp(new BigDecimal("2000"));//放款金额
        in.setAgencyRatio(new BigDecimal("0.25"));//资方占比
        in.setAgencyId("agencyId");//合作机构ID
        in.setProjectId("projectId");//合作项目ID
        in.setLoanAmount(new BigDecimal("5000"));//合作项目放款总额
        in.setAlreadyLoanAmount(new BigDecimal("200"));//合作项目已放款额
        in.setLoanLimitDay(new BigDecimal("5000"));//合作项目限额(日)
        in.setAlreadyLoanLimitDay(new BigDecimal("200"));//合作项目当日放已放款额
        in.setLoanLimitMonth(new BigDecimal("5000"));//合作项目限额(月)
        in.setAlreadyLoanLimitMonth(new BigDecimal("200"));//合作项目当月已放款额
        PositionSplitOut out = PositionSplitUtils.dealPositionSplit(in);
        logger.info("头寸拆分后返回值 = " + JsonConverter.object2Json(out));
    }

    @Test
    public void getAlreadyPosition(){
        PositionSplitIn in = publicDataService.getAlreadyLoan("1");
        logger.info("=====" + JsonConverter.object2Json(in));
    }

    @Test
    public void getUuid(){
        int num = 5;
        for(int i=0;i<num;i++){
            logger.info("======" + IdGen.uuid());
        }
    }

    @Test
    public void checkBusiTime(){

        Integer timeNow = Integer.parseInt("030001");

        String startTime = "210000";
        String endTime = "030000";
        //logger.info("========" + ParamUtil.checkBusiTime(startTime,endTime,timeNow));
    }

    @Test
    public void checkLastLoan(){
        LastLoanIn lastLoanIn = new LastLoanIn();
        lastLoanIn.setLoanTyp("110101196403181348");
        lastLoanIn.setIdNo("17073a");
        int i = lastLoanController.checkLastLoanController(lastLoanIn,"","");
        logger.info("查询上次贷款距今期限 =====" + i);
    }

    @Test
    public void testRouterData(){
        Map<String,Map<String,RouterMatchData>> map = routerMatchDataServiceImpl.getRouterMatchData("","");
        logger.info("Map<String,Map<String,RouterMatchData>> ======= " + map);
    }

    @Test
    public void testDateChange(){
        String month = DateUtils.formatDate(new Date(),CommonConstant.DATE_PATTERN_YYYYMM);
        logger.info("=============" + month);
    }
}
