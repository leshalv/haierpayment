package com.haiercash.pluslink.capital.manager.dao;

import org.springframework.stereotype.Repository;

/**
 * SequenceDao
 *
 * @author zhanggaowei
 * @date 2018/5/25
 */
@Repository
public interface SequenceDao {
    //获取导入请求批次的序列号
    String getImMsgRequestBatchSequence();
}
