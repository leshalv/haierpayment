package com.haiercash.pluslink.capital.router.server.service.impl;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.mybatis.dao.BaseCommonDao;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.common.utils.JsonConverter;
import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.entity.AlreadyPositionIn;
import com.haiercash.pluslink.capital.entity.AlreadyPositionOut;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.enums.RedisStatusEnum;
import com.haiercash.pluslink.capital.router.server.dao.*;
import com.haiercash.pluslink.capital.router.server.service.IPublicDataService;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 路由中心公共数据查询接口实现类
 * @author WDY
 * @date 2018-07-04
 * @rmk
 * (1)合作机构表缓存数据加载
 * (2)合作机构需求资料表缓存数据加载
 * (3)合作机构需求资料明细表缓存数据加载
 * (4)合作项目表缓存数据加载
 * (5)银行信息表缓存数据加载
 * (6)合作项目支持银行表缓存数据加载
 * (7)合作项目期限表缓存数据加载
 * (8)还款方式表缓存数据加载
 * (9)消金产品表缓存数据加载
 * (10)入件渠道表缓存数据加载
 * (11)客户标签表缓存数据加载
 * (12)调度规则表缓存数据加载
 * (13)规则阶段任务表缓存数据加载
 * (14)数据字典主表缓存数据加载
 * (15)数据字典子表缓存数据加载
 * (16)路由规则明细表缓存数据加载(包含路由规则数据)
 */
@Service("publicDataService")
public class PublicDataServiceImpl extends BaseService implements IPublicDataService {

    @Autowired
    private RouteRuleItemDao routeRuleItemDao;//路由规则明细表数据
    @Autowired
    private CooperationAgencyDao cooperationAgencyDao;//合作机构表数据库接口类
    @Autowired
    private AgencyDemandInfoDao agencyDemandInfoDao;//合作机构需求资料表数据库接口类
    @Autowired
    private AgencyDemandItemDao agencyDemandItemDao;//合作机构需求资料明细表数据库接口类
    @Autowired
    private CooperationProjectDao cooperationProjectDao;//合作项目表数据库接口类
    @Autowired
    private BankInfoDao bankInfoDao;//银行信息表数据库接口类
    @Autowired
    private ProjectBankDao projectBankDao;//合作项目支持银行表数据库接口类
    @Autowired
    private CooperationPeriodDao cooperationPeriodDao;//合作项目期限表数据库接口类
    @Autowired
    private RepaymentInfoDao repaymentInfoDao;//还款方式表数据库接口类
    @Autowired
    private CashProductDao cashProductDao;//消金产品表数据库接口类
    @Autowired
    private InsertsChannelDao insertsChannelDao;//入件渠道表数据库接口类
    @Autowired
    private CashCustSignDao cashCustSignDao;//客户标签表数据库接口类
    @Autowired
    private ScheduleRuleDao scheduleRuleDao;//调度规则表数据库接口类
    @Autowired
    private ScheduleRulePhaseDao scheduleRulePhaseDao;//规则阶段任务表数据库接口类
    @Autowired
    private DictionaryDao dictionaryDao;//数据字典主表数据库接口类
    @Autowired
    private DictionarySubDao dictionarySubDao;//数据字典子表数据库接口类
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private BaseCommonDao baseCommonDao;
    @Autowired
    private RouteResultRecordDao routeResultRecordDao;

