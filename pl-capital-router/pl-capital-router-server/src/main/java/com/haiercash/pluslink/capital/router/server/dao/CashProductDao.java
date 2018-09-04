package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.CashProduct;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

  /**
	* 消金产品表数据库接口类
	* @author WDY
	* @date 20180713
	* @rmk 
	*/
@Repository
public interface CashProductDao extends BaseMapper<CashProduct> {}