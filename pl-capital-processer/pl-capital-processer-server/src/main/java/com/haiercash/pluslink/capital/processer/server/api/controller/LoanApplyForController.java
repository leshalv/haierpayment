package com.haiercash.pluslink.capital.processer.server.api.controller;

import cn.jbinfo.api.annotation.Action;
import cn.jbinfo.api.annotation.Login;
import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.common.ApiResource;
import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.ApiException;
import cn.jbinfo.bean.ApiVersion;
import cn.jbinfo.integration.swagger.version.Api1;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.dictionary.PL0108Enum;
import com.haiercash.pluslink.capital.processer.api.ILoanApplyForApi;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanBalancePayRequestInfo;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanApplyForResponse;
import com.haiercash.pluslink.capital.processer.server.cache.LoanApplyCache;
import com.haiercash.pluslink.capital.processer.server.enums.*;
import com.haiercash.pluslink.capital.processer.server.service.LoanApplyForService;
import com.haiercash.pluslink.capital.processer.server.service.LoanDetailService;
import com.haiercash.pluslink.capital.processer.server.utils.ParamCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author xiaobin
 * @create 2018-07-05 下午6:30
 **/
@RestController
@RequestMapping("/api/processer/loan")
@Api(tags = "放款请求处理")
@Api1
@Slf4j
@Scope("request")
public class LoanApplyForController implements ILoanApplyForApi {

    @Autowired
    private LoanApplyForService loanApplyForService;

    @Autowired
    LoanDetailService loanDetailService;

    @Autowired
    private LoanApplyCache loanApplyCache;

    @Override
    @ApiVersion(1)
    @PostMapping(value = "/applyFor/{versionId}")
    @ResponseBody
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "versionId", value = "版本号", required = true, paramType = "path", dataType = "String")})
    @ApiOperation(value = "申请", produces = "application/json")
    @Login(action = Action.Skip)
    public RestResponse<LoanApplyForResponse> applyFor(@PathVariable("versionId") String versionId, @RequestBody RestRequest<LoanApplyForRequest> restRequest) {

        //数据校验
        //校验交易码
        if (!PL0108Enum.PL0108_3_PLCP001.getCode().equals(restRequest.getRequestHead().getTradeCode())) {
            throw new ApiException(CommonReturnCodeEnum.UNSUPPORT_CALLER.getCode(), "交易码不正确，不支持调用方", restRequest.getRequestHead().getSerNo(), null);
        }
        if (loanApplyCache.check(restRequest.getBody().getApplSeq())) {
            throw new ApiException(CommonReturnCodeEnum.REPEATED.getCode(), "正在处理，请稍后。", restRequest.getRequestHead().getSerNo(), null);
        }
        loanApplyCache.put(restRequest.getBody().getApplSeq());
        ApiContextManager.setFdKey(restRequest.getBody().getContractNo());

        //校验空值
        ApiResource.validateWithException(CommonReturnCodeEnum.NULL_FILED.getCode(), restRequest);
        //校验balancePayTag为Yes时余额支付信息列表不能为空,如果不是个人，客户编号不能为空
        if ("YES".equalsIgnoreCase(restRequest.getBody().getBalancePayTag())) {
            List<LoanBalancePayRequestInfo> loanBalancePayRequestInfos = restRequest.getBody().getBalancePayRequest();
            if (CollectionUtils.isEmpty(loanBalancePayRequestInfos)) {
                throw new ApiException(CommonReturnCodeEnum.NULL_FILED.getCode(), "附后置余额支付请求为YES时，余额支付信息列表不能为空", restRequest.getRequestHead().getSerNo(), null);
            }
            for (LoanBalancePayRequestInfo lbpri : loanBalancePayRequestInfos) {
                if (!CRMTypeEnum.PERSONAL.getCode().equals(lbpri.getCrmType()) && StringUtils.isBlank(lbpri.getCrmNo())) {
                    throw new ApiException(CommonReturnCodeEnum.NULL_FILED.getCode(), "附后置余额支付请求为YES时，余额支付信息列表中客户为门店或商户时，客户编号不能为空", restRequest.getRequestHead().getSerNo(), null);
                }
            }
        }
        //校验长度
        checkLength(restRequest.getBody(), restRequest.getRequestHead().getSerNo());
        //校验枚举值
        //checkEnums(restRequest.getBody(), restRequest.getRequestHead().getSerNo());
        return loanApplyForService.applyFor(restRequest);
    }

    //校验参数长度(校验部分主要字段）)
    public void checkLength(LoanApplyForRequest loanApplyForRequest, String serNo) {
        //校验参数长度(校验部分主要字段）)
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getApplSeq(), 50, "业务编号", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getLoanNo1(), 30, "自主借据号", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getLoanNo2(), 30, "资方借据号", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getCertCode(), 50, "证件号码", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getMobile().toString(), 11, "手机号", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getTradeAmount().toString(), 15, 2, "交易金额（斩头后）", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getContractNo(), 50, "合同号", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getOrigPrcp().toString(), 15, 2, "贷款本金", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getTotalAmount().toString(), 15, 2, "交易总金额", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getPayeeCardNo(), 32, "放款账户", serNo);
        ParamCheckUtil.checkApiParamLength(loanApplyForRequest.getCrmNo(), 20, "客户ID", serNo);
        //如果个人放款校验证件类型喝证件号不能为空
        if (CRMTypeEnum.PERSONAL.getCode().equals(loanApplyForRequest.getCrmType())) {
            if (StringUtils.isBlank(loanApplyForRequest.getCertType()) || StringUtils.isBlank(loanApplyForRequest.getCertNo())) {
                throw new ApiException(CommonReturnCodeEnum.NULL_FILED.getCode(), "客户类型为个人时，证件类型或者证件号不能为空", serNo, null);
            }
            if (loanApplyForRequest.getCertType().length() > 10) {
                throw new ApiException(CommonReturnCodeEnum.OVERLONG_FILED.getCode(), "客户类型为个人时，证件类型长度超过10位", serNo, null);
            }
            if (loanApplyForRequest.getCertNo().length() > 20) {
                throw new ApiException(CommonReturnCodeEnum.OVERLONG_FILED.getCode(), "客户类型为个人时，证件号长度超过20位", serNo, null);
            }
        }
    }

    //校验枚举类
    public void checkEnums(LoanApplyForRequest loanApplyForRequest, String serNo) {
        if (!CRMTypeEnum.getEnumByPro(loanApplyForRequest.getCrmType())) {
            throw new ApiException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "客户类型不符合枚举类型值请检查", serNo, null);
        }
        if (!CertTypeEnum.getEnumByPro(loanApplyForRequest.getCertType())) {
            throw new ApiException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "证件类型不符合枚举类型值请检查", serNo, null);
        }
        if (!CardTypeEnum.getEnumByPro(loanApplyForRequest.getBankCardType())) {
            throw new ApiException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "银行卡类型不符合枚举类型值请检查", serNo, null);
        }
        if (!AccountNatureEnum.getEnumByPro(loanApplyForRequest.getChannelNature())) {
            throw new ApiException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "交易属性(对公or对私)不符合枚举类型值请检查", serNo, null);
        }
        if (!YesNoEnum.getEnumByPro(loanApplyForRequest.getBalancePayTag())) {
            throw new ApiException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "是否附后置余额支付请求不符合枚举类型值请检查", serNo, null);
        }
    }
}
