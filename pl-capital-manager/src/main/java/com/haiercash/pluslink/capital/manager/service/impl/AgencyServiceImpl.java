package com.haiercash.pluslink.capital.manager.service.impl;

import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.common.mybatis.dao.BaseCommonDao;
import com.haiercash.pluslink.capital.data.CooperationAgency;
import com.haiercash.pluslink.capital.data.LogInfo;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0104Enum;
import com.haiercash.pluslink.capital.manager.dao.AgencyDao;
import com.haiercash.pluslink.capital.manager.dao.LogInfoDao;
import com.haiercash.pluslink.capital.manager.service.AgencyService;
import com.haiercash.pluslink.capital.manager.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * > 合作机构
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/14 16:04
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class AgencyServiceImpl implements AgencyService {
    private static final String LOG_NAME = "合作机构配置";
    @Autowired
    private AgencyDao agencyDao;
    @Autowired
    private LogInfoService logInfoService;
    @Autowired
    private BaseCommonDao baseCommonDao;
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addAgency(CooperationAgency cooperationAgency, Map<String, Object> operator) {
        //TODO 创建人
        cooperationAgency.setCreateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
        cooperationAgency.setCreateDate(new Date());
        cooperationAgency.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        int num = agencyDao.insert(cooperationAgency);
        //TODO 创建人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_1_INSERT.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "新增合作机构ID:" + cooperationAgency.getId());
        return num;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateAgency(CooperationAgency cooperationAgency, Map<String, Object> operator) {
        //TODO 创建人
        cooperationAgency.setUpdateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
        cooperationAgency.setUpdateDate(new Date());
        cooperationAgency.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        int num = agencyDao.update(cooperationAgency);
        //TODO 创建人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_2_UPDTE.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "修改合作机构ID:" + cooperationAgency.getId());
        return num;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delAgency(List<CooperationAgency> agencys, Map<String, Object> operator) {
        List<String> idList = new ArrayList<>();
        int num = 0;
        for (CooperationAgency cooperationAgency : agencys) {
            idList.add(cooperationAgency.getId());
            //TODO 创建人
            cooperationAgency.setUpdateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
            cooperationAgency.setUpdateDate(new Date());
            cooperationAgency.setDelFlag(PL0101Enum.PL0101_1_DEL.getCode());
            num += agencyDao.update(cooperationAgency);
        }
        //TODO 创建人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_3_DELETE.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "删除合作机构ID集合:" + JsonUtils.safeObjectToJson(idList));
        return num;
    }


}
