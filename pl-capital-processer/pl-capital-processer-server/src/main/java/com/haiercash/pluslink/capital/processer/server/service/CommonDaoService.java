package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.data.*;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.processer.server.cache.AssetsSplitCache;
import com.haiercash.pluslink.capital.processer.server.cache.AssetsSplitItemCache;
import com.haiercash.pluslink.capital.processer.server.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: yu jianwei
 * @Date: 2018/8/6 17:47
 * @Description:
 */
@Service
public class CommonDaoService {
    @Autowired
    private SequenceDao sequenceDao;
    @Autowired
    private AccountEntryDao accountEntryDao;
    @Autowired
    private AccountTransactionDao accountTransactionDao;
    @Autowired
    private AssetsSplitDao assetsSplitDao;
    @Autowired
    private AssetsSplitItemDao assetsSplitItemDao;
    @Autowired
    private RouteResultRecordDao plRouteResultRecordDao;
    @Autowired
    private CooperationProjectDao cooperationProjectDao;

    @Autowired
    private AssetsSplitCache assetsSplitCache;

    @Autowired
    private AssetsSplitItemCache assetsSplitItemCache;

    /*
     * getSequence
     * 获取序列
     *
     * @param
     * @return java.lang.String
     * @author yu jianwei
     * @date 2018/8/10 14:28
     */
    public String getSequence(String seqName) {
        return sequenceDao.selectSeqBySeqName(seqName);
    }

    /*
     * getTransactionSequence
     *获取事务表序列
     * @author yu jianwei
     * @date 2018/8/10 17:15
     * @param []
     * @return java.lang.String
     */
    public String getTransactionSequence() {
        return accountTransactionDao.getSequence();
    }

    /*
     * getAccountEntrySequence
     *获取分录表序列
     * @author yu jianwei
     * @date 2018/8/10 17:18
     * @param []
     * @return java.lang.String
     */
    public String getAccountEntrySequence() {
        return accountEntryDao.getSequence();
    }

    /*
     * selectBycontractNo
     * 记账获取资产表
     * @author yu jianwei
     * @date 2018/8/10 14:28
     * @param [contractNo]
     * @return com.haiercash.pluslink.capital.data.AssetsSplit
     */
    public AssetsSplit selectBycontractNo(String contractNo) {
        return assetsSplitDao.selectBycontractNo(contractNo, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /*
     * selectByAssetsSpiltId
     * 记账，根据资产主键查询 资产拆分明细
     * @author yu jianwei
     * @date 2018/8/10 14:33
     * @param [assetsSplitId]
     * @return java.util.List<com.haiercash.pluslink.capital.data.AssetsSplitItem>
     */
    public List<AssetsSplitItem> selectByAssetsSpiltId(String assetsSplitId) {
        return assetsSplitItemDao.selectByAssetsSpiltId(assetsSplitId, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /*
     * insertAccountTransaction
     *
     * @author yu jianwei
     * @date 2018/8/10 14:40
     * @param [accountTransaction]
     * @return void
     */
    public void insertAccountTransaction(AccountTransaction accountTransaction) {
        accountTransactionDao.insertAccountTransaction(accountTransaction);
    }

    /*
     * insertAccountEntryList
     *
     * @author yu jianwei
     * @date 2018/8/10 14:40
     * @param [accountEntrys]
     * @return void
     */
    public void insertAccountEntryList(List<AccountEntry> accountEntrys) {
        accountEntryDao.insertAccountEntryList(accountEntrys);
    }

    /*
     * insertAccountEntry
     *
     * @author yu jianwei
     * @date 2018/8/11 10:10
     * @param [accountEntry]
     * @return void
     */
    public void insertAccountEntry(AccountEntry accountEntry) {
        accountEntryDao.insertAccountEntry(accountEntry);
    }

    /*
     * selectByAccountingStatus
     * 记账，查询入账事务表信息
     * @author yu jianwei
     * @date 2018/8/10 14:45
     * @param []
     * @return java.util.List<com.haiercash.pluslink.capital.data.AccountTransaction>
     */
    public List<AccountTransaction> selectByAccountingStatus(String status) {
        return accountTransactionDao.selectByAccountingStatus(status, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /*
     * selectAccountEntryByTransId
     *记账，根据事务表id 查询分录表信息
     * @author yu jianwei
     * @date 2018/8/10 14:46
     * @param [transactionId]
     * @return java.util.List<com.haiercash.pluslink.capital.data.AccountEntry>
     */
    public List<AccountEntry> selectAccountEntryByTransId(String transactionId) {
        return accountEntryDao.selectAccountEntryByTransId(transactionId, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /*
     * updateAccountingStatuById
     *记账，更新记账状态
     * @author yu jianwei
     * @date 2018/8/10 14:53
     * @param [accountTransaction]
     * @return void
     */
    public void updateAccountingStatuById(AccountTransaction accountTransaction) {
        accountTransactionDao.updateAccountingStatuById(accountTransaction);
    }

    /*
     * updateAccoutIdById
     *记账 更新分录表账号
     * @author yu jianwei
     * @date 2018/8/10 14:53
     * @param [accountEntry]
     * @return void
     */
    public void updateAccoutIdById(AccountEntry accountEntry) {
        accountEntryDao.updateAccoutIdById(accountEntry);
    }

    /*
     * selectByApplSeq
     *根据业务编号查询<>路由结果记录表</> 获取资方放款占比，合作机构表主键id ,合作项目表主键id,渠道号channelNo
     * @author yu jianwei
     * @date 2018/8/11 10:40
     * @param [applSeq]
     * @return com.haiercash.pluslink.capital.data.RouteResultRecord
     */
    public RouteResultRecord selectByApplSeq(String applSeq) {
        RouteResultRecord routeResultRecord = plRouteResultRecordDao.selectByApplSeq(applSeq);
        return routeResultRecord;
    }

    /*
     * selectById
     *根据合作项目表主键projectId 查询<>合作项目表</>获取 限额
     * @author yu jianwei
     * @date 2018/8/11 10:47
     * @param [projectId]
     * @return com.haiercash.pluslink.capital.data.CooperationProject
     */
    public CooperationProject selectById(String projectId) {
        return cooperationProjectDao.selectById(projectId, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /*
     * updateProjectTypeById
     * 根据主键id 更改是否联合放款
     * @author yu jianwei
     * @date 2018/8/14 9:55
     * @param [assetsSplit]
     * @return void
     */
    public void updateProjectTypeById(AssetsSplit assetsSplit) {
        assetsSplit.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        assetsSplitCache.put(assetsSplit);
        assetsSplitDao.updateProjectTypeById(assetsSplit, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /*
     * insertAssetsSplitItemList
     * 存入资产拆分明细list
     * @author yu jianwei
     * @date 2018/8/14 10:02
     * @param [assetsSplitItems]
     * @return void
     */
    public void insertAssetsSplitItemList(List<AssetsSplitItem> assetsSplitItems) {
        assetsSplitItemDao.insertAssetsSplitItemList(assetsSplitItems);
        assetsSplitItems.forEach(item-> assetsSplitItemCache.put(item));
    }

    /*
     * insertAssetsSplitItem
     * 存入资产拆分明细
     * @author yu jianwei
     * @date 2018/8/14 10:12
     * @param [assetsSplitItem]
     * @return void
     */
    public void insertAssetsSplitItem(AssetsSplitItem assetsSplitItem) {
        assetsSplitItemDao.insert(assetsSplitItem);
        assetsSplitItemCache.put(assetsSplitItem);
    }

}
