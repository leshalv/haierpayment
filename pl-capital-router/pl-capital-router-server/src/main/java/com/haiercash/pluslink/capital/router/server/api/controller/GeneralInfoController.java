package com.haiercash.pluslink.capital.router.server.api.controller;

import cn.jbinfo.api.annotation.Action;
import cn.jbinfo.api.annotation.Login;
import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestRequestHead;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.base.RestResponseHead;
import cn.jbinfo.api.common.ApiResource;
import cn.jbinfo.api.exception.ApiException;
import cn.jbinfo.bean.ApiVersion;
import cn.jbinfo.integration.swagger.version.Api1;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.common.utils.JsonConverter;
import com.haiercash.pluslink.capital.data.CooperationPeriod;
import com.haiercash.pluslink.capital.data.RepaymentInfo;
import com.haiercash.pluslink.capital.enums.dictionary.PL0108Enum;
import com.haiercash.pluslink.capital.router.api.dto.IGeneralInfoApi;
import com.haiercash.pluslink.capital.router.api.dto.request.GeneralInfoRequest;
import com.haiercash.pluslink.capital.router.api.dto.response.GeneralInfoResponse;
import com.haiercash.pluslink.capital.router.api.dto.response.list.CooperationPeriodResponse;
import com.haiercash.pluslink.capital.router.api.dto.response.list.RepaymentInfoResponse;
import com.haiercash.pluslink.capital.router.server.api.controller.enums.ApiReturnCodeEnums;
import com.haiercash.pluslink.capital.router.server.biz.IApiQueryBiz;
import com.haiercash.pluslink.capital.router.server.entity.GeneralInfoMatch;
import com.haiercash.pluslink.capital.router.server.entity.GeneralInfoResult;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import com.haiercash.pluslink.capital.router.server.version.VersionConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 资金方综合信息查询接口实现类
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
@RestController
@RequestMapping("/api/plusLink/capital/router/queryGeneralInfo")
@Api(tags = "资方综合信息查询接口")
@Api1
@Slf4j
public class GeneralInfoController extends BaseService implements IGeneralInfoApi {

    @Autowired
    private IApiQueryBiz apiQueryBiz;