    /**为缓存数据提供公共查询**/
    public Object queryPublicDataForCache(RedisCacheEnum typeEnum){
        Object obj = null;
        try{
            //1,路由规则明细缓存数据加载
            if(RedisCacheEnum.route_rule_item.getCode().equals(typeEnum.getCode())){
                //规则编号(明细),业务类型(明细),规则内容(明细),匹配规则(规则),资金渠道(规则),渠道产品(规则),优先级(规则)
                RouteRuleItem routeRuleItem = new RouteRuleItem();
                List<RouteRuleItem> list = routeRuleItemDao.selectRouteRuleAndItemList(routeRuleItem);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //2，合作机构表缓存数据加载
            if(RedisCacheEnum.cooperation_agency.getCode().equals(typeEnum.getCode())){
                CooperationAgency cooperationAgency = new CooperationAgency();
                List<CooperationAgency> list = cooperationAgencyDao.select(cooperationAgency);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //3，合作机构需求资料表缓存数据加载
            if(RedisCacheEnum.agency_demand_info.getCode().equals(typeEnum.getCode())){
                AgencyDemandInfo agencyDemandInfo = new AgencyDemandInfo();
                List<AgencyDemandInfo> list = agencyDemandInfoDao.select(agencyDemandInfo);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //4，合作机构需求资料明细表缓存数据加载
            if(RedisCacheEnum.agency_demand_item.getCode().equals(typeEnum.getCode())){
                AgencyDemandItem agencyDemandItem = new AgencyDemandItem();
                List<AgencyDemandItem> list = agencyDemandItemDao.select(agencyDemandItem);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //5，合作项目表缓存数据加载
            if(RedisCacheEnum.cooperation_project.getCode().equals(typeEnum.getCode())){
                CooperationProject cooperationProject = new CooperationProject();
                List<CooperationProject> list = cooperationProjectDao.select(cooperationProject);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //6，银行信息表缓存数据加载
            if(RedisCacheEnum.bank_info.getCode().equals(typeEnum.getCode())){
                BankInfo bankInfo = new BankInfo();
                List<BankInfo> list = bankInfoDao.select(bankInfo);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //7，合作项目支持银行表缓存数据加载
            if(RedisCacheEnum.project_bank.getCode().equals(typeEnum.getCode())){
                ProjectBank projectBank = new ProjectBank();
                List<ProjectBank> list = projectBankDao.select(projectBank);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //8，合作项目期限表缓存数据加载
            if(RedisCacheEnum.cooperation_period.getCode().equals(typeEnum.getCode())){
                CooperationPeriod cooperationPeriod = new CooperationPeriod();
                List<CooperationPeriod> list = cooperationPeriodDao.select(cooperationPeriod);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //9，还款方式表缓存数据加载
            if(RedisCacheEnum.repayment_info.getCode().equals(typeEnum.getCode())){
                RepaymentInfo repaymentInfo = new RepaymentInfo();
                List<RepaymentInfo> list = repaymentInfoDao.select(repaymentInfo);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //10，消金产品表缓存数据加载
            if(RedisCacheEnum.cash_product.getCode().equals(typeEnum.getCode())){
                CashProduct cashProduct = new CashProduct();
                List<CashProduct> list = cashProductDao.select(cashProduct);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //11，入件渠道表缓存数据加载
            if(RedisCacheEnum.inserts_channel.getCode().equals(typeEnum.getCode())){
                InsertsChannel insertsChannel = new InsertsChannel();
                List<InsertsChannel> list = insertsChannelDao.select(insertsChannel);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //12，客户标签表缓存数据加载
            if(RedisCacheEnum.cash_cust_sign.getCode().equals(typeEnum.getCode())){
                CashCustSign cashCustSign = new CashCustSign();
                List<CashCustSign> list = cashCustSignDao.select(cashCustSign);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //13，调度规则表缓存数据加载
            if(RedisCacheEnum.schedule_rule.getCode().equals(typeEnum.getCode())){
                ScheduleRule scheduleRule = new ScheduleRule();
                List<ScheduleRule> list = scheduleRuleDao.select(scheduleRule);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //14，规则阶段任务表缓存数据加载
            if(RedisCacheEnum.schedule_rule_phase.getCode().equals(typeEnum.getCode())){
                ScheduleRulePhase scheduleRulePhase = new ScheduleRulePhase();
                List<ScheduleRulePhase> list = scheduleRulePhaseDao.select(scheduleRulePhase);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //15，数据字典主表缓存数据加载
            if(RedisCacheEnum.dictionary.getCode().equals(typeEnum.getCode())){
                Dictionary dictionary = new Dictionary();
                List<Dictionary> list = dictionaryDao.select(dictionary);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

            //16，数据字典子表缓存数据加载
            if(RedisCacheEnum.dictionary_sub.getCode().equals(typeEnum.getCode())){
                DictionarySub dictionarySub = new DictionarySub();
                List<DictionarySub> list = dictionarySubDao.select(dictionarySub);
                if(null == list){
                    list = new ArrayList();
                }
                obj = list;
            }

        }catch(Exception e) {
            logger.error("查询" + typeEnum.getDesc() + "到redis数据异常" + e.getMessage(),e);
            throw new PlCapitalException(RedisStatusEnum.router_query_data_error.getCode(),"查询" + typeEnum.getDesc() + "到redis数据异常");
        }

        return obj;
    }

    /**查询序列**/
    public String querySequence(String seqName){
        String seq;
        try{
            seq = commonDao.selectSeqBySeqName(seqName);
        }catch(Exception e){
            logger.error("查询序列" + seqName + "异常,Message = " + e.getMessage(),e);
            throw new PlCapitalException("查询序列异常");
        }
        return seq;
    }

    /**插入路由结果表**/
    public void addRouteResultRecord(RouteResultRecord routeResultRecord){
        try{
            String[] ipAndHostName = ParamUtil.getIpAndHostName();
            routeResultRecord.setExcuteIP(ipAndHostName[0]);//
            routeResultRecord.setExcuteHostName(ipAndHostName[1]);
            routeResultRecordDao.insert(routeResultRecord);
        }catch(Exception e){
            logger.error("添加路由结果异常RouteResultRecord = " + JsonConverter.object2Json(routeResultRecord) + ",Message = " + e.getMessage(),e);
            throw new PlCapitalException("添加路由结果异常");
        }
    }

    /**已使用限额**/
    public PositionSplitIn getAlreadyLoan(String projectId){
        PositionSplitIn positionSplitIn = new PositionSplitIn();
        Date date = new Date();
        AlreadyPositionIn alreadyPositionIn= new AlreadyPositionIn(projectId,date);
        try{
            AlreadyPositionOut out = baseCommonDao.selecAlreadyPosition(alreadyPositionIn);
            if(null == out){
                return null;
            }
            positionSplitIn.setAlreadyLoanAmount(out.getAlreadyLoanAmount());
            positionSplitIn.setAlreadyLoanLimitMonth(out.getAlreadyLoanLimitMonth());
            positionSplitIn.setAlreadyLoanLimitDay(out.getAlreadyLoanLimitDay());
        }catch(Exception e){
            logger.error("查询合作项目" + projectId + "已放款头寸异常,Message = " + e.getMessage(),e);
            throw new PlCapitalException("查询合作项目已放款头寸异常");
        }
        return positionSplitIn;
    }
}
