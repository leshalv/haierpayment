package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.SystemException;
import com.haiercash.pluslink.capital.common.utils.RestUtils;
import com.haiercash.pluslink.capital.common.utils.SequenceUtil;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.AccountEntry;
import com.haiercash.pluslink.capital.data.AccountTransaction;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.enums.SystemFlagEnum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0105Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0505Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0602Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1101Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1102Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1202Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1203Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1205Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1206Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1207Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1301Enum;
import com.haiercash.pluslink.capital.processer.server.config.AccountIdConfig;
import com.haiercash.pluslink.capital.processer.server.dao.SequenceDao;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.utils.threading.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/26 11:10
 * @Description:记账业务层
 */
@Service
public class AccountService extends BaseService {
    @Autowired
    private AccountToPgwService accountToPgwService;
    @Autowired
    private AccountIdConfig accountIdConfig;
    @Autowired
    private CommonDaoService commonDaoService;
    @Autowired

    private SequenceDao sequenceDao;
    private static final String tradeLink = "支付";
    private static final String voucherNo = "PZ4001";

    /**
     * account
     * 记账
     *
     * @param
     * @return void
     * @author yu jianwei
     * @date 2018/7/26 11:19
     */
    public void accountRecord(String contractNo) {
        logger.info("===============<处理中心>-记账->开始->contractNo：" + contractNo);
        if (StringUtils.isEmpty(contractNo)) {
            logger.error("===============<处理中心>-记账->合同号为null");
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
        }
        try {
            AssetsSplit assetsSplit = commonDaoService.selectBycontractNo(contractNo);
            logger.info("===============<处理中心>-记账->查询资产表完毕=========");
            if (StringUtils.isEmpty(assetsSplit)) {
                logger.error("===============<处理中心>-记账->资产数据不存在->contractNo：" + contractNo);
                throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
            }
            String assetsSplitId = assetsSplit.getId();
            logger.info("=============<处理中心>-记账->资产数据id:" + assetsSplitId);
            List<AssetsSplitItem> assetsSplitItems = commonDaoService.selectByAssetsSpiltId(assetsSplitId);
            logger.info("===============<处理中心>-记账->查询资产明细表完毕=========");
            if (StringUtils.isEmpty(assetsSplitItems) || assetsSplitItems.size() == 0) {
                logger.error("===============<处理中心>-记账->资产明细数据不存在->assetsSplitId：" + assetsSplitId);
                throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
            }
            /*是否联合放款*/
            String projectType = assetsSplit.getProjectType();
            if (projectType.equals(PL0505Enum.PL0505_1_UNION.getCode())) {
                logger.info("===============<处理中心>-记账-><联合>放款记账开始======");
                logger.info("===============<处理中心>-记账->1.工行放款成功，工行打款给我司放款事务及分录开始======");
                saveSilverDeposit(assetsSplitItems, assetsSplit);
            }
            logger.info("===============<处理中心>-记账-><非联合>放款记账开始======");
            logger.info("===============<处理中心>-记账->２.应付抹平<机构中间户>（由于应付账款贷款，贷方是核算记账不走va,所以要发起抹平）开始======");
            Optional<AssetsSplitItem> any = assetsSplitItems.stream().filter((assetsSplitIte) -> PL0602Enum.PL0602_1_OWN.getCode().equals(assetsSplitIte.getLoanType())).findAny();
            if (any.isPresent()) {
                AssetsSplitItem assetsSplitItem = any.get();
                AccountTransaction accountTransaction = saveAgencyAccount(assetsSplit, assetsSplitItem.getTransAmt());
                logger.info("===============<处理中心>-记账->３.资金平台明确放款方式后记账开始======");
                saveOtherReceivables(assetsSplitItems, assetsSplit, projectType, accountTransaction.getTransNo());
            } else {
                logger.error("===============<处理中心>-记账->资产明细<自有>数据不存在->assetsSplitId：" + assetsSplitId);
                throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
            }

        } catch (SystemException e) {
            logger.error("===============<处理中心>-记账->出现system异常->合同号contractNo：" + contractNo);
            throw new SystemException(e.getCode(), e.getMessage(), ApiContextManager.getSerNo(), e);
        } catch (Exception e) {
            logger.error("===============<处理中心>-记账->出现未知异常：", e);
            throw new SystemException(CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getCode(), CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getDesc(), ApiContextManager.getSerNo(), e);
        }
    }

