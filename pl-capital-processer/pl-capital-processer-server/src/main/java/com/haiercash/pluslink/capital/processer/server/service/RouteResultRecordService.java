package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.data.RouteResultRecord;
import com.haiercash.pluslink.capital.processer.server.dao.RouteResultRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 路由结果记录表service
 *
 * @create 2018-07-19 下午1:55
 **/
@Service
@Transactional
public class RouteResultRecordService {

    @Autowired
    private RouteResultRecordDao routeResultRecordDao;

    //根据业务编号查询路由结果中的客户信息
    String selectCustIdByApplSeq(String applSeq) {
        RouteResultRecord routeResultRecord = null;
        try {
            routeResultRecord = routeResultRecordDao.selectByApplSeq(applSeq);
        } catch (Exception e) {
            return null;
        }
        if (routeResultRecord == null) {
            return null;
        }
        return routeResultRecord.getCustId();
    }
}
