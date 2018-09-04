package com.haiercash.pluslink.capital.router.server.service.impl;

import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.dictionary.PL0404Enum;
import com.haiercash.pluslink.capital.router.server.cache.manage.IQueryCacheService;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchData;
import com.haiercash.pluslink.capital.router.server.service.IRouterMatchDataService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取路由匹配参数实现类
 * @author WDY
 * @date 2018-07-18
 * @rmk
 */
@Service("routerMatchDataService")
public class RouterMatchDataServiceImpl extends BaseService implements IRouterMatchDataService {

    @Autowired
    private IQueryCacheService queryCacheService;

    /**获取路由匹配参数**/
    public Map<String,Map<String,RouterMatchData>> getRouterMatchData(String serNo,String applSeq){

        Map<String,Map<String,RouterMatchData>> map = new HashMap();

        //从Redis缓存中取出数据
        Map<String,CooperationAgency> agencyMap = queryCacheService.queryCooperationAgencyCache();//合作机构
        Map<String,CooperationProject> projectMap = queryCacheService.queryCooperationProjectCache();//合作项目
        String message = "";
        for(String key : projectMap.keySet()){
            message = message + projectMap.get(key).getId() + ",";
        }
        logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +",共获取到：" + projectMap.size() + "条合作项目：" + message);
        Map<String,BankInfo> bankInfoMap = queryCacheService.queryBankInfoCache();//银行信息数据
        Map<String,ProjectBank> projectBankMap = queryCacheService.queryProjectBankCache();//合作项目支持银行数据
        Map<String,CashProduct> productMap = queryCacheService.queryCashProductCache();//消金产品
        Map<String,InsertsChannel> channelMap = queryCacheService.queryInsertsChannelCache();//消金渠道
        Map<String,CashCustSign> custSignMap = queryCacheService.queryCashCustSignCache();//客户标签
        Map<String,CooperationPeriod> periodMap = queryCacheService.queryCooperationPeriodCache();//合作项目支持期限
        Map<String,AgencyDemandInfo> agencyDemandInfo = queryCacheService.queryAgencyDemandInfo();//需求资料数据
        Map<String,AgencyDemandItem> demandItemMap = queryCacheService.queryAgencyDemandItem();//需求资料明细数据

        //获取合作机构支持银行Map<合作机构Id,Map<银行英文编码,银行名称>>
        Map<String,Map<String,String>> projectSupportBankMap = getProjectBank(bankInfoMap,projectBankMap);
        //合作项目期限Map<合作项目,Map<期限类型,Map<Long,CooperationPeriod>>>
        Map<String,Map<String,Map<Long,CooperationPeriod>>> periodChangeMap = getPeriodChangeMap(periodMap);
        //合作机构需求资料Map<合作机构ID,Map<资料类型,Map<明细资料类型,合作资料明细>>>
        Map<String,Map<String,Map<String,AgencyDemandItem>>> agencyDemandItemMap = getAgencyDemandItem(agencyDemandInfo,demandItemMap);

