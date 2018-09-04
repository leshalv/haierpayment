package com.haiercash.pluslink.capital.router.server.cache.manage.impl;

import com.alibaba.fastjson.TypeReference;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import com.haiercash.pluslink.capital.router.server.cache.manage.IQueryCacheService;
import com.haiercash.pluslink.capital.router.server.service.IPublicDataService;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从缓存中获取数据接口实现类(若不存在或查询异常则从数据库查询)
 * @author WDY
 * 2018-07-16
 */
@Service("queryCacheService")
public class QueryCacheServiceImpl extends BaseService implements IQueryCacheService {

    @Autowired
    private IRedisCommonService redisCommonService;
    @Autowired
    private IPublicDataService publicDataService;
    @Value("${redis.expire.outTime:}")
    private String outTime;
    @Value("${redis.expire.timeType:}")
    private String timeType;
    @Value("${redis.cache.config.cacheArray:}")
    private String[] cacheArray;
    @Value("${redis.cache.flag:}")
    private String flag;

    /**查询合作项目**/
    public Map<String,CooperationProject> queryCooperationProjectCache(){
        return queryData(RedisCacheEnum.cooperation_project,"合作项目",new TypeReference<Map<String,CooperationProject>>(){});
    }

    /**查询还款方式**/
    public Map<String,RepaymentInfo> queryRepaymentInfoCache(){
        return queryData(RedisCacheEnum.repayment_info,"还款方式",new TypeReference<Map<String,RepaymentInfo>>(){});
    }

    /**查询合作期限**/
    public Map<String,CooperationPeriod> queryCooperationPeriodCache(){
        return queryData(RedisCacheEnum.cooperation_period,"合作期限",new TypeReference<Map<String,CooperationPeriod>>(){});
    }

    /**合作机构数据**/
    public Map<String,CooperationAgency> queryCooperationAgencyCache(){
        return queryData(RedisCacheEnum.cooperation_agency,"合作机构",new TypeReference<Map<String,CooperationAgency>>(){});
    }

    /**银行信息数据**/
    public Map<String,BankInfo> queryBankInfoCache(){
        return queryData(RedisCacheEnum.bank_info,"银行数据",new TypeReference<Map<String,BankInfo>>(){});
    }


    /**合作项目支持银行数据**/
   public Map<String,ProjectBank> queryProjectBankCache(){
       return queryData(RedisCacheEnum.project_bank,"合作项目支持银行",new TypeReference<Map<String,ProjectBank>>(){});
   }


    /**消金产品数据**/
    public Map<String,CashProduct> queryCashProductCache(){
        return queryData(RedisCacheEnum.cash_product,"消金产品数据",new TypeReference<Map<String,CashProduct>>(){});
    }


    /**客户标签数据**/
    public Map<String,CashCustSign> queryCashCustSignCache(){
        return queryData(RedisCacheEnum.cash_cust_sign,"客户标签数据",new TypeReference<Map<String,CashCustSign>>(){});
    }


    /**入件渠道数据**/
    public Map<String,InsertsChannel> queryInsertsChannelCache(){
        return queryData(RedisCacheEnum.inserts_channel,"入件渠道数据",new TypeReference<Map<String,InsertsChannel>>(){});
    }

    /**需求资料数据**/
    public Map<String,AgencyDemandInfo> queryAgencyDemandInfo(){
        return queryData(RedisCacheEnum.agency_demand_info,"需求资料数据",new TypeReference<Map<String,AgencyDemandInfo>>(){});
    }

    /**需求资料明细数据**/
    public Map<String,AgencyDemandItem> queryAgencyDemandItem(){
        return queryData(RedisCacheEnum.agency_demand_item,"需求资料明细数据",new TypeReference<Map<String,AgencyDemandItem>>(){});
    }

    private <T> Map<String,T> queryData(RedisCacheEnum redisCacheEnum,String message,TypeReference<Map<String,T>> type){
        Map<String,T> map;
        try{
            try{
                if("true".equals(flag)){
                    map = redisCommonService.selectRedis(redisCacheEnum.getCode(),type);
                }else{
                    map = queryDataByDB(redisCacheEnum,false);
                }
                if(null == map){
                    throw new Exception(message + "信息获取失败");
                }
            }catch(Exception e){
                logger.error("redis查询异常," + message + "信息改从数据库查询...");
                map = queryDataByDB(redisCacheEnum,true);
            }
        }catch(Exception ex){
            map = new HashMap();
        }
        return map;
    }

    private <T> Map<String,T> queryDataByDB(RedisCacheEnum redisCacheEnum,boolean redisFlag){
        Object obj = publicDataService.queryPublicDataForCache(redisCacheEnum);
        Map<String,T> map = new HashedMap();
        if(null == obj){
            return map;
        }
        List<T> list = (List<T>)obj;
        for(T op : list){
            String id = ParamUtil.getIdByData(op);
            if(null == id){
                continue;
            }
            map.put(id,op);
        }
        if(redisFlag){
            try{
                intoRedis(redisCacheEnum.getCode(),map);
            }catch(Exception e){
                logger.error("添加redis异常,待下次插入......");
            }
        }
        return map;
    }

    private void intoRedis(String key,Object obj){
        redisCommonService.addRedis(key,obj,Integer.parseInt(outTime),TimeEnum.getEnum(timeType));
    }
}
