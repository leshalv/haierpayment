package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.BalancePayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lzh
 * @Title: BalancePayRequestInfoDao
 * @ProjectName pl-capital
 * @Description:余额支付信息表dao
 * @date 2018/7/1313:27
 */
@Repository
public interface BalancePayRequestInfoDao {
    //插入余额支付信息(目前还未建表战时模拟，建完表后修改类名，实体名)
    void insertBalancePayRequestInfo(@Param("balancePayRequestInfos") List<BalancePayInfo> balancePayRequestInfos);

    //得到主键id
    String getSequence();
}
