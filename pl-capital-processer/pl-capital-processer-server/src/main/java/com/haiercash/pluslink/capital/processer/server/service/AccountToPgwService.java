package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.cloud.core.utils.MyBeanUtils;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.data.AccountEntry;
import com.haiercash.pluslink.capital.data.AccountTransaction;
import com.haiercash.pluslink.capital.enums.dictionary.PL0105Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1102Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL1206Enum;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.rest.client.PgwVaClient;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PgwAccountRecordRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PgwQueryByCrmNoRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PgwAccountRecordResponse;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PgwQueryByCrmNoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Auther: yu jianwei
 * @Date: 2018/8/4 17:04
 * @Description:
 */
@Service
public class AccountToPgwService extends BaseService {
    @Autowired
    private CommonDaoService commonDaoService;
    @Autowired
    private PgwVaClient vaClient;

    /*
     * recordToPgwSilverDeposit
     *异步发送至支付网关记账,工行放款成功，工行打款给我司
     * @author yu jianwei
     * @date 2018/8/1 15:09
     * @param [accountTransaction, accountEntrys]
     * @return void
     */
    public void recordToPgwSilverDeposit(AccountTransaction accountTransaction, List<AccountEntry> accountEntrys) {

        logger.info("==============<处理中心>-记账->异步记账信息推送至pgw开始===========合同号:" + accountTransaction.getContractNo() + "事务ID：" + accountTransaction.getId());
        PgwAccountRecordRequest pgwAccountRecordRequest = new PgwAccountRecordRequest();
        MyBeanUtils.copyProperties(accountTransaction, pgwAccountRecordRequest);
        pgwAccountRecordRequest.setEntryList(accountEntrys);
        logger.info("==============<处理中心>-记账->异步资方信息推送至pgw入参：" + JsonUtils.writeObjectToJson(pgwAccountRecordRequest));
        PgwAccountRecordResponse pgwAccountRecordResponse = null;
        try {
            pgwAccountRecordResponse = vaClient.accountRecord(pgwAccountRecordRequest);
            logger.info("==============<处理中心>-记账->异步资方信息推送至pgw响应信息===========" + JsonUtils.writeObjectToJson(pgwAccountRecordResponse));
        } catch (Exception e) {
            accountTransaction.setAccountingStatus(PL1102Enum.PL1102_3_FAIL.getCode());
            accountTransaction.setUpdateDate(new Date());
            commonDaoService.updateAccountingStatuById(accountTransaction);
            logger.info("==============<处理中心>-记账->异步资方信息推送至pgw响应失败，记账状态设置为失败===========");
            return;
        }
        if ((pgwAccountRecordResponse.getRtnCode().equals(CommonReturnCodeEnum.TRADE_SUCCESS.getCode()))) {
            accountTransaction.setAccountingStatus(PL1102Enum.PL1102_2_SUCCESS.getCode());
            accountTransaction.setUpdateDate(new Date());
            commonDaoService.updateAccountingStatuById(accountTransaction);
            return;
        }
        accountTransaction.setAccountingStatus(PL1102Enum.PL1102_3_FAIL.getCode());
        accountTransaction.setUpdateDate(new Date());
        commonDaoService.updateAccountingStatuById(accountTransaction);
    }

    /*
     * recordToPgwCust
     *
     * @author yu jianwei
     * @date 2018/8/1 16:40
     * @param [accountTransaction, accountEntrys]
     * @return void
     */
    public void recordToPgwCust(AccountTransaction accountTransaction, List<AccountEntry> accountEntrys) {
        logger.info("==============<处理中心>-记账->异步更新商户或个人账户开始===========合同号:" + accountTransaction.getContractNo() + "事务ID：" + accountTransaction.getId());
        String crmType = accountTransaction.getCrmType();
        String crmNo = accountTransaction.getCrmNo();
        Optional<AccountEntry> optionalEntry = accountEntrys.stream().filter((accountEntry) -> {
            String accountType = accountEntry.getAccountType();
            logger.info("==============<处理中心>-记账->异步更新商户或个人账户类型accountType：" + accountType);
            return (accountType.equals(PL1206Enum.PL1206_3_1004.getCode()) || accountType.equals(PL1206Enum.PL1206_4_1002.getCode()));
        }).findAny();
        if (optionalEntry.isPresent()) {
            AccountEntry accountEntry = optionalEntry.get();
            if (StringUtils.isEmpty(accountEntry.getAccountId())) {
                PgwQueryByCrmNoRequest pgwQueryByCrmNoRequest = new PgwQueryByCrmNoRequest();
                pgwQueryByCrmNoRequest.setCrmType(crmType);
                pgwQueryByCrmNoRequest.setCrmNo(crmNo);
                pgwQueryByCrmNoRequest.setAccountType(accountEntry.getAccountType());
                pgwQueryByCrmNoRequest.setIsOpen(PL0105Enum.PL0105_1_YES.getCode());
                logger.info("==============<处理中心>-记账->异步更新商户或个人账户入参：" + JsonUtils.writeObjectToJson(pgwQueryByCrmNoRequest));
                PgwQueryByCrmNoResponse pgwQueryByCrmNoResponse = null;
                try {
                    pgwQueryByCrmNoResponse = vaClient.queryByCrmNoAndType(pgwQueryByCrmNoRequest);
                } catch (Exception e) {
                    logger.info("==============<处理中心>-记账->异步更新商户或个人账户响应失败，任务结束===========");
                    return;
                }
                logger.info("==============<处理中心>-记账->异步更新商户或个人账户响应===========" + JsonUtils.writeObjectToJson(pgwQueryByCrmNoResponse));
                String rtnCode = pgwQueryByCrmNoResponse.getRtnCode();
                if (rtnCode.equals(CommonReturnCodeEnum.TRADE_SUCCESS.getCode())) {
                    PgwQueryByCrmNoResponse.Account account = pgwQueryByCrmNoResponse.getAccount();
                    if (!StringUtils.isEmpty(account)) {
                        accountEntry.setAccountId(account.getId());
                        accountEntry.setUpdateDate(new Date());
                        commonDaoService.updateAccoutIdById(accountEntry);
                        accountEntrys.removeIf((accountEntryOld) -> {
                            String accountType = accountEntryOld.getAccountType();
                            return (accountType.equals(PL1206Enum.PL1206_3_1004.getCode()) || accountType.equals(PL1206Enum.PL1206_4_1002.getCode()));
                        });
                        accountEntrys.add(accountEntry);
                        recordToPgwSilverDeposit(accountTransaction, accountEntrys);
                    }
                    logger.info("==============<处理中心>-记账->异步更新商户或个人账户响应无信息============");
                }
            }
        } else {
            recordToPgwSilverDeposit(accountTransaction, accountEntrys);
        }
    }
}
