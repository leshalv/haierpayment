package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;

import com.haiercash.pluslink.capital.processer.api.dto.request.LoanSearchRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanSearchResponse;
import com.haiercash.pluslink.capital.processer.server.copier.LoanCopier;

import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lzh
 * @Title: LoanSearchService
 * @ProjectName pl-capital
 * @Description: 处理中心放款查询service
 * @date 2018/7/915:29
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class LoanSearchService extends BaseService {

    @Autowired
    private AssetsSplitService assetsSplitService;
    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    public RestResponse<LoanSearchResponse> search(RestRequest<LoanSearchRequest> restRequest) {

        LoanSearchResponse loanSearchResponse;
        //根据业务编号或者合同号查询资产表信息
        AssetsSplit assetsSplit = assetsSplitService.searchAssetsSplitByApplSeqOrContractNo(restRequest.getBody().getApplSeq(), restRequest.getBody().getContractNo());
        if (assetsSplit == null) {
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getCode(), "放款查询信息不存在，请求参数【" + JsonUtils.writeObjectToJson(restRequest) + "】", restRequest.getBody().getApplSeq(), null);
        }
        //根据资产表主键查询拆分明细表信息
        AssetsSplitItem assetsSplitItem = new AssetsSplitItem();
        assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
        //设置放款状态为成功或者失败的

        try {
            List<AssetsSplitItem> assetsSplitItems = assetsSplitItemService.selectByAssetsSpiltIdForCredit(assetsSplit.getId());
            //do转化vo
            loanSearchResponse = LoanCopier.loanSearchDoToVo(assetsSplit, assetsSplitItems);
            //设置支付请求号和交易流水号
            loanSearchResponse.setApplSeq(restRequest.getBody().getApplSeq());
            loanSearchResponse.setSerNo(restRequest.getRequestHead().getSerNo());
        } catch (Exception e) {
            log.error("========================<处理中心>-业务编号:{},查询放款详情失败:{}", assetsSplit.getApplSeq(), e.getMessage());
            throw new SystemException(CommonReturnCodeEnum.DATA_QUERY_EXCEPTION.getCode(), "放款查询信息不存在，请求参数【" + JsonUtils.writeObjectToJson(restRequest) + "】", restRequest.getBody().getApplSeq(), e);
        }
        //设置返回参数
        RestResponse<LoanSearchResponse> restResponse = new RestResponse<>();
        restResponse.setOK();
        restResponse.setBody(loanSearchResponse);
        log.info("========================<处理中心>-查询放款详情成功，返回详情[{}]======================",JsonUtils.safeObjectToJson(loanSearchResponse));
        return restResponse;
    }
}