    @PostMapping(value = "/applyFor/{versionId}")
    @ResponseBody
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "versionId", value = "版本号", required = true, paramType = "path", dataType = "String")})
    @ApiOperation(value = "请求", produces = "application/json")
    @Login(action = Action.Skip)
    public RestResponse<GeneralInfoResponse> applyFor(@PathVariable("versionId") String versionId,@RequestBody(required=false) RestRequest<GeneralInfoRequest> restRequest){

        Long startTime = System.currentTimeMillis();
        RestResponse<GeneralInfoResponse> response = new RestResponse();
        RestResponseHead head;
        GeneralInfoResponse responseBody = new GeneralInfoResponse();
        try {

            ParamUtil.checkInterVersion(VersionConstant.generalInfoControllerVersion,versionId,"资金方综合信息查询接口");

            checkData(restRequest,responseBody);//数据校验
            countTime(restRequest.getRequestHead().getSerNo(),startTime,"数据校验");
            Long startQueryTime = System.currentTimeMillis();
            dealGeneralInfo(restRequest,responseBody);
            countTime(restRequest.getRequestHead().getSerNo(),startQueryTime,"数据查询");
            head = new RestResponseHead(
                    ApiReturnCodeEnums.success.getStatus(),
                    ApiReturnCodeEnums.success.getCode(),
                    ApiReturnCodeEnums.success.getDesc(),
                    restRequest.getRequestHead().getSerNo());
        }catch(ApiException apiE){
            if(null == restRequest){
                restRequest = new RestRequest();
            }
            if(null == restRequest.getRequestHead()){
                RestRequestHead restHead = new RestRequestHead();
                restRequest.setRequestHead(restHead);
            }
            if(null == restRequest.getBody()){
                GeneralInfoRequest restBody = new GeneralInfoRequest();
                restRequest.setBody(restBody);
            }
            logger.error("GeneralInfo：serNo(" + restRequest.getRequestHead().getSerNo() + ")" + "资金方综合信息查询接口处理异常,耗时：" + countTime(restRequest.getRequestHead().getSerNo(),startTime,"总耗时") + "毫秒,Code = " + apiE.getCode() + ",request = " + JsonConverter.object2Json(restRequest) + ",Message = " + apiE.getMessage(),apiE);
            ApiReturnCodeEnums returMene = ApiReturnCodeEnums.getEnum(apiE.getCode());
            head = new RestResponseHead(
                    returMene.getStatus(),
                    returMene.getCode(),
                    returMene.getDesc() + apiE.getMessage(),
                    restRequest.getRequestHead().getSerNo());
        }catch(Exception e){
            if(null == restRequest){
                restRequest = new RestRequest();
            }
            if(null == restRequest.getRequestHead()){
                RestRequestHead restHead = new RestRequestHead();
                restRequest.setRequestHead(restHead);
            }
            if(null == restRequest.getBody()){
                GeneralInfoRequest restBody = new GeneralInfoRequest();
                restRequest.setBody(restBody);
            }
            logger.error("GeneralInfo：serNo(" + restRequest.getRequestHead().getSerNo() + ")" + "资金方综合信息查询接口处理异常,耗时：" + countTime(restRequest.getRequestHead().getSerNo(),startTime,"总耗时") + "毫秒,request = " + JsonConverter.object2Json(restRequest) + ",Message = " + e.getMessage(),e);
            head = new RestResponseHead(
                    ApiReturnCodeEnums.unknow.getStatus(),
                    ApiReturnCodeEnums.unknow.getCode(),
                    ApiReturnCodeEnums.unknow.getDesc(),
                    restRequest.getRequestHead().getSerNo());
        }
        response.setBody(responseBody);
        response.setHead(head);
        logger.info("GeneralInfo：serNo(" + restRequest.getRequestHead().getSerNo() + ")" + "合作机构主键ID:" + restRequest.getBody().getAgencyId() + ",合作项目主键ID:" + restRequest.getBody().getProjectId() + ",查询成功...耗时：" + countTime(restRequest.getRequestHead().getSerNo(),startTime,"总耗时") + "毫秒");
        return response;
    }

    /**接口逻辑处理**/
    private void dealGeneralInfo(RestRequest<GeneralInfoRequest> restRequest,GeneralInfoResponse responseBody){
        //封装调用参数
        GeneralInfoRequest generalInfoRequest = restRequest.getBody();
        RestRequestHead requestHead = restRequest.getRequestHead();

        GeneralInfoMatch generalInfoMatch = new GeneralInfoMatch();
        generalInfoMatch.setAgencyId(generalInfoRequest.getAgencyId());
        generalInfoMatch.setProjectId(generalInfoRequest.getProjectId());

        //调用方法
        GeneralInfoResult result = apiQueryBiz.querGeneralInfo(generalInfoMatch,requestHead.getSerNo());

        //封装返回参数
        responseBody.setNoBusiTimeStart(result.getProject().getNoBusiTimeStart());//业务每日暂停受理开始时间
        responseBody.setNoBusiTimeEnd(result.getProject().getNoBusiTimeEnd());//业务每日暂停受理结束时间
        responseBody.setCompensatoryTime(result.getProject().getCompensatoryTime());//代偿时间
        responseBody.setIsAssure(result.getProject().getIsAssure());//是否担保
        responseBody.setColralId(result.getProject().getCollateralId());//担保机构编码
        responseBody.setEffectTime(ParamUtil.changeDateTime(result.getProject().getEffectTime()));//项目生效时间
        responseBody.setCashYieldRate(result.getProject().getCashYieldRate());//消金收益率
        responseBody.setAgencyYieldRate(result.getProject().getAgencyYieldRate());//合作机构收益率
        responseBody.setCustLimitStart(result.getProject().getCustLimitStart());//用户额度区间开始
        responseBody.setCustLimitEnd(result.getProject().getCustLimitEnd());//用户额度区间结束
        responseBody.setCustLoanStart(result.getProject().getCustLoanStart());//用户贷款区间开始
        responseBody.setCustLoanEnd(result.getProject().getCustLoanEnd());//用户贷款区间结束
        responseBody.setCustSexDimension(result.getProject().getCustSexDimension());//用户性别维度
        responseBody.setCustAgeStart(result.getProject().getCustAgeStart());//用户年龄区间开始
        responseBody.setCustAgeEnd(result.getProject().getCustAgeEnd());//用户年龄区间结束
        responseBody.setCustFirstCreditAgo(result.getProject().getCustFirstCreditAgo());//用户首次用信距今天数
        responseBody.setTermCharge(result.getProject().getTermCharge());//期限服务费率

        //还款方式
        List<RepaymentInfoResponse> repaymentInfo= new ArrayList();
        for(RepaymentInfo ri : result.getRepayList()){
            RepaymentInfoResponse rp = new RepaymentInfoResponse();
            rp.setRepaymentTypeId(ri.getRepaymentType());
            repaymentInfo.add(rp);
        }

        //期限
        List<CooperationPeriodResponse> cooperationPeriod = new ArrayList();
        for(CooperationPeriod cp : result.getPeriodList()){
            CooperationPeriodResponse cpr = new CooperationPeriodResponse();
            cpr.setPeriod(cp.getCooperationPeriodValue());//期数
            cpr.setPeriodType(cp.getCooperationPeriodType());//期数类型
            cooperationPeriod.add(cpr);
        }
        responseBody.setRepaymentInfo(repaymentInfo);
        responseBody.setCooperationPeriod(cooperationPeriod);
    }

    /**数据校验**/
    private void checkData(RestRequest<GeneralInfoRequest> restRequest,GeneralInfoResponse responseBody){
        if(null == restRequest || null == restRequest.getRequestHead() || null == restRequest.getBody()){
            throw new ApiException(ApiReturnCodeEnums.null_filed.getCode(),",报文头或报文体数据不能为空");
        }
        //数据校验(非空)
        ApiResource.validateWithException(ApiReturnCodeEnums.null_filed.getCode(),restRequest);

        //只要数据不为空就塞入返回数据
        responseBody.setAgencyId(restRequest.getBody().getAgencyId());
        responseBody.setProjectId(restRequest.getBody().getProjectId());

        //校验参数长度
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getTradeCode(),20,"报文头-交易码",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getSysFlag(),10,"报文头-系统标识",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getSerNo(),30,"报文头-报文流水号",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getTradeDate(),30,"报文头-交易日期",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getTradeTime(),30,"报文头-交易时间",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getChannelNo(),10,"报文头-渠道编码",restRequest.getRequestHead().getSerNo());


        ParamUtil.checkApiParamLength(restRequest.getBody().getAgencyId(),36,"报文体-合作机构主键ID",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getProjectId(),36,"报文体-合作项目主键ID",restRequest.getRequestHead().getSerNo());

        //判断交易码
        if(!(PL0108Enum.PL0108_2_PLCR002.getCode().equals(restRequest.getRequestHead().getTradeCode()))){
            throw new ApiException(ApiReturnCodeEnums.error_tran_code.getCode(),"," + restRequest.getRequestHead().getTradeCode());
        }

        //判断调用方
        //TODO
        boolean systemIdFlag = true;
        if(!systemIdFlag){
            throw new ApiException(ApiReturnCodeEnums.unsupport_caller.getCode(),"," + restRequest.getRequestHead().getSysFlag());
        }
    }

    private Long countTime(String serNo,Long startTime,String message){
        Long endTime = System.currentTimeMillis();
        Long settleTime = endTime - startTime;
        logger.debug("GeneralInfo：serNo(" + serNo + ")" + ",执行：" + message + "操作耗时：" + settleTime + "毫秒");
        return settleTime;
    }
}
