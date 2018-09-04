package com.haiercash.pluslink.capital.router.server.biz.impl;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.common.utils.JsonConverter;
import com.haiercash.pluslink.capital.common.utils.PositionSplitUtils;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.AgencyDemandItem;
import com.haiercash.pluslink.capital.data.CooperationPeriod;
import com.haiercash.pluslink.capital.data.RouteResultRecord;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.entity.PositionSplitOut;
import com.haiercash.pluslink.capital.enums.*;
import com.haiercash.pluslink.capital.enums.dictionary.*;
import com.haiercash.pluslink.capital.router.server.api.controller.enums.ApiReturnCodeEnums;
import com.haiercash.pluslink.capital.router.server.biz.IFundRouteRuleBiz;
import com.haiercash.pluslink.capital.router.server.component.ICommonComponent;
import com.haiercash.pluslink.capital.router.server.component.IRouteRuleMatchComponent;
import com.haiercash.pluslink.capital.router.server.entity.LastLoanIn;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchData;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchIn;
import com.haiercash.pluslink.capital.router.server.entity.RouterResult;
import com.haiercash.pluslink.capital.router.server.rest.controller.ILastLoanController;
import com.haiercash.pluslink.capital.router.server.service.IPublicDataService;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.SysexMessage;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 资金路由规则主逻实现类
 * @author WDY
 * 2018-06-07
 */
@Component("fundRouteRuleBiz")
@Transactional
public class FundRouteRuleBizImpl extends BaseService implements IFundRouteRuleBiz {

	@Autowired
	IRouteRuleMatchComponent routeRuleMatchComponent;//路由匹配接口
	@Autowired
	ICommonComponent routerCommonComponent;
	@Autowired
	private IPublicDataService publicDataService;
	@Autowired
	private ILastLoanController lastLoanController;

