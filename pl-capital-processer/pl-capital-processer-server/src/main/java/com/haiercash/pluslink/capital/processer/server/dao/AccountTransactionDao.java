package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.AccountTransaction;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/26 09:58
 * @Description:记账事务持久层
 */
@Repository
public interface AccountTransactionDao extends BaseMapper<AccountTransaction> {

    /**
     * 获取序列
     */
    String getSequence();

    void insertAccountTransaction(AccountTransaction accountTransaction);
    /**
     * 根据主键id 更改推送状态
     */
    void updateAccountingStatuById(AccountTransaction accountTransaction);

    /**
     * 根据入账状态定时扫描事务表
     */
    List<AccountTransaction> selectByAccountingStatus(@Param("accountingStatus") String accountingStatus, @Param("delFlag") String delFlag);
}
