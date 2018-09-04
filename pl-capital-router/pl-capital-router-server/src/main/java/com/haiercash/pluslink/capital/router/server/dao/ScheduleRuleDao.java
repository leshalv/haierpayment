package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.ScheduleRule;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

  /**
	* 调度规则表数据库接口类
	* @author WDY
	* @date 20180713
	* @rmk 
	*/
@Repository
public interface ScheduleRuleDao extends BaseMapper<ScheduleRule> {}