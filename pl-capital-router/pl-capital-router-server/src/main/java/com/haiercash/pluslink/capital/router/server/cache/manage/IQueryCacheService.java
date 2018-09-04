package com.haiercash.pluslink.capital.router.server.cache.manage;

import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;

import java.util.Map;

/**
 * 从缓存中获取数据接口类(若不存在或查询异常则从数据库查询)
 * @author WDY
 * 2018-07-16
 */
public interface IQueryCacheService {

    /**合作机构数据**/
    Map<String,CooperationAgency> queryCooperationAgencyCache();

    /**查询合作项目**/
    Map<String,CooperationProject> queryCooperationProjectCache();

    /**查询还款方式**/
    Map<String,RepaymentInfo> queryRepaymentInfoCache();

    /**查询合作期限**/
    Map<String,CooperationPeriod> queryCooperationPeriodCache();

    /**银行信息数据**/
    Map<String,BankInfo> queryBankInfoCache();


    /**合作项目支持银行数据**/
    Map<String,ProjectBank> queryProjectBankCache();


    /**消金产品数据**/
    Map<String,CashProduct> queryCashProductCache();


    /**客户标签数据**/
    Map<String,CashCustSign> queryCashCustSignCache();


    /**入件渠道数据**/
    Map<String,InsertsChannel> queryInsertsChannelCache();

    /**需求资料数据**/
    Map<String,AgencyDemandInfo> queryAgencyDemandInfo();

    /**需求资料明细数据**/
    Map<String,AgencyDemandItem> queryAgencyDemandItem();
}
