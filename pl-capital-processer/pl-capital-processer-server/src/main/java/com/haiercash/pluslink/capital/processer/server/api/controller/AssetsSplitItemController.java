package com.haiercash.pluslink.capital.processer.server.api.controller;

import cn.jbinfo.api.annotation.Action;
import cn.jbinfo.api.annotation.Login;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.bean.ApiVersion;
import cn.jbinfo.common.utils.JsonUtils;
import cn.jbinfo.integration.swagger.version.Api1;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitService;
import com.haiercash.pluslink.capital.processer.server.service.LoanDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xiaobin
 * @create 2018-08-08 下午7:47
 **/
@RestController
@RequestMapping("/api/splitItem")
@Api(tags = "资产拆分")
@Api1
@Slf4j
@Scope("request")
public class AssetsSplitItemController {

    @Autowired
    AssetsSplitService assetsSplitService;

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    @Autowired
    private LoanDetailService loanDetailService;

    @ApiVersion(1)
    @PostMapping(value = "/set/{versionId}/{splitId}/{status}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionId", value = "版本号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "splitId", value = "资产主键", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "资产主信息状态", required = true, paramType = "path", dataType = "String")
    })
    @ApiOperation(value = "拆分", produces = "application/json")
    @Login(action = Action.Skip)
    public RestResponse<Map<String, Object>> set(@PathVariable("versionId") String versionId, @PathVariable("splitId") String splitId,
                                                 @PathVariable("status") String status) {
        AssetsSplit assetsSplit = assetsSplitService.get(splitId);
        assetsSplitService.updateLoanStatusById(assetsSplit, PL0506Enum.getEnum(status));
        RestResponse<Map<String, Object>> restResponse = new RestResponse<>();
        //调用支付网关付款service
        //设置请求ip 从风险接口获取,测试时暂时查询不到ip写死，打出logger,后续去掉此处try catch
        try {
            assetsSplit.setRequestIp(loanDetailService.getAddrIp(
                    new ApplInfoAppRequestBody(assetsSplit.getApplSeq(), assetsSplit.getCertNo(), assetsSplit.getChannelNo(), null)));
        } catch (SystemException e) {
            //后续添加返回核心mq，修改资产表状态,抛出异常
            assetsSplit.setRequestIp("127.0.0.1");
        }
        assetsSplitItemService.assetsSplit(JsonUtils.writeObjectToJson(assetsSplit));
        restResponse.setOK();
        return restResponse;
    }
}