        //Map<P|消金产品,Map<项目编号,RouterMatchData>>
        Map<String,Map<String,RouterMatchData>> productResultMap = getCashProductMap(agencyMap,projectMap,projectSupportBankMap,productMap,periodChangeMap,agencyDemandItemMap,serNo,applSeq);
        //Map<P|消金渠道,Map<项目编号,RouterMatchData>>
        Map<String,Map<String,RouterMatchData>> channelResultMap = getCashChannelMap(agencyMap,projectMap,projectSupportBankMap,channelMap,periodChangeMap,agencyDemandItemMap,serNo,applSeq);
        //Map<P|客户标签,Map<项目编号,RouterMatchData>>
        Map<String,Map<String,RouterMatchData>> custSignResultMap = getCashCustSignMap(agencyMap,projectMap,projectSupportBankMap,custSignMap,periodChangeMap,agencyDemandItemMap,serNo,applSeq);
        map.putAll(productResultMap);
        map.putAll(channelResultMap);
        map.putAll(custSignResultMap);
        return map;
    }

    private Map<String,Map<String,RouterMatchData>> getCashProductMap(
            Map<String,CooperationAgency> agencyMap,
            Map<String,CooperationProject> projectMap,
            Map<String,Map<String,String>> projectSupportBankMap,
            Map<String,CashProduct> productMap,
            Map<String,Map<String,Map<Long,CooperationPeriod>>> periodMap,
            Map<String,Map<String,Map<String,AgencyDemandItem>>>  agencyDemandItemMap,
            String serNo,
            String applSeq){
        Map<String,Map<String,RouterMatchData>> productResultMap = new HashedMap();
        for(String key : productMap.keySet()){
            CashProduct product = productMap.get(key);
            StringBuilder productKey = new StringBuilder();
            productKey.append(PL0404Enum.PL0404_1_P.getCode()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(product.getCashProductNo());
            Map<String,RouterMatchData> map = productResultMap.get(productKey.toString());
            if(null == map){
                map = new HashMap();
            }
            RouterMatchData routerMatchData = getRouterMatchData(agencyMap,projectMap,projectSupportBankMap,periodMap,agencyDemandItemMap,product.getProjectId(),serNo,applSeq);
            //记录数据错误则不作为筛选条件
            if(null == routerMatchData){
                continue;
            }
            routerMatchData.setParamType(PL0404Enum.PL0404_1_P.getCode());
            routerMatchData.setParamValue(product.getCashProductNo());
            map.put(routerMatchData.getProject().getId(),routerMatchData);
            productResultMap.put(productKey.toString(),map);
        }
        return productResultMap;
    }

    private Map<String,Map<String,RouterMatchData>> getCashChannelMap(
            Map<String,CooperationAgency> agencyMap,
            Map<String,CooperationProject> projectMap,
            Map<String,Map<String,String>> projectSupportBankMap,
            Map<String,InsertsChannel> channelMap,
            Map<String,Map<String,Map<Long,CooperationPeriod>>> periodMap,
            Map<String,Map<String,Map<String,AgencyDemandItem>>>  agencyDemandItemMap,
            String serNo,
            String applSeq){
        Map<String,Map<String,RouterMatchData>> channelResultMap = new HashedMap();
        for(String key : channelMap.keySet()){
            InsertsChannel channel = channelMap.get(key);
            StringBuilder channelKey = new StringBuilder();
            channelKey.append(PL0404Enum.PL0404_2_C.getCode()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(channel.getInsertChannelType());
            Map<String,RouterMatchData> map = channelResultMap.get(channelKey.toString());
            if(null == map){
                map = new HashMap();
            }
            RouterMatchData routerMatchData = getRouterMatchData(agencyMap,projectMap,projectSupportBankMap,periodMap,agencyDemandItemMap,channel.getProjectId(),serNo,applSeq);
            //记录数据错误则不作为筛选条件
            if(null == routerMatchData){
                continue;
            }
            routerMatchData.setParamType(PL0404Enum.PL0404_2_C.getCode());
            routerMatchData.setParamValue(channel.getInsertChannelType());
            map.put(routerMatchData.getProject().getId(),routerMatchData);
            channelResultMap.put(channelKey.toString(),map);
        }
        return channelResultMap;
    }

    private Map<String,Map<String,RouterMatchData>> getCashCustSignMap(
            Map<String,CooperationAgency> agencyMap,
            Map<String,CooperationProject> projectMap,
            Map<String,Map<String,String>> projectSupportBankMap,
            Map<String,CashCustSign> custSignMap,
            Map<String,Map<String,Map<Long,CooperationPeriod>>> periodMap,
            Map<String,Map<String,Map<String,AgencyDemandItem>>>  agencyDemandItemMap,
            String serNo,
            String applSeq){
        Map<String,Map<String,RouterMatchData>> custSignResultMap = new HashedMap();
        for(String key : custSignMap.keySet()){
            CashCustSign custSign = custSignMap.get(key);
            StringBuilder custSignKey = new StringBuilder();
            custSignKey.append(PL0404Enum.PL0404_3_S.getCode()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(custSign.getCashCustSign());
            Map<String,RouterMatchData> map = custSignResultMap.get(custSignKey.toString());
            if(null == map){
                map = new HashMap();
            }
            RouterMatchData routerMatchData = getRouterMatchData(agencyMap,projectMap,projectSupportBankMap,periodMap,agencyDemandItemMap,custSign.getProjectId(),serNo,applSeq);
            //记录数据错误则不作为筛选条件
            if(null == routerMatchData){
                continue;
            }
            routerMatchData.setParamType(PL0404Enum.PL0404_3_S.getCode());
            routerMatchData.setParamValue(custSign.getCashCustSign());
            map.put(routerMatchData.getProject().getId(),routerMatchData);
            custSignResultMap.put(custSignKey.toString(),map);
        }
        return custSignResultMap;
    }

    private RouterMatchData getRouterMatchData(
            Map<String,CooperationAgency> agencyMap,
            Map<String,CooperationProject> projectMap,
            Map<String,Map<String,String>> projectSupportBankMap,
            Map<String,Map<String,Map<Long,CooperationPeriod>>> periodMap,
            Map<String,Map<String,Map<String,AgencyDemandItem>>>  agencyDemandItemMap,
            String projectId,
            String serNo,
            String applSeq){
        RouterMatchData data = new RouterMatchData();
        CooperationProject project = projectMap.get(projectId);//合作项目
        CooperationAgency agency = null;
        Map<String,String> supportBank =null;
        Map<String,Map<String,AgencyDemandItem>>  supportAgencyDemandItem = null;
        Map<String,Map<Long,CooperationPeriod>> supportPriodMap = null;
        if(null != project){
            agency = agencyMap.get(project.getAgencyId());//合作机构
            supportBank = projectSupportBankMap.get(project.getId());//合作项目支持银行Map<银行英文编码,银行名称>
            supportPriodMap = periodMap.get(project.getId());
            supportAgencyDemandItem = agencyDemandItemMap.get(project.getAgencyId());
            if(null == supportAgencyDemandItem){
                supportAgencyDemandItem = new HashMap();
            }
        }
        if(null == project || null == agency || null == supportBank || null == supportPriodMap){
            logger.error("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +",合作机构,合作项目或合作项目支持银行卡或合作项目支持期限维护异常(包含状态为未启用),不列入路由筛选条件...");
            return null;
        }
        data.setAgency(agency);
        data.setProject(project);
        data.setSupportBank(supportBank);
        data.setPeriodMap(supportPriodMap);
        data.setAgencyDemandItemMap(supportAgencyDemandItem);
        return data;
    }

    /**获取合作机构支持银行Map<合作机构Id,Map<银行英文编码,银行名称>>**/
    private Map<String,Map<String,String>> getProjectBank(Map<String,BankInfo> bankInfoMap,Map<String,ProjectBank> projectBankMap){
        Map<String,Map<String,String>> map = new HashedMap();
        for(String key : projectBankMap.keySet()){
            ProjectBank projectBank = projectBankMap.get(key);
            Map<String,String> projectSupportBankMap = map.get(projectBank.getProjectId());
            if(null == projectSupportBankMap){
                projectSupportBankMap = new HashMap();
            }
            BankInfo bankInfo = bankInfoMap.get(projectBank.getBankId());
            projectSupportBankMap.put(bankInfo.getBankNoNum(),bankInfo.getBankName());
            map.put(projectBank.getProjectId(),projectSupportBankMap);
        }
        return map;
    }

    /**合作项目期限Map<合作项目,Map<期限类型,Map<Long,CooperationPeriod>>>**/
    private Map<String,Map<String,Map<Long,CooperationPeriod>>> getPeriodChangeMap(Map<String,CooperationPeriod> periodMap){
        Map<String,Map<String,Map<Long,CooperationPeriod>>> projectPeriodChangeMap = new HashedMap();
        for(String key : periodMap.keySet()){
            CooperationPeriod bean = periodMap.get(key);
            Map<String,Map<Long,CooperationPeriod>> periodChangeMap =
                    projectPeriodChangeMap.get(bean.getProjectId());
            if(null == periodChangeMap){
                periodChangeMap = new HashMap();
            }
            Map<Long,CooperationPeriod> map = periodChangeMap.
                    get(bean.getCooperationPeriodType());//根据期限类型
            if(null == map){
                map = new HashMap();
            }
            map.put(bean.getCooperationPeriodValue(),bean);
            periodChangeMap.put(bean.getCooperationPeriodType(),map);
            projectPeriodChangeMap.put(bean.getProjectId(),periodChangeMap);
        }
        return projectPeriodChangeMap;
    }

    /**合作机构需求资料Map<合作机构ID,Map<资料类型,Map<明细资料类型,合作资料明细>>>**/
    private Map<String,Map<String,Map<String,AgencyDemandItem>>> getAgencyDemandItem(Map<String,AgencyDemandInfo> agencyDemandInfo,Map<String,AgencyDemandItem> demandItemMap){
        Map<String,Map<String,Map<String,AgencyDemandItem>>> angencySupportDemandItemMap = new HashMap();
        for(String itemId : demandItemMap.keySet()){
            AgencyDemandItem item = demandItemMap.get(itemId);
            AgencyDemandInfo info = agencyDemandInfo.get(item.getDemandId());
            if(null == info){
                continue;
            }
            Map<String,Map<String,AgencyDemandItem>> agencyDemandItemMap = angencySupportDemandItemMap.get(info.getAgencyId());
            if(null == agencyDemandItemMap){
                agencyDemandItemMap = new HashMap();
            }
            Map<String,AgencyDemandItem> map = agencyDemandItemMap.get(info.getDemandType());
            if(null == map){
                map = new HashMap();
            }
            map.put(item.getMaterialType(),item);
            agencyDemandItemMap.put(info.getDemandType(),map);
            angencySupportDemandItemMap.put(info.getAgencyId(),agencyDemandItemMap);
        }
        return angencySupportDemandItemMap;
    }
}

