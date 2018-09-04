package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.RepaymentInfo;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

  /**
	* 还款方式表数据库接口类
	* @author WDY
	* @date 20180713
	* @rmk 
	*/
@Repository
public interface RepaymentInfoDao extends BaseMapper<RepaymentInfo> {}