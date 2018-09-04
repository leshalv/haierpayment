package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.data.Area;
import com.haiercash.pluslink.capital.processer.server.dao.AreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaobin
 * @create 2018-07-19 下午1:55
 **/
@Service
@Transactional
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    public Area get(String id) {
        return areaDao.selectById(id);
    }
}
