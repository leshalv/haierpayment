package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.ProcesserJob;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fengxingqiang
 * @date 2018/7/20
 * 任务调度持久层
 */
@Repository
public interface ProcesserJobDao extends BaseMapper<ProcesserJob> {

    List<ProcesserJob> selectByJobStatus(@Param("jobStartDate") String jobStartDate, @Param("jobEndDate") String jobEndDate,
                                         @Param("modelName") String modelName, @Param("delFlag") String delFlag);

    void updateStatusById(@Param("id") String id, @Param("runTime") Long runTime, @Param("delFlag") String delFlag);

    void updateNextDateById(@Param("id") String id, @Param("runTime") Long runTime, @Param("jobStartDate") String jobStartDate);

    void insertProcesserJob(ProcesserJob job);

    void updateErrorInfo(@Param("runErrorStatus") String runErrorStatus, @Param("runTime") Long runTime, @Param("runErrorMsg") String runErrorMsg, @Param("id") String id, @Param("delFlag") String delFlag);
}
