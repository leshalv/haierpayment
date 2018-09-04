package com.haiercash.pluslink.capital.processer.server.api.controller;

import cn.jbinfo.api.annotation.Action;
import cn.jbinfo.api.annotation.Login;
import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.bean.ApiVersion;
import cn.jbinfo.integration.swagger.version.Api1;
import com.haiercash.pluslink.capital.processer.api.ICreditCallbackApi;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.api.dto.response.CreditBackResponse;
import com.haiercash.pluslink.capital.processer.server.service.CreditBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;


/**
 * @author lzh
 * @create 2018-07-05 下午6:30
 **/
@RestController
@RequestMapping("/api/credit")
@Api(tags = "授信回调接口")
@Api1
@Slf4j
@Scope("request")
public class CreditBackController implements ICreditCallbackApi {

    @Autowired
    CreditBackService resCreditBackService;

    @Override
    @ApiVersion(1)
    @PostMapping(value = "/creditback/{versionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionId", value = "版本号", required = true, paramType = "path", dataType = "String")
    })
    @ApiOperation(value = "回调", produces = "application/json")
    @Login(action = Action.Skip)
    public RestResponse<CreditBackResponse> creditCallback(@PathVariable("versionId") final String versionId,
                                                           @RequestBody RestRequest<ResCreditBackVo> restRequest) {
        //数据校验
        //todo
        return resCreditBackService.creditCallback(restRequest);
    }
}
