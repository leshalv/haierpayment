package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.ProcesserFlowLog;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaobin
 * @create 2018-08-06 下午1:25
 **/
@Repository
public interface ProcesserFlowLogDao extends BaseMapper<ProcesserFlowLog> {

    void insertProcesserFlowList(List<ProcesserFlowLog> processerFlowLogList);

    List<ProcesserFlowLog> selectByApplSeq(@Param("applSeq") String applSeq);
}
