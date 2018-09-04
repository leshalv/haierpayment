package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.Dictionary;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

  /**
	* 数据字典主表数据库接口类
	* @author WDY
	* @date 20180713
	* @rmk 
	*/
@Repository
public interface DictionaryDao extends BaseMapper<Dictionary> {}