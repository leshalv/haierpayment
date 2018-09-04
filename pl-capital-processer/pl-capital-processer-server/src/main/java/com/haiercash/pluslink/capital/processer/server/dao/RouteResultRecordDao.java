package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.RouteResultRecord;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author yu jianwei
 * @date 2018/7/13 14:20
 * 路由结果记录持久层
 */
@Repository
public interface RouteResultRecordDao extends BaseMapper<RouteResultRecord> {
    RouteResultRecord selectByApplSeq(String applSeq);
}