    /*
     * 1.saveSilverDeposit
     *工行放款成功，工行打款给我司放款事务及分录
     * @param
     * @return void
     * @author yu jianwei
     * @date 2018/7/31 17:09
     */
    @Transactional
    public void saveSilverDeposit(List<AssetsSplitItem> assetsSplitItems, AssetsSplit assetsSplit) {
        /*客户类型*/
        String crmType = assetsSplit.getCrmType();
        /*客户编号*/
        String crmNo = assetsSplit.getCrmNum();
        /*合同号*/
        String contractNo = assetsSplit.getContractNo();
        AccountTransaction accountTransaction = getAccountTransaction(crmType, crmNo, contractNo);
        String transId = accountTransaction.getId();
        /*获取资方的拆分信息*/
        Optional<AssetsSplitItem> optionalAssetsSplitItem = assetsSplitItems.stream().filter((assetsSplitItem) -> (PL0602Enum.PL0602_2_OUTSIDE.getCode()).equals(assetsSplitItem.getLoanType())).findAny();
        List<AccountEntry> accountEntrys = new ArrayList<>();
        if (optionalAssetsSplitItem.isPresent()) {
            AssetsSplitItem assetsSplitItem = optionalAssetsSplitItem.get();
            /*10110101-辅助工行户*/
            AccountEntry accountEntrySilverDeposit = getAccountEntry(transId, assetsSplitItem.getTransAmt());
            accountEntrySilverDeposit.setAccountType(PL1206Enum.PL1206_1_1001.getCode());
            accountEntrySilverDeposit.setAccountId(accountIdConfig.getSilverDeposit());
            accountEntrySilverDeposit.setBalanceDirection(PL1202Enum.PL1202_2_RECHARGE.getCode());
            accountEntrySilverDeposit.setSummary(PL1202Enum.PL1202_2_RECHARGE.getDesc());
            accountEntrySilverDeposit.setAssCheckType(PL1203Enum.PL1203_1_0011.getCode());
            accountEntrySilverDeposit.setAssCheckCode(PL1207Enum.PL1207_1_2315.getCode());
            accountEntrys.add(accountEntrySilverDeposit);
            /*其他应收款/第三方平台*/
            AccountEntry accountEntryOtherReceivables = getAccountEntry(transId, assetsSplitItem.getTransAmt());
            accountEntryOtherReceivables.setAccountType(PL1206Enum.PL1206_2_4000.getCode());
            accountEntryOtherReceivables.setAccountId(accountIdConfig.getOtherReceivables());
            accountEntryOtherReceivables.setBalanceDirection(PL1202Enum.PL1202_2_RECHARGE.getCode());
            accountEntryOtherReceivables.setSummary(PL1202Enum.PL1202_2_RECHARGE.getDesc());
            accountEntryOtherReceivables.setAssCheckType(PL1203Enum.PL1203_2_0019.getCode());
            accountEntryOtherReceivables.setAssCheckCode(PL1207Enum.PL1207_2_5001.getCode());
            accountEntrys.add(accountEntryOtherReceivables);
            commonDaoService.insertAccountTransaction(accountTransaction);
            commonDaoService.insertAccountEntryList(accountEntrys);
        } else {
            logger.error("===============<处理中心>-记账->查询资产拆分资方明细不存在->资产表transId：" + assetsSplit.getId());
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
        }
//        return new AccountRecord(accountTransaction, accountEntrys, "silverDeposit");
        ThreadPool.submit(() -> accountToPgwService.recordToPgwSilverDeposit(accountTransaction, accountEntrys));
    }