	/**资产路由
	 * 1，匹配路由规则查找合适的合作机构和合作项目
	 * 2，合作项目生效时间判断
	 * 3，业务受理时间
	 * 4，用户贷款金额判断
	 * 5，用户首次用信距今天数(暂无法判断)
	 * 6，判断客户年龄是否在区间内(一期不实现)
	 * 7，判断客户判断客户额度是否在区间内(一期不实现)
	 * 8，判断客户性别(一期不实现)
	 * 9，渠道支持银行卡
	 * 10,合作项目是否支持期限
	 * 11, 判断资金方剩余额度是否充足
	 * 12，匹配优先级(先匹配合作机构,再匹配合作项目)
	 * 13，封装路由结果数据
	 **/
	public RouterResult dealFundRouteMatch(RouterMatchIn routerMatch){
		RouterResult routerResult;
		StringBuilder message = new StringBuilder();
		LastLoanIn lastLoanIn = new LastLoanIn();
		lastLoanIn.setIdNo(routerMatch.getIdNo());//客户身份证号
		lastLoanIn.setLoanTyp(routerMatch.getTypCde());//消金产品
		int dayAgo = 0;
		try{
			RouterMatchData routerMatchData;//最优先的路由数据
			//匹配路由规则查找合适的合作机构和合作项目
			Long matchTime = System.currentTimeMillis();
			Map<String,RouterMatchData> map = doRouterMatch(routerMatch);
			countTime(routerMatch.getSerNo(),routerMatch.getApplSeq(),matchTime,"根据产品渠道标签查找符合条件的合作项目");
			if(null == map || 0 >= map.size()){
				message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append("未找到符合条件的合作项目信息,执行消金放款");
			}
			Long lastLoanApiTime = System.currentTimeMillis();
			try{
				dayAgo = lastLoanController.checkLastLoanController(lastLoanIn,routerMatch.getSerNo(),routerMatch.getApplSeq());
			}catch(Exception e){
				dayAgo = 0;
				logger.error("Router：applSeq：" + routerMatch.getApplSeq() + "调用ACRM查询用户是否30天前贷款接口异常...,执行消金放款...");
				throw new PlCapitalException(ApiReturnCodeEnums.unsupport_caller.getCode(),"调用ACRM查询用户是否30天前贷款接口异常...");
			}
			countTime(routerMatch.getSerNo(),routerMatch.getApplSeq(),lastLoanApiTime,"用户是否30天前贷款查询接口");
			//其他业务校验
			Long checkMatchTime = System.currentTimeMillis();
			checkMatchSomeThing(map,routerMatch,dayAgo);
			countTime(routerMatch.getSerNo(),routerMatch.getApplSeq(),checkMatchTime,"其他业务校验");
			//判断资金方剩余额度是否充足 和 匹配优先级(先匹配合作机构,再匹配合作项目)
			Long checkPositionTime = System.currentTimeMillis();
			routerMatchData = checkPositionAndPriority(map,routerMatch);
			countTime(routerMatch.getSerNo(),routerMatch.getApplSeq(),checkPositionTime,"判断资金方剩余额度是否充足和匹配优先级");
			//封装路由结果数据
			Long routerResultTime = System.currentTimeMillis();
			routerResult = getRouterResult(routerMatchData,routerMatch,message);
			countTime(routerMatch.getSerNo(),routerMatch.getApplSeq(),routerResultTime,"封装路由结果数据");
		}catch(PlCapitalException ple){
			message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",路由失败,失败原因：").append(ple.getDefineMessage()).append("执行消金放款");
			routerResult = getRouterResult(null,routerMatch,message);
			logger.error("Router：applSeq：" + routerMatch.getApplSeq() + ",路由失败,执行消金放款...失败原因：" + ple.getMessage());
		}catch(Exception e){
			logger.error("Router：applSeq：" + routerMatch.getApplSeq() + ",路由异常,内部错误...失败原因：" + e.getMessage(),e);
			throw new PlCapitalException("内部错误");
		}
		RouteResultRecord routeResultRecord = routerResult.getRouteResultRecord();
		routeResultRecord.setCustLastLoanDay(dayAgo);
		publicDataService.addRouteResultRecord(routeResultRecord);
		logger.info("Router：applSeq：" + routerMatch.getApplSeq() + "匹配路由结果为：" + JsonConverter.object2Json(routerResult));
		//非联合放款时将合作机构和合作项目信息置空
		if(PL0505Enum.PL0505_2_NOT_UNION.getCode().equals(routerResult.getRouteResultRecord().getIsUniteLoan())){
			routerResult.getRouteResultRecord().setAgencyId(null);//合作机构表主键ID
			routerResult.setAgencyName(null);//合作机构名称
			routerResult.getRouteResultRecord().setProjectId(null);//合作项目主键ID
			routerResult.setProjectName(null);//合作项目名称
		}
		return routerResult;
	}

	private Map<String,RouterMatchData> doRouterMatch(RouterMatchIn routerMatch){
		//匹配路由规则查找合适的渠道和产品
		Map<String,RouterMatchData> accessMap = routeRuleMatchComponent.dealRouteRuleMatch(
				routerMatch.getTypCde(),
				routerMatch.getChannelNo(),
				routerMatch.getCustSign(),
				routerMatch.getSerNo(),
				routerMatch.getApplSeq());
		return accessMap;
	}

	private RouterResult getRouterResult(RouterMatchData routerMatchData,RouterMatchIn routerMatch,StringBuilder message){
		RouterResult routerResult = new RouterResult();
		RouteResultRecord routeResultRecord = new RouteResultRecord();
		routerResult.setRouteResultRecord(routeResultRecord);
		String rmk = message.toString();
		//判断资方金额是不是0，若为0且消金放款为放款金额则走消金放款
		boolean loanFLag = true;
		if(null != routerMatchData){
			loanFLag = ((routerMatchData.getPositionSplitOut().getAgencyLoanAmount()).
					compareTo(BigDecimal.ZERO) == 0) &&
					((routerMatchData.getPositionSplitOut().getCashLoanAmount()).
							compareTo(routerMatch.getOrigPrcp()) == 0);
		}
		if(null == routerMatchData || loanFLag){//消金放款
			routerResult.getRouteResultRecord().setAgencyId(CommonConstant.CASH_LOAN_AMOUNT);//合作机构表主键ID
			routerResult.setAgencyName(CommonConstant.CASH_LOAN_AMOUNT_NAME);//合作机构名称
			routerResult.getRouteResultRecord().setProjectId(CommonConstant.CASH_LOAN_AMOUNT);//合作项目主键ID
			routerResult.setProjectName(CommonConstant.CASH_LOAN_AMOUNT_NAME);//合作项目名称
			routerResult.getRouteResultRecord().setAgencyRatio(BigDecimal.ZERO);//合作机构资金占比
			routerResult.getRouteResultRecord().setCashLoanAmount(routerMatch.getOrigPrcp());//消金放款金额
			routerResult.getRouteResultRecord().setAgencyLoanAmount(BigDecimal.ZERO);//合作机构放款金额
			routerResult.getRouteResultRecord().setAngencyPlanLoanAmount(BigDecimal.ZERO);//合作机构计划放款金额
			routerResult.getRouteResultRecord().setAgencyAlreadyAmount(BigDecimal.ZERO);//合作机构已放款金额
			routerResult.getRouteResultRecord().setAgencyLeftAmount(BigDecimal.ZERO);//合作机构剩余放款金额
			routerResult.getRouteResultRecord().setIsCredit(PL0403Enum.PL0403_1_CASH.getCode());//是否需要查征信：1-消金映射,2-机构自查
			routerResult.getRouteResultRecord().setIsUniteLoan(PL0505Enum.PL0505_2_NOT_UNION.getCode());//非联合放款
			rmk = "路由成功,非联合放款:" + rmk;

		}else{//联合放款
			routerResult.getRouteResultRecord().setAgencyId(routerMatchData.getAgency().getId());//合作机构表主键ID
			routerResult.setAgencyName(routerMatchData.getAgency().getAgencyName());//合作机构名称
			routerResult.getRouteResultRecord().setProjectId(routerMatchData.getProject().getId());//合作项目表主键ID
			routerResult.setProjectName(routerMatchData.getProject().getProjectName());//合作项目名称
			routerResult.getRouteResultRecord().setAgencyRatio(routerMatchData.getProject().getAgencyRatio());//合作机构资金占比
			routerResult.getRouteResultRecord().setCashLoanAmount(routerMatchData.getPositionSplitOut().getCashLoanAmount());//消金放款金额
			routerResult.getRouteResultRecord().setAgencyLoanAmount(routerMatchData.getPositionSplitOut().getAgencyLoanAmount());//合作机构放款金额
			routerResult.getRouteResultRecord().setAngencyPlanLoanAmount(routerMatchData.getProject().getLoanAmount());//合作机构计划放款金额
			routerResult.getRouteResultRecord().setAgencyAlreadyAmount(routerMatchData.getPositionSplitIn().getAlreadyLoanAmount());//合作机构已放款金额
			routerResult.getRouteResultRecord().setIsUniteLoan(PL0505Enum.PL0505_1_UNION.getCode());//联合放款
			BigDecimal agncyLeftAmount = (
					routerMatchData.getPositionSplitIn().getLoanAmount()).
					subtract(
							routerMatchData.getPositionSplitIn().getAlreadyLoanAmount());
			routerResult.getRouteResultRecord().setAgencyLeftAmount(agncyLeftAmount);//合作机构剩余放款金额
			routerResult.getRouteResultRecord().setIsCredit(routerMatchData.getProject().getCreditWay());//是否需要查征信：1-消金映射,2-机构自查
			if(PL0403Enum.PL0403_2_ANGENCY.getCode().equals(routerMatchData.getProject().getCreditWay())){//需要查征信
				Map<String,Map<String,AgencyDemandItem>> agencyDemandMap = routerMatchData.getAgencyDemandItemMap();
				if(null != agencyDemandMap){
					Map<String,AgencyDemandItem> agencyDemandItemMap = agencyDemandMap.get(PL0301Enum.PL0301_3_PROTOCOL.getCode());
					if(null != agencyDemandItemMap){
						AgencyDemandItem agencyDemandItem = agencyDemandItemMap.get(PL0302Enum.PL0302_1_PROTOCOL_MODLE.getCode());
						if(null != agencyDemandItem){
							routerResult.getRouteResultRecord().setCreditModleUrl(agencyDemandItem.getMaterialValue());
						}
					}
				}
			}
			rmk = "路由成功,联合放款,成功路由出合作机构和合作项目:" + rmk;
		}
		String serialNo = routerCommonComponent.doGeneratorCommonSerialNo(SerialNoEnum.router_match_result,routerMatch.getSerNo());
		routerResult.getRouteResultRecord().setId(serialNo);//主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)
		routerResult.getRouteResultRecord().setApplSeq(routerMatch.getApplSeq());//业务编号:申请流水号,幂等唯一
		routerResult.getRouteResultRecord().setCustId(routerMatch.getCustId());//消金客户编号
		routerResult.getRouteResultRecord().setCustIdNo(routerMatch.getIdNo());//身份证号
		routerResult.getRouteResultRecord().setCustSign(routerMatch.getCustSign());//客户标签
		routerResult.getRouteResultRecord().setTypCde(routerMatch.getTypCde());//消金产品编号
		routerResult.getRouteResultRecord().setChannelNo(routerMatch.getChannelNo());//消金渠道编号
		routerResult.getRouteResultRecord().setPeriod(routerMatch.getPeriod());//消金产品期数
		routerResult.getRouteResultRecord().setBankNoNum(routerMatch.getRepayCardBankNo());//还款银行代码
		routerResult.getRouteResultRecord().setLoanAmount(routerMatch.getOrigPrcp());//放款金额
		routerResult.getRouteResultRecord().setRouteRuleId("");//路由规则ID:可空(路由中心筛选规则改变)
		routerResult.getRouteResultRecord().setCustLimit(routerMatch.getCustLimit());//客户额度
		routerResult.getRouteResultRecord().setCustAge(routerMatch.getCustAge());//客户年龄
		routerResult.getRouteResultRecord().setCustSex(routerMatch.getCustSex());//客户性别
		routerResult.getRouteResultRecord().setSerNo(routerMatch.getSerNo());//报文流水号
		routerResult.getRouteResultRecord().setSysFlag(routerMatch.getSysFlag());//系统标识
		routerResult.getRouteResultRecord().setCreateDate(new Date());//创建时间

		if(300 < rmk.length()){
			rmk = rmk.substring(0,300);
		}
		routerResult.getRouteResultRecord().setRmk(rmk);
		return routerResult;
	}

	private void checkMatchSomeThing(Map<String,RouterMatchData> map,RouterMatchIn routerMatch,int dayAgo){
		Map<String,RouterMatchData> entryMap = new HashMap();
		entryMap.putAll(map);
		Iterator<Map.Entry<String,RouterMatchData>> entries = entryMap.entrySet().iterator();
		while(entries.hasNext()){
			Map.Entry<String,RouterMatchData> entry = entries.next();
			String key = entry.getKey();
			RouterMatchData bean = entry.getValue();

			Date date = new Date();
			Long dateTimeNow = ParamUtil.changeDateTimeLong(date);
			logger.info("Router：applSeq(" + routerMatch.getApplSeq() + "),当前时间【" + dateTimeNow + "】");
			Long timeNow = ParamUtil.changeTimeLong(date);

			//合作项目生效时间判断
			Long effectTime = ParamUtil.changeDateTimeLong(bean.getProject().getEffectTime());
			//当前时间大于生效时间
			if(effectTime >= dateTimeNow){
				map.remove(key);
				StringBuilder message = new StringBuilder("");
				message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append("未到生效时间")
						.append(",生效时间：").append(effectTime)
						.append(",当前时间：").append(dateTimeNow);
				bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),bean.getProject().getId(),message);
				continue;
			}
			if(
					(null!= bean.getProject().getNoBusiTimeStart()) &&
							!("".equals(bean.getProject().getNoBusiTimeStart())) &&
							(null!= bean.getProject().getNoBusiTimeEnd()) &&
							!("".equals(bean.getProject().getNoBusiTimeEnd()))
					){
				//业务受理时间
				if(ParamUtil.checkBusiTime(bean.getProject().getNoBusiTimeStart(),bean.getProject().getNoBusiTimeEnd(),timeNow,routerMatch.getApplSeq())){
					map.remove(key);
					StringBuilder message = new StringBuilder("");
					message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append("未处于业务受理时间内")
							.append(",业务受理时间：").append(bean.getProject().getNoBusiTimeStart())
							.append(" - ").append(bean.getProject().getNoBusiTimeEnd())
							.append(",当前时间：").append(timeNow);
					bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),bean.getProject().getId(),message);
					continue;
				}
			}
			if(null != bean.getProject().getCustLoanStart() && null != bean.getProject().getCustLoanEnd()){
				//用户贷款金额判断
				BigDecimal startLoanAmount = bean.getProject().getCustLoanStart();
				BigDecimal endLoanAmount = bean.getProject().getCustLoanEnd();
				//当前贷款金额小于贷款金额下限
				boolean startFlag = (routerMatch.getOrigPrcp().compareTo(startLoanAmount)) < 0;
				//当前贷款金额大于贷款金额上限
				boolean endFlag = (routerMatch.getOrigPrcp().compareTo(endLoanAmount)) > 0;
				if(startFlag || endFlag){
					map.remove(key);
					StringBuilder message = new StringBuilder("");
					message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append("超过用户贷款金额上下限")
							.append(",用户贷款金额区间：").append(startLoanAmount).append("元")
							.append(" - ").append(endLoanAmount).append("元");
					bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),bean.getProject().getId(),message);
					continue;
				}
			}
			if(null != bean.getProject().getCustFirstCreditAgo() && !("".equals(bean.getProject().getCustFirstCreditAgo()))){
				//用户首次用信距今天数是否满足
				int needDayAgo = Integer.parseInt(bean.getProject().getCustFirstCreditAgo());
				if(dayAgo < needDayAgo){//首次用信距今天数小于要求天数则舍去
					map.remove(key);
					StringBuilder message = new StringBuilder("");
					message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append("未达到首次用信距今天数")
							.append(",要求距今").append(needDayAgo).append("天")
							.append(",该客户实际距今").append(dayAgo).append("天");
					bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),bean.getProject().getId(),message);
					continue;
				}
			}
			//渠道支持银行卡
			Map<String,String> bankSupportMap = bean.getSupportBank();
			String bankName = bankSupportMap.get(routerMatch.getRepayCardBankNo());
			if(null == bankName){
				map.remove(key);
				StringBuilder message = new StringBuilder("");
				message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append("不支持该银行")
						.append(",数字编码:").append(routerMatch.getRepayCardBankNo());
				bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),bean.getProject().getId(),message);
				continue;
			}

			//合作项目是否支持期限(只要小于该期限就满足)
			boolean periodFlag = true;
			Map<String,Map<Long,CooperationPeriod>> periodTypeMap = bean.getPeriodMap();
			Map<Long,CooperationPeriod> periodMap = periodTypeMap.get(routerMatch.getPeriodType());
			if(null == periodMap || 0 == periodMap.size()){
				periodFlag = false;
			}else{
				//只要存在比设定大的期限就不满足
				for(Long period : periodMap.keySet()){
					if(routerMatch.getPeriod() > period){
						periodFlag = false;
					}
				}
			}
			if(!periodFlag){
				map.remove(key);
				String periodMessage = "";
				for(String periodKey : periodTypeMap.keySet()){
					Map<Long,CooperationPeriod> periodKeyMap = periodTypeMap.get(periodKey);
					for(Long period : periodKeyMap.keySet()){
						CooperationPeriod cooperationPeriod = periodKeyMap.get(period);
						periodMessage = periodMessage +
								"[" + cooperationPeriod.getCooperationPeriodValue() +
								cooperationPeriod.getCooperationPeriodType() +
								"]";
					}
				}
				if("".equals(periodMessage)){
					periodMessage = "无";
				}
				StringBuilder message = new StringBuilder("");
				message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append("不支持该期限")
						.append(",期限").append(routerMatch.getPeriod())
						.append(",期限类型").append(routerMatch.getPeriodType())
						.append(",只支持").append(periodMessage);
				bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),bean.getProject().getId(),message);
				continue;
			}
		}
	}

	private RouterMatchData checkPositionAndPriority(Map<String,RouterMatchData> map,RouterMatchIn routerMatch){
		RouterMatchData routerMatchData;
		//优先级Map<合作机构ID,Map<合作项目Id,RouterMatchData>
		Map<String,Map<String,RouterMatchData>> priorityMap = new HashMap();
		int agencyPriority = 0;
		String priorityAgencyId = null;
		for(String key : map.keySet()){
			RouterMatchData bean = map.get(key);
			PositionSplitIn in = routerCommonComponent.getPositionSplitParam(routerMatch,bean.getProject());
			PositionSplitOut out = PositionSplitUtils.dealPositionSplit(in);
			if((PL0601Enum.PL0601_1_10.getCode())
					.equals(out.getStatus().getCode())) {//拆分成功
				bean.setPositionSplitIn(in);
				bean.setPositionSplitOut(out);

				Map<String,RouterMatchData> projectMap = priorityMap.get(bean.getProject().getAgencyId());
				if(null == projectMap){
					projectMap = new HashedMap();
				}
				projectMap.put(bean.getProject().getId(),bean);
				priorityMap.put(bean.getProject().getAgencyId(),projectMap);

				//当前优先级 < 该数据优先级
				if(agencyPriority < Integer.parseInt(bean.getAgency().getAgencyPriority())){
					agencyPriority = Integer.parseInt(bean.getAgency().getAgencyPriority());
					priorityAgencyId = bean.getAgency().getId();
				}
			}else{
				StringBuilder message = new StringBuilder("");
				message.append("Router：applSeq：").append(routerMatch.getApplSeq()).append(",合作项目：").append(bean.getProject().getId()).append(out.getFlagMessage())
						.append(",资方占比：").append(in.getAgencyRatio())
						.append("客户申请放款金额：").append(in.getOrigPrcp()).append("元")
						.append(",合作项目放款总额：").append(in.getLoanAmount()).append("元")
						.append(",合作项目已放款额：").append(in.getAlreadyLoanAmount()).append("元")
						.append(",合作项目限额(日)：").append(in.getLoanLimitDay()).append("元")
						.append(",合作项目当日放已放款额：").append(in.getAlreadyLoanLimitDay()).append("元")
						.append(",合作项目限额(月)：").append(in.getLoanLimitMonth()).append("元")
						.append(",合作项目当月已放款额：").append(in.getAlreadyLoanLimitMonth()).append("元");
				bussLog(routerMatch.getSerNo(),routerMatch.getApplSeq(),in.getProjectId(),message);
			}
		}
		if(null == priorityAgencyId){//未找到匹配的机构
			StringBuilder message = new StringBuilder("未找到匹配的合作机构,执行海尔消金放款...");
			bussLogPriority(routerMatch.getSerNo(),routerMatch.getApplSeq(),message);
			return null;
		}else{
			StringBuilder message = new StringBuilder("筛选出优先级最高的合作合作机构：" + priorityAgencyId);
			bussLogPriority(routerMatch.getSerNo(),routerMatch.getApplSeq(),message);
		}

		//找到合作机构后匹配合作项目
		Map<String,RouterMatchData> projectMap = priorityMap.get(priorityAgencyId);
		int projectPriority = 0;
		String priorityProjectId = null;
		for(String key : projectMap.keySet()){
			RouterMatchData bean = projectMap.get(key);
			//当前优先级 < 该数据优先级
			if(projectPriority < Integer.parseInt(bean.getProject().getProjectPriority())){
				projectPriority = Integer.parseInt(bean.getProject().getProjectPriority());
				priorityProjectId = bean.getProject().getId();
			}
		}
		if(null == priorityProjectId){//未找到匹配的项目
			StringBuilder message = new StringBuilder("未找到匹配的合作项目,执行海尔消金放款...");
			bussLogPriority(routerMatch.getSerNo(),routerMatch.getApplSeq(),message);
			return null;
		}else{
			StringBuilder message = new StringBuilder("筛选出优先级最高的合作项目：" + priorityProjectId);
			bussLogPriority(routerMatch.getSerNo(),routerMatch.getApplSeq(),message);
		}
		routerMatchData = projectMap.get(priorityProjectId);
		return routerMatchData;
	}

	private void countTime(String serNo,String applSeq,Long startTime,String message){
		Long endTime = System.currentTimeMillis();
		Long settleTime = endTime - startTime;
		logger.debug("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +",执行：" + message + "操作耗时：" + settleTime + "毫秒");
	}

	private void bussLog(String serNo,String applSeq,String projectId,StringBuilder message){
		logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +
				",合作项目(根据优先级筛选时为空)：" + projectId +
				"," + message.toString() + ",数据过滤...datafilter");
	}

	private void bussLogPriority(String serNo,String applSeq,StringBuilder message){
		logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +
				"," + message.toString());
	}

}
