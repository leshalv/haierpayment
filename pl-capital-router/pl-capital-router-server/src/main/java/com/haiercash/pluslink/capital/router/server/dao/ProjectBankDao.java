package com.haiercash.pluslink.capital.router.server.dao;

import com.haiercash.pluslink.capital.data.ProjectBank;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

  /**
	* 合作项目支持银行表数据库接口类
	* @author WDY
	* @date 20180713
	* @rmk 
	*/
@Repository
public interface ProjectBankDao extends BaseMapper<ProjectBank> {}