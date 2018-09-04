package com.haiercash.pluslink.capital.router.server.dao;

import org.springframework.stereotype.Repository;

/**
 * 公共数据查询接口
 * @author WDY
 * @date 20180718
 * @rmk
 */
@Repository
public interface CommonDao {

    /**查询序列**/
    String selectSeqBySeqName(String seqName);
}
