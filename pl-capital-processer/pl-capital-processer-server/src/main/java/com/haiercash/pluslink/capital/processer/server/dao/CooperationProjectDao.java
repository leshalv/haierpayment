package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.CooperationProject;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/14 13:16
 * @Description:
 */
@Repository
public interface CooperationProjectDao extends BaseMapper<CooperationProject> {

    CooperationProject selectById(@Param("id") String id, @Param("delFlag") String delFlag);
}
