package com.haiercash.pluslink.capital.processer.server.task.impl;

import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.vo.LoanResultVo;
import com.haiercash.pluslink.capital.processer.server.service.AccountService;
import com.haiercash.pluslink.capital.processer.server.task.IJobExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 记账
 *
 * @author xiaobin
 * @create 2018-08-11 下午5:03
 **/
@Slf4j
public class AccountRecordTask implements IJobExecutor, Serializable {

    public static final String MODEL_NAME = "com.haiercash.pluslink.capital.processer.server.task.impl.AccountRecordTask";

    private void init() {
        accountService = SpringContextHolder.getBean(AccountService.class);
    }


    @Override
    public boolean execute(String jobContext) {
        init();
        LoanResultVo loanResultVo = JsonUtils.readObjectByJson(jobContext, LoanResultVo.class);
        if (loanResultVo != null) {
            try {
                accountService.accountRecord(loanResultVo.getContractNo());
            } catch (Exception ex) {
                log.error("记账发生错误：", ex);
            }
        }
        return true;
    }

    private AccountService accountService;
}
