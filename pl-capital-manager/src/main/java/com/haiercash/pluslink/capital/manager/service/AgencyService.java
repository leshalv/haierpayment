package com.haiercash.pluslink.capital.manager.service;

import com.haiercash.pluslink.capital.data.CooperationAgency;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * > 合作机构
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/14 16:03
 */
public interface AgencyService {
    int addAgency(CooperationAgency cooperationAgency, Map<String, Object> operator);
    int updateAgency(CooperationAgency cooperationAgency, Map<String, Object> operator);
    int delAgency(List<CooperationAgency> agencys, Map<String, Object> operator);
}
