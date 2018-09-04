package com.haiercash.pluslink.capital.manager.service;

import com.haiercash.pluslink.capital.data.BankInfo;
import com.haiercash.pluslink.capital.data.CooperationProject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * >
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/18 10:13
 */
@Service
public interface ProjectService {
    List<BankInfo> getBankList();
    int addProject(CooperationProject cooperationProject, Map<String, Object> operator);
    int updateProject(CooperationProject cooperationProject, Map<String, Object> operator);
    int delProject(List<CooperationProject> projects, Map<String, Object> operator);
}
