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
import com.haiercash.pluslink.capital.enums.dictionary.PL0108Enum;
import com.haiercash.pluslink.capital.router.api.dto.IRouterApi;
import com.haiercash.pluslink.capital.router.api.dto.request.RouterRequest;
import com.haiercash.pluslink.capital.router.api.dto.response.RouterResponse;
import com.haiercash.pluslink.capital.router.server.api.controller.enums.ApiReturnCodeEnums;
import com.haiercash.pluslink.capital.router.server.biz.IFundRouteRuleBiz;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchIn;
import com.haiercash.pluslink.capital.router.server.entity.RouterResult;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import com.haiercash.pluslink.capital.router.server.version.VersionConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 路由中心处理接口实现类
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
@RestController
@RequestMapping("/api/plusLink/capital/router/routerMatch")
@Api(tags = "路由中心处理")
@Api1
@Slf4j
public class RouterController extends BaseService implements IRouterApi {

    @Autowired
    private IFundRouteRuleBiz fundRouteRuleBiz;

    @Override
    @PostMapping(value = "/applyFor/{versionId}")
    @ResponseBody
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "versionId", value = "版本号", required = true, paramType = "path", dataType = "String")})
    @ApiOperation(value = "请求", produces = "application/json")
    @Login(action = Action.Skip)
    public RestResponse<RouterResponse> applyFor(@PathVariable("versionId") String versionId,@RequestBody(required=false) RestRequest<RouterRequest> restRequest) {

        Long startTime = System.currentTimeMillis();
        RestResponse<RouterResponse> response = new RestResponse();
        RestResponseHead head;
        RouterResponse responseBody = new RouterResponse();

        try {

            ParamUtil.checkInterVersion(VersionConstant.routerControllerVersion,versionId,"通知路由处理接口");

            checkData(restRequest,responseBody);//数据校验,只要数据不为空就封装请求返回一样的数据
            countTime(restRequest.getRequestHead().getSerNo(),restRequest.getBody().getApplSeq(),startTime,"数据校验");
            Long startDealRouterTime = System.currentTimeMillis();
            dealRouter(restRequest.getBody(),restRequest.getRequestHead(),responseBody);
            countTime(restRequest.getRequestHead().getSerNo(),restRequest.getBody().getApplSeq(),startDealRouterTime,"路由筛选");
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
                RouterRequest restBody = new RouterRequest();
                restRequest.setBody(restBody);
            }
            logger.error("Router：serNo(" + restRequest.getRequestHead().getSerNo() + ")路由中心处理接口处理异常,耗时间：" +  countTime(restRequest.getRequestHead().getSerNo(),restRequest.getBody().getApplSeq(),startTime,"总耗时") + "毫秒,Code = " + apiE.getCode() + ",request = " + JsonConverter.object2Json(restRequest) + ",Message = " + apiE.getMessage(),apiE);
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
                RouterRequest restBody = new RouterRequest();
                restRequest.setBody(restBody);
            }
            logger.error("Router：serNo(" + restRequest.getRequestHead().getSerNo() + ")路由中心处理接口处理异常,耗时间：" +  countTime(restRequest.getRequestHead().getSerNo(),restRequest.getBody().getApplSeq(),startTime,"总耗时") + "毫秒,request = " + JsonConverter.object2Json(restRequest) + ",Message = " + e.getMessage(),e);
            head = new RestResponseHead(
                    ApiReturnCodeEnums.unknow.getStatus(),
                    ApiReturnCodeEnums.unknow.getCode(),
                    ApiReturnCodeEnums.unknow.getDesc(),
                    restRequest.getRequestHead().getSerNo());
        }
        response.setBody(responseBody);
        response.setHead(head);
        logger.info("Router：serNo(" + restRequest.getRequestHead().getSerNo() + ")路由中心处理接口处理成功,耗时：" + countTime(restRequest.getRequestHead().getSerNo(),restRequest.getBody().getApplSeq(),startTime,"总耗时") + "毫秒");
        return response;
    }

    /**接口逻辑处理**/
    private void dealRouter(RouterRequest routerRequest,RestRequestHead requestHead,RouterResponse responseBody){
        //封装调用参数
        RouterMatchIn routerMatch = new RouterMatchIn();
        routerMatch.setApplSeq(routerRequest.getApplSeq());//业务编号
        routerMatch.setCustId(routerRequest.getCustId());//消金客户编号
        routerMatch.setTypCde(routerRequest.getTypCde());//消金产品编号
        routerMatch.setCustSign(routerRequest.getCustSign());//消金客户标签
        routerMatch.setChannelNo(routerRequest.getChannelNo());//消金渠道编号
        routerMatch.setPeriod(Long.parseLong(routerRequest.getPeriod()));//期数
        routerMatch.setPeriodType(routerRequest.getPeriodType());//期数类型
        routerMatch.setRepayCardBankNo(routerRequest.getRepayCardBankNo());//还款卡银行数字编码
        routerMatch.setOrigPrcp(new BigDecimal(routerRequest.getOrigPrcp()));//放款金额
        routerMatch.setIdNo(routerRequest.getIdNo());//客户身份证号
        routerMatch.setSerNo(requestHead.getSerNo());//报文流水号
        routerMatch.setSysFlag(requestHead.getSysFlag());//系统标识

        //以下参数预留,暂未使用
        //routerMatch.setCustLimit(new BigDecimal(routerRequest.getCustLimit()));//客户额度
        //routerMatch.setCustAge(routerRequest.getCustAge());//客户年龄
        //routerMatch.setCustSex(routerRequest.getCustSex());//客户性别:0-男,1-女,2-全部

        //调用方法
        RouterResult result = fundRouteRuleBiz.dealFundRouteMatch(routerMatch);


        responseBody.setAgencyId(result.getRouteResultRecord().getAgencyId());//合作机构编码
        responseBody.setAgencyIdName(result.getAgencyName());//合作机构名称
        responseBody.setProjectId(result.getRouteResultRecord().getProjectId());//合作项目编码
        responseBody.setProjectName(result.getProjectName());//合作项目名称
        responseBody.setIsCredit(result.getRouteResultRecord().getIsCredit());//是否需要征信
        responseBody.setCreditModleUrl(result.getRouteResultRecord().getCreditModleUrl());//合作机构征信模板路径
        responseBody.setIsUniteLoan(result.getRouteResultRecord().getIsUniteLoan());//是否联合放款
    }

    /**数据校验**/
    private void checkData(RestRequest<RouterRequest> restRequest,RouterResponse responseBody){
        if(null == restRequest || null == restRequest.getRequestHead() || null == restRequest.getBody()){
            throw new ApiException(ApiReturnCodeEnums.null_filed.getCode(),",报文头或报文体数据不能为空");
        }
        //数据校验(非空)
        ApiResource.validateWithException(ApiReturnCodeEnums.null_filed.getCode(),restRequest);

        //只要数据不为空就塞入返回数据
        responseBody.setApplSeq(restRequest.getBody().getApplSeq());//业务编号

        //校验参数长度
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getTradeCode(),20,"报文头-交易码",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getSysFlag(),10,"报文头-系统标识",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getSerNo(),30,"报文头-报文流水号",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getTradeDate(),30,"报文头-交易日期",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getTradeTime(),30,"报文头-交易时间",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getRequestHead().getChannelNo(),10,"报文头-渠道编码",restRequest.getRequestHead().getSerNo());


        ParamUtil.checkApiParamLength(restRequest.getBody().getApplSeq(),36,"报文体-业务编号",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getCustId(),36,"报文体-消金客户编号",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getIdNo(),20,"报文体-消金客户身份证号",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getTypCde(),36,"报文体-消金产品编号",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getCustSign(),36,"报文体-消金客户标签",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getChannelNo(),36,"报文体-消金渠道编号",restRequest.getRequestHead().getSerNo());
        BigDecimal period = ParamUtil.checkApiParamLength(restRequest.getBody().getPeriod(),10,0,"报文体-期数",restRequest.getRequestHead().getSerNo());
        restRequest.getBody().setPeriod(String.valueOf(period));
        ParamUtil.checkApiParamLength(restRequest.getBody().getPeriodType(),2,"报文体-期数类型",restRequest.getRequestHead().getSerNo());
        ParamUtil.checkApiParamLength(restRequest.getBody().getRepayCardBankNo(),20,"报文体-还款卡银行数字编码",restRequest.getRequestHead().getSerNo());
        BigDecimal origPrcp = ParamUtil.checkApiParamLength(restRequest.getBody().getOrigPrcp(),16,2,"报文体-放款金额",restRequest.getRequestHead().getSerNo());

        if(period.compareTo(BigDecimal.ZERO) < 0){//报文体-期数不能小于0
            throw new ApiException(ApiReturnCodeEnums.error_filed.getCode(),",报文体-期数不能小于0");
        }

        if(origPrcp.compareTo(BigDecimal.ZERO) <= 0){//报文体-放款金额不能小于或等于0
            throw new ApiException(ApiReturnCodeEnums.error_filed.getCode(),",报文体-放款金额不能小于或等于0");
        }

        //判断交易码
        if(!(PL0108Enum.PL0108_1_PLCR001.getCode().equals(restRequest.getRequestHead().getTradeCode()))){
            throw new ApiException(ApiReturnCodeEnums.error_tran_code.getCode(),"," + restRequest.getRequestHead().getTradeCode());
        }

        //判断调用方
        //TODO
        boolean systemIdFlag = true;
        if(!systemIdFlag){
            throw new ApiException(ApiReturnCodeEnums.unsupport_caller.getCode(),"," + restRequest.getRequestHead().getSysFlag());
        }
    }

    private Long countTime(String serNo,String applSeq,Long startTime,String message){
        Long endTime = System.currentTimeMillis();
        Long settleTime = endTime - startTime;
        logger.debug("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +",执行：" + message + "操作耗时：" + settleTime + "毫秒");
        return settleTime;
    }
}
