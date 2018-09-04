package com.haiercash.pluslink.capital.router.server.biz.impl;

import cn.jbinfo.api.exception.ApiException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.data.CooperationPeriod;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.data.RepaymentInfo;
import com.haiercash.pluslink.capital.router.server.api.controller.enums.ApiReturnCodeEnums;
import com.haiercash.pluslink.capital.router.server.biz.IApiQueryBiz;
import com.haiercash.pluslink.capital.router.server.cache.manage.IQueryCacheService;
import com.haiercash.pluslink.capital.router.server.entity.GeneralInfoMatch;
import com.haiercash.pluslink.capital.router.server.entity.GeneralInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资金路由规则主逻辑接口实现类
 * @author WDY
 * 2018-06-07
 */
@Component("apiQueryBiz")
public class ApiQueryBizImpl extends BaseService implements IApiQueryBiz{

    @Autowired
    private IQueryCacheService queryCacheService;

    /**资金方综合查询**/
    public GeneralInfoResult querGeneralInfo(GeneralInfoMatch generalInfoMatch,String serNo){
        GeneralInfoResult resulet = new GeneralInfoResult();
        List<RepaymentInfo> repayList = new ArrayList();
        List<CooperationPeriod> periodList = new ArrayList();
        CooperationProject project = null;
        //查询合作项目信息
        Map<String,CooperationProject> projectMap = queryCacheService.queryCooperationProjectCache();
        for(String projectKey : projectMap.keySet()){
            CooperationProject tmpCp = projectMap.get(projectKey);
            if(tmpCp.getId().equals(generalInfoMatch.getProjectId())){
                project = tmpCp;
                //查询还款方式
                Map<String,RepaymentInfo> repaymentMap = queryCacheService.queryRepaymentInfoCache();
                for(String repayKey : repaymentMap.keySet()){
                    RepaymentInfo repayInfo = repaymentMap.get(repayKey);
                    if(repayInfo.getProjectId().equals(tmpCp.getId())){
                        repayList.add(repayInfo);
                    }
                }
                //查询还款期限
                Map<String,CooperationPeriod> periodMap = queryCacheService.queryCooperationPeriodCache();
                for(String periodKey : periodMap.keySet()){
                    CooperationPeriod period = periodMap.get(periodKey);
                    if(period.getProjectId().equals(tmpCp.getId())){
                        periodList.add(period);
                    }
                }
            }
        }
        if(null == project){//查询结果不存在
            logger.info("GeneralInfo：serNo(" + serNo + ")" + "合作机构主键ID:" + generalInfoMatch.getAgencyId() + ",合作项目主键ID:" + generalInfoMatch.getProjectId() + ",查询结果不存在...");
            throw new ApiException(ApiReturnCodeEnums.error_query_data.getCode(),"");
        }
        resulet.setProject(project);
        resulet.setRepayList(repayList);
        resulet.setPeriodList(periodList);
        return resulet;
    }
}
