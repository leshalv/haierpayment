package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.common.utils.StringUtils;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.rest.client.CrmCustClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author lzh
 * @Title: CrmCustService  Crm客户信息接口
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1814:37
 */
@Service
@Transactional(readOnly = true)
public class CrmCustService extends BaseService {

    @Autowired
    private CrmCustClient crmCustClient;

    /**
     * @param certNo   证件号码
     * @param custName 客户名称
     * @return 返回客户编号
     */
    public String queryMerchCustInfo(String certNo, String custName) {
        CrmCustResponse crmCustResponse = null;
        try {
            crmCustResponse = crmCustClient.queryMerchCustInfo(certNo);
        } catch (Exception e) {
            logger.error("<处理中心>-<处理中心>-通知支付网关放款-调用CRM客户信息接口通讯失败,certNo【" +certNo+ "】");
            return null;
        }
            return crmCustResponse.getBody().getCustNo();
    }

}
