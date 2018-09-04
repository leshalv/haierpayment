package com.haiercash.pluslink.capital.processer.server.dao;

import org.springframework.stereotype.Repository;

/**
 * @Auther: yu jianwei
 * @Date: 2018/8/4 10:54
 * @Description:
 */
@Repository
public interface SequenceDao {
    /**
     * 查询序列
     */
    String selectSeqBySeqName(String seqName);

}
