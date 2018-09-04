package com.haiercash.pluslink.capital.manager.service.impl;

import com.haiercash.pluslink.capital.common.mybatis.dao.BaseCommonDao;
import com.haiercash.pluslink.capital.data.LogInfo;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.manager.dao.LogInfoDao;
import com.haiercash.pluslink.capital.manager.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * >
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/08/07 18:05
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class LogInfoServiceImpl implements LogInfoService{
    @Autowired
    private LogInfoDao logInfoDao;
    @Autowired
    private BaseCommonDao baseCommonDao;

    /**
     * log日志处理
     * @param logName   [模块名称]
     * @param businessType  [操作类型]
     * @param operator  [操作人]
     * @param logContent    [log内容]
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertLog(String logName, String businessType, String operator, String logContent) {
        LogInfo logInfo = new LogInfo();
        logInfo.setId(baseCommonDao.getUUID());
        logInfo.setLogName(logName);
        logInfo.setBusinessType(businessType);
        logInfo.setCreateBy(operator);
        logInfo.setLogContent(logContent);
        logInfo.setCreateDate(new Date());
        logInfo.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        logInfoDao.insert(logInfo);
    }
}
