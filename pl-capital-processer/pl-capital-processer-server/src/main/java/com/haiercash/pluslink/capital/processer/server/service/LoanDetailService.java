package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.cloud.core.copier.BeanTemplate;
import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.common.utils.JsonUtils;
import cn.jbinfo.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.SystemFlagEnum;
import com.haiercash.pluslink.capital.processer.server.cache.LoanDetailCache;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.rest.client.AcquirerClient;
import com.haiercash.pluslink.capital.processer.server.rest.client.RiskInfoClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.*;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lzh
 * @Title: LoanDetailService 资产详情接口
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1814:37
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class LoanDetailService extends BaseService {

    @Autowired
    RiskInfoClient riskInfoClient;

    @Autowired
    AcquirerClient acquirerClient;

    @Autowired
    private LoanDetailCache loanDetailCache;

    /**
     * 调用详情信息
     *
     * @param applInfoAppRequestBody 消息请求体
     */
    public LoanDetailResponse selectApplInfoApp(ApplInfoAppRequestBody applInfoAppRequestBody) {
        LoanDetailResponse loanDetailResponse = loanDetailCache.get(applInfoAppRequestBody);
        if (loanDetailResponse != null) {
            return loanDetailResponse;
        }
        loanDetailResponse = new LoanDetailResponse();
        //封装详情接口所需的请求头信息
        //定义通用完整请求消息
        LoanRequest<LoanSecondRequest<RequestHead, ApplInfoAppRequestBody>> loanRequest = new LoanRequest<>();
        //定义通用二级包裹体信息
        LoanSecondRequest<RequestHead, ApplInfoAppRequestBody> loanSecondRequest = new LoanSecondRequest<>();
        //定义详情的请求head
        RequestHead applInfoAppHead = new RequestHead();
        applInfoAppHead.setTradeTime(DateUtils.format("HH:mm:ss"));
        applInfoAppHead.setSysFlag(SystemFlagEnum.PLCPS.getSystemFlag());//系统自定义后续加入枚举
        applInfoAppHead.setChannelNo(applInfoAppRequestBody.getChannelNo());//渠道编码默认
        applInfoAppHead.setSerno(IdGen.uuid());
        applInfoAppHead.setTradeCode("ACQ-1145");//交易码默认
        applInfoAppHead.setTradeDate(DateUtils.format("yyyy-MM-dd"));
        loanSecondRequest.setHead(applInfoAppHead);
        loanSecondRequest.setBody(applInfoAppRequestBody);
        loanRequest.setRequest(loanSecondRequest);
        //详情接口
        List<ApplInfoAppApptResponse> applInfoAppApptResponses;
        //详情消息商品列表消息主申请人列表消息
        List<ApplInfoAppGoodResponse> applInfoAppGoodResponses;
        //详情接口返回消息
        ApplInfoAppApptReponse loanReponse;
        try {
            //调用详情接口获取信息
            loanReponse = acquirerClient.selectApplInfoApp(loanRequest);
            //loanReponse = JsonUtils.readObjectByJson(json, ApplInfoAppApptReponse.class);
            log.info("========================<处理中心>-业务编号:{},请求信息，{}，调用贷款详情信息接口成功详情json:{}=======================", applInfoAppRequestBody.getApplSeq(),JsonUtils.writeObjectToJson(loanRequest),JsonUtils.writeObjectToJson(loanReponse));
        } catch (Exception e) {
            log.error("贷款详情接口出现异常:{}", e);
            throw new SystemException(CommonReturnCodeEnum.DATA_COMM_EXCEPTION.getCode(), "调用贷款详情接口通讯异常，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", applInfoAppHead.getSerno(), e);
        }
        try {
            applInfoAppApptResponses = loanReponse.getResponse().getBody().getApptList().getAppt();
            applInfoAppGoodResponses = loanReponse.getResponse().getBody().getGoodsList().getGood();
        } catch (Exception e) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "贷款详情信息[申请人、商品]不存在，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", applInfoAppHead.getSerno(), e);
        }
        if (applInfoAppApptResponses == null || applInfoAppApptResponses.size() == 0) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "贷款详情信息不存在，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", applInfoAppHead.getSerno(), null);
        }
        //遍历取得的申请人信息
        for (ApplInfoAppApptResponse aAppt : applInfoAppApptResponses) {
            //只取是主申请人的信息
            if (StringUtils.equals("01", aAppt.getApptTyp())) {
                loanDetailResponse = BeanTemplate.executeBean(aAppt, LoanDetailResponse.class);
                loanDetailResponse.setRelMobile(aAppt.getSpouseMobile());
                loanDetailResponse.setRelName(aAppt.getSpouseName());
                log.info("========================<处理中心>-业务编号:{},得到贷款详情信息配偶信息电话：{}，姓名{}=======", applInfoAppRequestBody.getApplSeq(), loanDetailResponse.getRelMobile(), loanDetailResponse.getRelName());
                if (StringUtils.isBlank(loanDetailResponse.getRelName()) || StringUtils.isBlank(loanDetailResponse.getRelMobile())) {
                    //由于联系人文档中是必填项，联系人list取一条即可
                    if (CollectionUtils.isNotEmpty(aAppt.getRelList().getRel())) {
                        loanDetailResponse.setRelMobile(aAppt.getRelList().getRel().get(0).getRelMobile());
                        loanDetailResponse.setRelName(aAppt.getRelList().getRel().get(0).getRelName());
                    }
                }
            }
        }
        //设置ip,
        loanDetailResponse.setAddrIp(applInfoAppRequestBody.getRequestIp());
        //获取商品信息,取第一条
        if (CollectionUtils.isNotEmpty(applInfoAppGoodResponses)) {
            loanDetailResponse.setGoodsName(applInfoAppGoodResponses.get(0).getGoodsName());
            //订单号用业务编号
            loanDetailResponse.setOrderNumber(applInfoAppRequestBody.getApplSeq());
        }
        //设置申请日期
        loanDetailResponse.setApplyDt(loanReponse.getResponse().getBody().getApplyDt());
        //设置分期期数
        loanDetailResponse.setApplyTnr(loanReponse.getResponse().getBody().getApplyTnr());
        /*//判断详情接口联系人信息
        if (StringUtils.isBlank(loanDetailResponse.getRelMobile()) || StringUtils.isBlank(loanDetailResponse.getRelName())) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "贷款详情信息联系人信息不存在，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", applInfoAppHead.getSerno(), null);
        }*/
        log.info("========================<处理中心>-业务编号:{},得到贷款详情信息成功================", applInfoAppRequestBody.getApplSeq());
        loanDetailCache.put(applInfoAppRequestBody, loanDetailResponse);
        return loanDetailResponse;
    }

    /**
     * 调用风险大数据风险接口得到申请人ip
     *
     * @param applInfoAppRequestBody 详情申请消息
     * @return
     */
    public String getAddrIp(ApplInfoAppRequestBody applInfoAppRequestBody) {
        //申请ip
        String addrIp = "";
        //得到ip
        //定义大数据风控的请求body
        RiskInfoRequestBody riskInfoBody = new RiskInfoRequestBody();
        List<RiskInfoRequestBodyDetail> list = new ArrayList<RiskInfoRequestBodyDetail>();
        RiskInfoRequestBodyDetail riskInfoBodyDetail = new RiskInfoRequestBodyDetail();
        //固定传1
        riskInfoBodyDetail.setSortNo("1");
        //固定传20
        riskInfoBodyDetail.setIdTyp("20");
        riskInfoBodyDetail.setIdNo(applInfoAppRequestBody.getCertNo());
        riskInfoBodyDetail.setApplSeq(applInfoAppRequestBody.getApplSeq());
        riskInfoBodyDetail.setDataTyp("A506");
        list.add(riskInfoBodyDetail);
        riskInfoBody.setList(list);
        //定义通用完整请求消息
        LoanRequest<LoanSecondRequest<RequestHead, RiskInfoRequestBody>> loanRequest = new LoanRequest<>();
        //定义通用二级包裹体信息
        LoanSecondRequest<RequestHead, RiskInfoRequestBody> loanSecondRequest = new LoanSecondRequest<>();
        //定义大数据风控的请求head
        RequestHead riskInfoHead = new RequestHead();
        riskInfoHead.setTradeTime(DateUtils.format("HH:mm:ss"));
        riskInfoHead.setSysFlag(SystemFlagEnum.PLCPS.getSystemFlag());//自己系统定义，后续加入枚举
        riskInfoHead.setChannelNo(applInfoAppRequestBody.getChannelNo()); //渠道编码非必传
        riskInfoHead.setSerno(IdGen.uuid());
        riskInfoHead.setTradeCode("100001");//自己系统的定义
        riskInfoHead.setTradeDate(DateUtils.format("yyyy-MM-dd"));
        //riskInfoHead.setTradeType("1"); 非必传
        loanSecondRequest.setHead(riskInfoHead);
        loanSecondRequest.setBody(riskInfoBody);
        loanRequest.setRequest(loanSecondRequest);
        List<RiskInfoBodyDetailResponse> rdrs = null;

        try {
            //调用大数据风险接口
            String json = riskInfoClient.getAddrIp(loanRequest);
            RiskLoanReponse loanReponse = JsonUtils.readObjectByJson(json, RiskLoanReponse.class);
            rdrs = loanReponse.getResponse().getBody().getList1();
        } catch (Exception e) {
            throw new SystemException(CommonReturnCodeEnum.DATA_COMM_EXCEPTION.getCode(), "调用风险详情接口通讯异常，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", riskInfoHead.getSerno(), e);
        }
        if (rdrs == null || rdrs.size() == 0) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "请求ip不存在，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", riskInfoHead.getSerno(), null);
        }
        for (RiskInfoBodyDetailResponse riskInfoBodyDetailResponse : rdrs) {
            //如果A506=dataTyp 则取出ip
            if ("A506".equalsIgnoreCase(riskInfoBodyDetailResponse.getDataTyp())) {
                addrIp = riskInfoBodyDetailResponse.getContent();
            }
        }
        if (StringUtils.isBlank(addrIp)) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "请求ip不存在，业务编号【" + applInfoAppRequestBody.getApplSeq() + "】", riskInfoHead.getSerno(), null);
        }
        return addrIp;
    }
}
