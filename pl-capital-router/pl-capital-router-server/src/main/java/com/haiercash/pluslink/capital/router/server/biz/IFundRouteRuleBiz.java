package com.haiercash.pluslink.capital.router.server.biz;

import com.haiercash.pluslink.capital.router.server.entity.RouterMatchIn;
import com.haiercash.pluslink.capital.router.server.entity.RouterResult;

/**
 * 资金路由规则主逻辑接口
 * @author WDY
 * 2018-06-07
 */
public interface IFundRouteRuleBiz{

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
	 * 10, 判断资金方剩余额度是否充足
	 * 11，匹配优先级(先匹配合作机构,再匹配合作项目)
	 * 12，封装路由结果数据
	 **/
	RouterResult dealFundRouteMatch(RouterMatchIn routerMatchIn);

}
