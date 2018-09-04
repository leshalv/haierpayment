package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.RouteRuleItem;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

  /**
	* 路由规则明细表数据库操作接口类
	* @author WDY
	* @date 20180615
	* @rmk 
	*/
@Repository
public interface RouteRuleItemDao extends BaseMapper<RouteRuleItem> {

	  /**
	   * 查询路由规则明细(初始化进redis时使用,关联路由规则表部分字段)
	   * 规则编号(明细)
	   * 业务类型(明细)
	   * 规则内容(明细)
	   * 匹配规则(规则)
	   * 资金渠道(规则)
	   * 渠道产品(规则)
	   * 优先级(规则)
	   * @author WDY
	   * @inParam
	   * @outParam
	   * @date 20180615
	   * @rmk
	   */
	  List<RouteRuleItem> selectRouteRuleAndItemList(RouteRuleItem routeRuleItem);

  }
