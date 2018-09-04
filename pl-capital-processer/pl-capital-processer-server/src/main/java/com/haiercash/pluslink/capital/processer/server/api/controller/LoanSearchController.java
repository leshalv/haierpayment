package com.haiercash.pluslink.capital.processer.server.api.controller;

import cn.jbinfo.api.annotation.Action;
import cn.jbinfo.api.annotation.Login;
import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.common.ApiResource;
import cn.jbinfo.api.exception.ApiException;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.bean.ApiVersion;
import cn.jbinfo.common.utils.JsonUtils;
import cn.jbinfo.integration.swagger.version.Api1;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.dictionary.PL0108Enum;
import com.haiercash.pluslink.capital.processer.api.ILoanSearchApi;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanSearchRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanSearchResponse;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.service.LoanSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 放款查询接口
 *
 * @author xiaobin
 * @create 2018-07-16 下午1:11
 **/
@RestController
@RequestMapping("/api/processer/loan")
@Api(tags = "放款请求处理")
@Api1
@Slf4j
public class LoanSearchController implements ILoanSearchApi {

    @Autowired
    private LoanSearchService loanSearchService;

    @Override
    @ApiVersion(1)
    @PostMapping(value = "/search/{versionId}")
    @ResponseBody
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "versionId", value = "版本号", required = true, paramType = "path", dataType = "String")})
    @ApiOperation(value = "查询", produces = "application/json")
    @Login(action = Action.Skip)
    public RestResponse<LoanSearchResponse> search(@PathVariable("versionId") String versionId, @RequestBody RestRequest<LoanSearchRequest> restRequest) {
        //校验交易码
        if (!PL0108Enum.PL0108_4_PLCP002.getCode().equals(restRequest.getRequestHead().getTradeCode())) {
            throw new ApiException(CommonReturnCodeEnum.UNSUPPORT_CALLER.getCode(), "交易码不正确，不支持调用方", restRequest.getRequestHead().getSerNo(), null);
        }
        //数据校验 ,合同号或者支付请求号其中一个必填
        if(StringUtils.isBlank(restRequest.getBody().getApplSeq())&&StringUtils.isBlank(restRequest.getBody().getContractNo())){
            throw new ApiException(CommonReturnCodeEnum.NULL_FILED.getCode(), "查询放款信息失败，支付请求号和合同号两者必输其一", null, null);
        }
        return loanSearchService.search(restRequest);
    }
}
