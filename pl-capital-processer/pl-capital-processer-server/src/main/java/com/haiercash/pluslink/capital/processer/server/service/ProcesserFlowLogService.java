package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.data.ProcesserFlowLog;
import com.haiercash.pluslink.capital.processer.server.dao.ProcesserFlowLogDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiaobin
 * @create 2018-08-06 下午1:34
 **/
@Service
@Transactional(readOnly = true)
public class ProcesserFlowLogService extends BaseService {

    @Autowired
    private ProcesserFlowLogDao processerFlowLogDao;

    @Transactional
    public void insertProcesserFlowList(List<ProcesserFlowLog> processerFlowLogList) {
        if (CollectionUtils.isEmpty(processerFlowLogList))
            return;

        // processerFlowLogDao.insertProcesserFlowList(processerFlowLogList);
    }

    public int selectByApplSeq(String applSeq) {
        try {
            List<ProcesserFlowLog> flowLogList = processerFlowLogDao.selectByApplSeq(applSeq);
            if (CollectionUtils.isNotEmpty(flowLogList)) {
                return flowLogList.get(0).getFdIndex();
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
