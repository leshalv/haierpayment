package com.haiercash.pluslink.capital.router.server.component.impl;

import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.enums.dictionary.PL0404Enum;
import com.haiercash.pluslink.capital.router.server.component.IRouteRuleMatchComponent;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchData;
import com.haiercash.pluslink.capital.router.server.service.IRouterMatchDataService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 路由匹配实现类
 * @author WDY
 * 2018-06-07
 */
@Component("routeRuleMatchComponent")
public class RouteRuleMatchComponentImpl extends BaseService implements IRouteRuleMatchComponent {

	@Autowired
	IRouterMatchDataService routerMatchDataServiceImpl;

	/**筛选标准数组**/
	private static final String[] DEAL_ROUTE_MATCH_TYPE_ARRAY = {PL0404Enum.PL0404_1_P.getCode(),PL0404Enum.PL0404_2_C.getCode(),PL0404Enum.PL0404_3_S.getCode()};

	/**资产路由
	 * 传入参数：
	 * cashProId ： 消金产品编号
	 * cashBusiChannel ： 消金产品渠道
	 * cashCustSign ： 消金客户标签
	 *
	 * map规则1：map1.get(P|产品)
	 * map规则2：map1.get(C|渠道)
	 * map规则3：map1.get(S|标签)
	 * 定义参数：map匹配通过
	 * map规则1,map规则2,map规则3,map匹配通过泛型均Map<String,bean>
	 *
	 * (1)循环map规则1根据(bean.匹配规则)属性判断
	 * 例如：匹配规则为：产品(P) + 渠道(C) + 标签(S)
	 * 如果map规则2.get(规则编号(map规则1的key)) 不为null，
	 * 继续判断规则3.get(规则编号(map规则1的key))，如果不为null，匹配通过。
	 * 反之，匹配失败删除3个map中key为该规则编号的值即：
	 *
	 * (2)循环map规则2,同map规则1的处理方式
	 *
	 * (3)循环map规则3,同map规则1的处理方式
	 *
	 * (4)匹配通过数据为：
	 * map匹配通过.putAll(map规则1);
	 * map匹配通过.putAll(map规则2);
	 * map匹配通过.putAll(map规则3);**/
	public Map<String,RouterMatchData> dealRouteRuleMatch(String cashProId, String cashBusiChannel, String cashCustSign,String serNo,String applSeq){

		Map<String,Map<String,RouterMatchData>> map = routerMatchDataServiceImpl.getRouterMatchData(serNo,applSeq);

		//map规则1：map1.get(P|产品)
		StringBuilder productKey = new StringBuilder();
		productKey.append(PL0404Enum.PL0404_1_P.getCode()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(cashProId);
		Map<String,RouterMatchData> productMap = map.get(productKey.toString());
		//map规则2：map1.get(C|渠道)
		StringBuilder channelKey = new StringBuilder();
		channelKey.append(PL0404Enum.PL0404_2_C.getCode()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(cashBusiChannel);
		Map<String,RouterMatchData> channelMap = map.get(channelKey.toString());
		//map规则3：map1.get(S|标签)
		StringBuilder signKey = new StringBuilder();
		signKey.append(PL0404Enum.PL0404_3_S.getCode()).append(CommonConstant.ROUTE_RULE_CACHE_KEY_SIGN).append(cashCustSign);
		Map<String,RouterMatchData> signMap = map.get(signKey.toString());

		//定义符合匹配规则的部分
		Map<String,RouterMatchData> matchMap = new HashMap();

		if(null == productMap){
			productMap = new HashedMap();
		}
		if(null == channelMap){
			channelMap = new HashedMap();
		}
		if(null == signMap){
			signMap = new HashedMap();
		}
		//(1)循环map规则1 根据(bean.匹配规则)属性判断
		//例如：匹配规则为：产品(P) + 渠道(C) + 标签(S)
		//如果map规则2.get(规则编号(map规则1的key)) 不为null，
		//继续判断规则3.get(规则编号(map规则1的key))，如果不为null，匹配通过。
		//反之，匹配失败删除3个map中key为该规则编号的值即：
		for(int i=0;i<DEAL_ROUTE_MATCH_TYPE_ARRAY.length;i++){
			dealRouteMatch(
					productMap,
					channelMap,
					signMap,
					DEAL_ROUTE_MATCH_TYPE_ARRAY[i],
					serNo,
					applSeq);
		}
		matchMap.putAll(productMap);
		matchMap.putAll(channelMap);
		matchMap.putAll(signMap);
		return matchMap;
	}

	/**处理路由规则明细**/
	private void dealRouteMatch(Map<String,RouterMatchData> pMap,Map<String,RouterMatchData> cMap,Map<String,RouterMatchData> sMap,String type,String serNo,String applSeq){
		Map<String,RouterMatchData> entryMap = new HashMap();
		if(PL0404Enum.PL0404_1_P.getCode().equals(type)){
			entryMap.putAll(pMap);
		}
		if(PL0404Enum.PL0404_2_C.getCode().equals(type)){
			entryMap.putAll(cMap);
		}
		if(PL0404Enum.PL0404_3_S.getCode().equals(type)){
			entryMap.putAll(sMap);
		}

		Iterator<Map.Entry<String,RouterMatchData>> entries = entryMap.entrySet().iterator();

		while(entries.hasNext()){
			Map.Entry<String,RouterMatchData> entry = entries.next();
			String key = entry.getKey();
			RouterMatchData bean = entry.getValue();
			//产品Map是否判断
			boolean pFlag = false;
			//渠道Map是否判断
			boolean cFlag = false;
			//标签Map是否判断
			boolean sFlag = false;
			//是否匹配通过
			boolean matchFlag = true;
			//规则1：产品
			if(PL0404Enum.PL0404_1_P.getCode().equals(bean.getProject().getMateRule())){
				pFlag = true;
			}
			//规则2：渠道
			if(PL0404Enum.PL0404_2_C.getCode().equals(bean.getProject().getMateRule())){
				cFlag = true;
			}
			//规则3：标签
			if(PL0404Enum.PL0404_3_S.getCode().equals(bean.getProject().getMateRule())){
				sFlag = true;
			}
			//规则4：产品+渠道
			if(PL0404Enum.PL0404_4_PC.getCode().equals(bean.getProject().getMateRule())){
				pFlag = true;
				cFlag = true;
			}
			//规则5：渠道 +标签
			if(PL0404Enum.PL0404_5_CS.getCode().equals(bean.getProject().getMateRule())){
				cFlag = true;
				sFlag = true;
			}
			//规则6：产品+标签
			if(PL0404Enum.PL0404_6_PS.getCode().equals(bean.getProject().getMateRule())){
				pFlag = true;
				sFlag = true;
			}
			//规则7：产品 +渠道+标签
			if(PL0404Enum.PL0404_7_PCS.getCode().equals(bean.getProject().getMateRule())){
				pFlag = true;
				cFlag = true;
				sFlag = true;
			}

			//判断产品
			if(pFlag){
				if(null == pMap.get(key)){
					matchFlag = false;
					logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +
							",合作项目：" + bean.getProject().getId() +
							",匹配规则：" + bean.getProject().getMateRule() +
							",根据消金产品筛选失败,数据过滤...datafilter");
				}
			}
			//判断渠道
			if(cFlag){
				if(null == cMap.get(key)){
					matchFlag = false;
					logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +
							",合作项目：" + bean.getProject().getId() +
							",匹配规则：" + bean.getProject().getMateRule() +
							",根据消金入件渠道筛选失败,数据过滤...datafilter");
				}
			}
			//判断标签
			if(sFlag){
				if(null == sMap.get(key)){
					matchFlag = false;
					logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")" +
							",合作项目：" + bean.getProject().getId() +
							",匹配规则：" + bean.getProject().getMateRule() +
							",根据消金客户标签筛选失败,数据过滤...datafilter");
				}
			}

			//如果匹配未通过
			if(!matchFlag){
				pMap.remove(key);
				cMap.remove(key);
				sMap.remove(key);
			}
		}

	}
}
