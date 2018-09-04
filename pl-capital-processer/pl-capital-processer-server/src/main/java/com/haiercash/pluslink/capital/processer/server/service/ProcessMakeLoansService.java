package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.data.ProcessMakeLoans;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.processer.server.dao.ProcessMakeLoansDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 放款记录
 *
 * @author xiaobin
 * @create 2018-07-25 下午3:39
 **/
@Service
@Transactional
public class ProcessMakeLoansService extends BaseService {

    @Autowired
    private ProcessMakeLoansDao processMakeLoansDao;

    public void insert(ProcessMakeLoans processMakeLoans) {
        processMakeLoans.setCreateDate(new Date());
        processMakeLoans.setUpdateDate(new Date());
        processMakeLoans.setCreateBy("SYSTEM");
        processMakeLoansDao.insert(processMakeLoans);
    }

    @Transactional(readOnly = true)
    public ProcessMakeLoans selectByApplSeq(String applSeq) {
        return processMakeLoansDao.selectByApplSeq(applSeq,PL0101Enum.PL0101_2_NORMAL.getCode());
    }
}
