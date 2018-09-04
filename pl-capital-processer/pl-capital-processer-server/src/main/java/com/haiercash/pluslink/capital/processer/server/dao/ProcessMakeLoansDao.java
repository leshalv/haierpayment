package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.ProcessMakeLoans;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 放款记录
 *
 * @author xiaobin
 * @create 2018-07-25 下午3:15
 **/
@Repository
public interface ProcessMakeLoansDao extends BaseMapper<ProcessMakeLoans> {

    /**
     * 根据业务编号查询放款记录
     *
     * @param applSeq
     * @return
     */
    ProcessMakeLoans selectByApplSeq(@Param("applSeq") String applSeq,@Param("delFlag") String delFlag);
}
