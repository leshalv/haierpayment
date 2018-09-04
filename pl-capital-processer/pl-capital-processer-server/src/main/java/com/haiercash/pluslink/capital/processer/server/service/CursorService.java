package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.data.Cursor;
import com.haiercash.pluslink.capital.processer.server.dao.CursorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * > 临时表Service
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/23 13:14
 */
@Service
@Transactional(readOnly = true)
public class CursorService extends BaseService {

    @Autowired
    private CursorDao cursorDao;

    @Transactional
    public void deleteByContractNo(String contractNo) {
        cursorDao.deleteByContractNo(contractNo);
    }

    @Transactional
    public int insert(Cursor cursor) {
        return cursorDao.insert(cursor);
    }

}
