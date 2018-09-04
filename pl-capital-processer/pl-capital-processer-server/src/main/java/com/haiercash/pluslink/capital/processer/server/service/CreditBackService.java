package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.base.RestResponseHead;
import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.cloud.core.utils.MyBeanUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.processer.api.dto.ResCreditBackVo;
import com.haiercash.pluslink.capital.processer.api.dto.response.CreditBackResponse;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditBackContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


/**
 * @author lzh
 * 授信回调service
 * @date 2018/7/915:29
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class CreditBackService extends BaseService implements Serializable {

    @Transactional
    public RestResponse<CreditBackResponse> creditCallback(RestRequest<ResCreditBackVo> restRequest) {
        log.info(">>>>>>>>>>>>>>>>授信回调Service<<<<<<<<<<<<<<<<");
        ResCreditBackVo backResponseVoDTO = restRequest.getBody();

        Quota quota = quotaService.selectByCorpMsgId(backResponseVoDTO.getCorpMsgId());

        if (quota != null && StringUtils.isNotBlank(quota.getCinoMemno())) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "额度信息为空，消息ID：【" + backResponseVoDTO.getCorpMsgId() + "】", ApiContextManager.getSerNo(), null);
        }
        if (quota != null && StringUtils.isNotBlank(backResponseVoDTO.getCinoMemno())) {
            quota.setCinoMemno(backResponseVoDTO.getCinoMemno());
            quota.setCooprUserId(backResponseVoDTO.getCooprUserId());
            //quota.setAssetsSplitItemId(backResponseVoDTO.get);
            quota.setStatus(Quota.BACK_OK);
            quotaService.updateByCallBackData(quota);
        } else {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "额度信息为空，消息ID：【" + backResponseVoDTO.getCorpMsgId() + "】", ApiContextManager.getSerNo(), null);
        }

        AssetsSplitItem splitItem = assetsSplitItemService.get(quota.getAssetsSplitItemId());
        AssetsSplit split = assetsSplitService.get(splitItem.getAssetsSplitId());

        RestResponseHead responseHead = new RestResponseHead();
        MyBeanUtils.copyProperties(restRequest.getRequestHead(), responseHead);
        RestResponse<ResCreditBackVo> restResponse = new RestResponse<>(responseHead, restRequest.getBody());
        CreditBackContext creditBackContext = new CreditBackContext(split, splitItem, restResponse, quota, backResponseVoDTO.getCorpMsgId());

        flowWorkerServer.creditApplyBack(creditBackContext, this.getClass().getName());

        //设置返回信息
        RestResponse<CreditBackResponse> resCreditBackResponseRestResponse = new RestResponse<>();
        CreditBackResponse resCreditBackResponse = new CreditBackResponse();
        resCreditBackResponse.setCorpMsgId(backResponseVoDTO.getCorpMsgId());

        resCreditBackResponseRestResponse.setOK();
        return resCreditBackResponseRestResponse;
    }

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    @Autowired
    private AssetsSplitService assetsSplitService;

    @Autowired
    private FlowWorkerServer flowWorkerServer;
}
