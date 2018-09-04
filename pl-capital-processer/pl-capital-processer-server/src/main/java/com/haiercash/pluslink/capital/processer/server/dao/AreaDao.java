package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.Area;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * 省市区
 * <p>
 * 区域信息
 *
 * @author xiaobin
 * @create 2018-07-19 下午1:54
 **/
@Component
public interface AreaDao extends BaseMapper<Area> {

    Area selectById(@Param("id") String id);

}