    /**
     * saveOtherReceivables
     * 3.资金平台明确放款方式后记账
     *
     * @param
     * @return void
     * @author yu jianwei
     * @date 2018/7/31 17:18
     */
    @Transactional
    public void saveOtherReceivables(List<AssetsSplitItem> assetsSplitItems, AssetsSplit assetsSplit, String projectType, String transNo) {
        /*客户类型*/
        String crmType = assetsSplit.getCrmType();
        /*客户编号*/
        String crmNo = assetsSplit.getCrmNum();
        /*账户类型*/
        String accountType = getAccountTypeByCrmType(crmType);
        /*合同号*/
        String contractNo = assetsSplit.getContractNo();
        /*是否联合放款*/
        AccountTransaction accountTransaction = getAccountTransaction(crmType, crmNo, contractNo);
        accountTransaction.setContextTransNo(transNo);
        commonDaoService.insertAccountTransaction(accountTransaction);
        String transId = accountTransaction.getId();
        List<AccountEntry> accountEntrys = new ArrayList<>();
        assetsSplitItems.forEach((assetsSplitItem) -> {
            AccountEntry accountEntry = getAccountEntry(transId, assetsSplitItem.getTransAmt());
            accountEntry.setBalanceDirection(PL1202Enum.PL1202_1_WITHHOLD.getCode());
            accountEntry.setSummary(PL1202Enum.PL1202_1_WITHHOLD.getDesc());
            if ((PL0602Enum.PL0602_2_OUTSIDE.getCode()).equals(assetsSplitItem.getLoanType()) && projectType.equals(PL0505Enum.PL0505_1_UNION.getCode())) {
                /* 122108-其他应收款/第三方平台（辅助工行）*/
                accountEntry.setAccountType(PL1206Enum.PL1206_2_4000.getCode());
                accountEntry.setAccountId(accountIdConfig.getOtherReceivables());
                accountEntry.setAssCheckType(PL1203Enum.PL1203_2_0019.getCode());
                accountEntry.setAssCheckCode(PL1207Enum.PL1207_2_5001.getCode());
                accountEntrys.add(accountEntry);
                return;
            } else if ((PL0602Enum.PL0602_1_OWN.getCode()).equals(assetsSplitItem.getLoanType())) {
                /* 22020602-应付账款贷款/客户(或者22020601应付账款贷款/商户）*/
                accountEntry.setAccountType(accountType);
                accountEntrys.add(accountEntry);
                return;
            }
        });
        /*  22020604-应付账款贷款/联合合作-（辅助工行）*/
        AccountEntry accountEntry = getAccountEntry(transId, assetsSplit.getTotalAmount());
        accountEntry.setAccountType(PL1206Enum.PL1206_5_4001.getCode());
        accountEntry.setAccountId(accountIdConfig.getTheMiddle());
        accountEntry.setBalanceDirection(PL1202Enum.PL1202_2_RECHARGE.getCode());
        accountEntry.setSummary(PL1202Enum.PL1202_2_RECHARGE.getDesc());
        accountEntrys.add(accountEntry);
        commonDaoService.insertAccountEntryList(accountEntrys);
        ThreadPool.submit(() ->
                accountToPgwService.recordToPgwCust(accountTransaction, accountEntrys)
        );
    }

