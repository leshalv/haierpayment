package com.haiercash.pluslink.capital.manager.service.impl;


import com.haiercash.pluslink.capital.manager.dao.SequenceDao;
import com.haiercash.pluslink.capital.manager.service.ISequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SequenceService
 *
 * @author zhanggaowei
 * @date 2018/5/25
 */
@Service
public class SequenceServiceImpl implements ISequenceService {
    @Autowired
    private SequenceDao sequenceDao;

    /**
     * 获取导入请求批次的序列号
     */
    @Override
    public String getImMsgRequestBatchSequence() {
        return sequenceDao.getImMsgRequestBatchSequence();
    }
}
