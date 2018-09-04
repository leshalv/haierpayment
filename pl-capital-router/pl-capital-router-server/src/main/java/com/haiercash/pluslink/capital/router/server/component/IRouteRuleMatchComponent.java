package com.haiercash.pluslink.capital.router.server.component;

import com.haiercash.pluslink.capital.router.server.entity.RouterMatchData;
import java.util.Map;

/**
 * 路由匹配接口
 * @author WDY
 * 2018-06-07
 */
public interface IRouteRuleMatchComponent {

	/**资产路由**/
	Map<String,RouterMatchData> dealRouteRuleMatch(String cashProId, String cashBusiChannel, String cashCustSign,String serNo,String applSeq);

}
