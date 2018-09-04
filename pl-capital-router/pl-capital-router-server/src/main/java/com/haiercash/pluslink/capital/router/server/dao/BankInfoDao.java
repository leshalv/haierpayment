package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.BankInfo;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

  /**
	* 银行信息表数据库接口类
	* @author WDY
	* @date 20180713
	* @rmk 
	*/
@Repository
public interface BankInfoDao extends BaseMapper<BankInfo> {}