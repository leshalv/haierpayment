package com.haiercash.pluslink.capital.manager.service;

import com.haiercash.pluslink.capital.manager.data.AgencyType;
import com.haiercash.pluslink.capital.manager.data.AgencyTypeDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * > 机构类别service
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 15:46
 */
@Service
public interface AgencyTypeService {
    List<AgencyType> getAgencyTypeList();
    Integer addAgencyType(AgencyType agencyType, Map<String, Object> operator);
    Integer updateAgencyType(AgencyType agencyType, Map<String, Object> operator);
    Integer delAgencyType(List<AgencyType> agencyTypes, Map<String, Object> operator);
    List<AgencyTypeDetail> getAgencyTypeDetail(String agencyType);
}
