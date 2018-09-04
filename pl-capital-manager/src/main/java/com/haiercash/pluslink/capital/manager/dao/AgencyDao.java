package com.haiercash.pluslink.capital.manager.dao;

import com.haiercash.pluslink.capital.data.CooperationAgency;
import org.springframework.stereotype.Repository;

/**
 * > 合作机构
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/14 13:53
 */
@Repository
public interface AgencyDao {
    int insert(CooperationAgency cooperationAgency);
    int update(CooperationAgency cooperationAgency);
}
