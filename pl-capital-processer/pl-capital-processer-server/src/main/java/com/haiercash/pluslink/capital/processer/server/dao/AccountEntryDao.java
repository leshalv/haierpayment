package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.AccountEntry;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/26 09:59
 * @Description:记账分录持久层
 */
@Repository
public interface AccountEntryDao extends BaseMapper<AccountEntry> {
    /**
     * 存入分录明细list
     */
    void insertAccountEntryList(List<AccountEntry> accountEntryList);

    /**
     * 存入分录明细
     */
    void insertAccountEntry(AccountEntry accountEntryList);

    /**
     * 获取序列
     */
    String getSequence();

    /**
     * 根据主键ID 更改账户
     */
    void updateAccoutIdById(AccountEntry accountEntry);

    /**
     * 根据transIda查询分录表
     */
    List<AccountEntry> selectAccountEntryByTransId(@Param("transId") String transId, @Param("delFlag") String delFlag);
}