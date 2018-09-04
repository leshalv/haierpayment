package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.base.RestRequest;
import cn.jbinfo.api.base.RestResponse;
import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.BalancePayInfo;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.processer.api.dto.request.LoanApplyForRequest;
import com.haiercash.pluslink.capital.processer.api.dto.response.LoanApplyForResponse;
import com.haiercash.pluslink.capital.processer.server.copier.LoanCopier;
import com.haiercash.pluslink.capital.processer.server.enums.CRMTypeEnum;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.utils.threading.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzh
 * @Title: LoanService
 * @ProjectName pl-capital
 * @Description: 处理中心放款接口service
 * @date 2018/7/915:29
 */
@Service
@Slf4j
public class LoanApplyForService extends BaseService {
    @Autowired
    private AssetsSplitService assetsSplitService;
    @Autowired
    private LoanApplyForHandleService loanApplyForHandleService;
    @Autowired
    private RouteResultRecordService routeResultRecordService;
    @Autowired
    private CrmCustService crmCustService;

    //都是同步的不需要事物回滚
    public RestResponse<LoanApplyForResponse> applyFor(RestRequest<LoanApplyForRequest> restRequest) {
        RestResponse<LoanApplyForResponse> restResponse = new RestResponse<>();
        LoanApplyForResponse lendingResponse = new LoanApplyForResponse();
        LoanApplyForRequest loanApplyForRequest = restRequest.getBody();
        String serNo = ApiContextManager.getSerNo();
        //请求头消息与数据库实体转换
        AssetsSplit assetsSplitmq;
        try {
            AssetsSplit assetsSplit = LoanCopier.loanRequestVoToAssetsSplitDo(restRequest.getBody());
            //设置渠道号
            assetsSplit.setChannelNo(restRequest.getRequestHead().getChannelNo());
            //调用CRM接口查询客户编号
            //判断客户类型如果是个人去crm查询客户编号，否则直接用客户id
            if (CRMTypeEnum.PERSONAL.getCode().equals(assetsSplit.getCrmType())){
                assetsSplit.setCrmNum( queryMerchCustInfo(loanApplyForRequest));
            }else{
                assetsSplit.setCrmNum(assetsSplit.getCrmNo());
            }
            //余额信息的vo转do
            List<BalancePayInfo> balancePayInfos = LoanCopier.loanRequestVoToBalancePayInfoDo(restRequest.getBody().getBalancePayRequest());
            //插入资产表和余额信息表,返回传给mq的资产信息
            assetsSplitmq = assetsSplitService.insertAssetsSplitAndBalancePayRequestInfo(assetsSplit, balancePayInfos);

            log.info("===============<处理中心>-业务编号:{}，放款处理信息插入资产表成功===============", assetsSplit.getApplSeq());
            log.info("===============<处理中心>-业务编号:{},资产表放款状态{}===============", assetsSplit.getApplSeq(), assetsSplitmq.getLoanStatus());
            //设置返回体
            lendingResponse.setSerNo(serNo);
            lendingResponse.setPropertyType(PL0506Enum.PL0506_1_10.getCode());
            restResponse.setOK();
            restResponse.setBody(lendingResponse);
            ThreadPool.submit(() -> {
                loanApplyForHandleService.applyForHandle(restRequest, assetsSplitmq);
            });
            return restResponse;
        } catch (Exception e) {
            //判断业务编码是否存在,
            if (StringUtils.isBlank(assetsSplitService.isExistApplSeq(restRequest.getBody().getApplSeq()))) {
                log.error("插入资产表异常，请求信息{}", JsonUtils.writeObjectToJson(restRequest));
                throw new SystemException(CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getCode(), "插入资产表异常,请求信息【" + JsonUtils.writeObjectToJson(restRequest) + "】", serNo, null);
            } else {
                //设置返回体
                log.error("业务编号重复:{}", restRequest.getBody().getApplSeq());
                lendingResponse.setSerNo(serNo);
                lendingResponse.setPropertyType(PL0506Enum.PL0506_1_10.getCode());
                restResponse.setOK();
                restResponse.setBody(lendingResponse);
                return restResponse;
            }
        }
    }

    public String queryMerchCustInfo(LoanApplyForRequest loanApplyForRequest) {
        String crmNo = routeResultRecordService.selectCustIdByApplSeq(loanApplyForRequest.getApplSeq());
        if (StringUtils.isBlank(crmNo)) {
            crmNo = crmCustService.queryMerchCustInfo(loanApplyForRequest.getCertCode(), loanApplyForRequest.getCustName());
        }
        return crmNo;
    }
}