    /**
     * 2.saveAgencyAccount
     * 应付抹平（由于应付账款贷款，贷方是核算记账不走va,所以要发起抹平）
     *
     * @param
     * @return void
     * @author yu jianwei
     * @date 2018/7/31 17:20
     */
    @Transactional
    public AccountTransaction saveAgencyAccount(AssetsSplit assetsSplit, BigDecimal ammount) {
        /*客户类型*/
        String crmType = assetsSplit.getCrmType();
        /*合同号*/
        String contractNo = assetsSplit.getContractNo();
        /*客户编号*/
        String crmNo = assetsSplit.getCrmNum();
        /*账户类型*/
        String accountType = getAccountTypeByCrmType(crmType);
        AccountTransaction accountTransaction = getAccountTransaction(crmType, crmNo, contractNo);
        String transId = accountTransaction.getId();
        List<AccountEntry> accountEntrys = new ArrayList<>();
        /*机构户*/
        AccountEntry accountEntryAgencyAccount = getAccountEntry(transId, ammount);
        accountEntryAgencyAccount.setAccountType(PL1206Enum.PL1206_6_1000.getCode());
        accountEntryAgencyAccount.setAccountId(accountIdConfig.getAgencyAccount());
        accountEntryAgencyAccount.setBalanceDirection(PL1202Enum.PL1202_1_WITHHOLD.getCode());
        accountEntryAgencyAccount.setSummary(PL1202Enum.PL1202_1_WITHHOLD.getDesc());
        /*22020602-应付账款贷款/客户(或者22020601应付账款贷款/商户）*/
        AccountEntry accountEntryCust = getAccountEntry(transId, ammount);
        accountEntryCust.setAccountType(accountType);
        accountEntryCust.setBalanceDirection(PL1202Enum.PL1202_2_RECHARGE.getCode());
        accountEntryCust.setSummary(PL1202Enum.PL1202_2_RECHARGE.getDesc());
        accountEntrys.add(accountEntryAgencyAccount);
        accountEntrys.add(accountEntryCust);
        commonDaoService.insertAccountTransaction(accountTransaction);
        commonDaoService.insertAccountEntryList(accountEntrys);
        ThreadPool.submit(() -> {
            accountToPgwService.recordToPgwCust(accountTransaction, accountEntrys);
        });
        return accountTransaction;
    }

