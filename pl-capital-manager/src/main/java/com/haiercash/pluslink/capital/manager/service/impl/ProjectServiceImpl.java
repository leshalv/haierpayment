package com.haiercash.pluslink.capital.manager.service.impl;

import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.common.mybatis.dao.BaseCommonDao;
import com.haiercash.pluslink.capital.data.BankInfo;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0104Enum;
import com.haiercash.pluslink.capital.manager.dao.BankInfoDao;
import com.haiercash.pluslink.capital.manager.dao.LogInfoDao;
import com.haiercash.pluslink.capital.manager.dao.ProjectDao;
import com.haiercash.pluslink.capital.manager.service.LogInfoService;
import com.haiercash.pluslink.capital.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * > 合作项目Service实现
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/18 10:14
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class ProjectServiceImpl implements ProjectService {
    private static final String LOG_NAME = "合作项目配置";
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private BaseCommonDao baseCommonDao;
    @Autowired
    private BankInfoDao bankInfoDao;
    @Autowired
    private LogInfoService logInfoService;

    @Override
    public List<BankInfo> getBankList() {
        return bankInfoDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addProject(CooperationProject cooperationProject, Map<String, Object> operator) {
        //TODO 创建人
        cooperationProject.setCreateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
        cooperationProject.setCreateDate(new Date());
        cooperationProject.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        cooperationProject.setId(baseCommonDao.getUUID());
        int num = projectDao.insert(cooperationProject);
        //TODO 操作人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_1_INSERT.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "新增合作项目ID:" + cooperationProject.getId());
        return num;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateProject(CooperationProject cooperationProject, Map<String, Object> operator) {
        //TODO 修改人
        cooperationProject.setUpdateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
        cooperationProject.setUpdateDate(new Date());
        int num = projectDao.updateNotNullByPrimaryKey(cooperationProject);
        //TODO 操作人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_2_UPDTE.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "修改合作项目ID:" + cooperationProject.getId());
        return num;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delProject(List<CooperationProject> projects, Map<String, Object> operator) {
        List<String> idList = new ArrayList<>();
        int num = 0;
        for (CooperationProject cooperationProject : projects) {
            idList.add(cooperationProject.getId());
            //TODO 操作人
            cooperationProject.setUpdateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
            cooperationProject.setUpdateDate(new Date());
            cooperationProject.setDelFlag(PL0101Enum.PL0101_1_DEL.getCode());
            num += projectDao.updateNotNullByPrimaryKey(cooperationProject);
        }
        //TODO 操作人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_3_DELETE.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "删除合作项目ID集合:" + JsonUtils.safeObjectToJson(idList));
        return num;
    }
}
