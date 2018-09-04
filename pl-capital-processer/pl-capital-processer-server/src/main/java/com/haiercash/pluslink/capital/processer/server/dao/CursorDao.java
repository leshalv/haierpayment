package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.Cursor;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * > 临时表数据Dao
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/20 17:19
 */
@Repository
public interface CursorDao extends BaseMapper<Cursor>{

    void deleteByContractNo(@Param("contractNo") String contractNo);
}