    /*
     * timingRecordToPgw
     * 定时扫描 未入账和入账失败的事务表发送至支付网关
     * @author yu jianwei
     * @date 2018/8/1 19:27
     * @param []
     * @return void
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    @Transactional
    public void timingRecordToPgw() {
        logger.info("===============<处理中心>-记账->定时扫描<未入账>开始======");
        /*未入账*/
        List<AccountTransaction> accountTransactions = commonDaoService.selectByAccountingStatus(PL1102Enum.PL1102_1_NORECORDED.getCode());
        accountTransactions.forEach((accountTransaction) -> {
            String transactionId = accountTransaction.getId();
            List<AccountEntry> accountEntries = commonDaoService.selectAccountEntryByTransId(transactionId);
//            ThreadPool.submit(() -> {
            accountToPgwService.recordToPgwCust(accountTransaction, accountEntries);
//            });
        });
        logger.info("===============<处理中心>-记账->定时扫描<入账失败>开始======");
        /*入账失败*/
        List<AccountTransaction> accountTransactionList = commonDaoService.selectByAccountingStatus(PL1102Enum.PL1102_3_FAIL.getCode());
        accountTransactionList.forEach((accountTransaction) -> {
            String transactionId = accountTransaction.getId();
            List<AccountEntry> accountEntries = commonDaoService.selectAccountEntryByTransId(transactionId);
//            ThreadPool.submit(() ->
            accountToPgwService.recordToPgwSilverDeposit(accountTransaction, accountEntries)/*)*/;
        });
    }

    /*
     * getAccountTypeByCrmType
     * 根据客户类型获取账户类型
     *
     * @param
     * @return java.lang.String
     * @author yu jianwei
     * @date 2018/7/31 17:31
     */
    private String getAccountTypeByCrmType(String crmType) {
        if (PL1301Enum.PL1301_1_STORE.getCode().equals(crmType) || PL1301Enum.PL1301_2_MERCHANT.getCode().equals(crmType)) {
            return PL1206Enum.PL1206_4_1002.getCode();
        }
        if (PL1301Enum.PL1301_3_PERSONAL.getCode().equals(crmType)) {
            return PL1206Enum.PL1206_3_1004.getCode();
        } else {
            logger.error("===============<处理中心>-记账->该客户类型匹配不到账户类型crmType：" + crmType);
            throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
        }
    }


    /*
     * getAccountTransaction
     * 获取事务表
     * @author yu jianwei
     * @date 2018/8/1 10:13
     * @param [transNo, orderId, tradeLink]  事物号，系统跟踪号，交易环节
     * @return com.haiercash.pluslink.capital.data.AccountTransaction
     */
    private AccountTransaction getAccountTransaction(String crmType, String crmNo, String contractNo) {
        AccountTransaction accountTransaction = new AccountTransaction();
        String sequence = commonDaoService.getSequence(SerialNoEnum.PL_ACCOUNT_TRANSACTION.getSeqName());
        logger.info("===============<处理中心>-记账->生成事务表序列======完毕sequence：" + sequence);
        accountTransaction.setId((SequenceUtil.getSequence(SerialNoEnum.PL_ACCOUNT_TRANSACTION.getTypeName(), sequence)));
        accountTransaction.setAppId(PL1101Enum.PL1101_1_PL.getCode());
        accountTransaction.setBizType(PL1205Enum.PL1205_17_PLR_CARD_TRANSFER.getCode());
        accountTransaction.setIsBounced(PL0105Enum.PL0105_2_NO.getCode());
        accountTransaction.setAccountingStatus(PL1102Enum.PL1102_1_NORECORDED.getCode());
        accountTransaction.setTransNo(SequenceUtil.getTransNo(SystemFlagEnum.PLCPS.getSystemFlag(), sequence));
        accountTransaction.setOrderId(RestUtils.getGuid());
        accountTransaction.setTradeLink(tradeLink);
        accountTransaction.setCrmType(crmType);
        accountTransaction.setCrmNo(crmNo);
        accountTransaction.setBizId(contractNo);
        accountTransaction.setCreateBy(CommonConstant.SYSTEM_OPER);
        Date date = new Date();
        accountTransaction.setRequestTime(date);
        accountTransaction.setOperatorId(CommonConstant.SYSTEM_OPER);
        accountTransaction.setContractNo(contractNo);
        accountTransaction.setCreateDate(date);
        accountTransaction.setUpdateDate(date);
        return accountTransaction;
    }

    /*
     * getAccountEntry
     * 获取分录表
     *
     * @param
     * @return com.haiercash.pluslink.capital.data.AccountEntry
     * @author yu jianwei
     * @date 2018/7/30 13:36
     */
    private AccountEntry getAccountEntry(String transId, BigDecimal amount) {
        AccountEntry accountEntry = new AccountEntry();
        String sequence = commonDaoService.getSequence(SerialNoEnum.PL_ACCOUNT_ENTRY.getSeqName());
        logger.info("===============<处理中心>-记账->生成分录表表序完毕======sequence：" + sequence);
        accountEntry.setId((SequenceUtil.getSequence(SerialNoEnum.PL_ACCOUNT_ENTRY.getTypeName(), sequence)));
        accountEntry.setTransId(transId);
        accountEntry.setAmount(amount);
        accountEntry.setIsControl(PL0105Enum.PL0105_2_NO.getCode());
        accountEntry.setVoucherNo(voucherNo);
        accountEntry.setIsCorrect(PL0105Enum.PL0105_2_NO.getCode());
        accountEntry.setCreateBy(CommonConstant.SYSTEM_OPER);
        Date date = new Date();
        accountEntry.setCreateDate(date);
        accountEntry.setUpdateDate(date);
        accountEntry.setTradeFinishTime(date);
        return accountEntry;
    }
}
