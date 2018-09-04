package com.haiercash.pluslink.capital.manager.service.impl;

import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.DictionarySub;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0104Enum;
import com.haiercash.pluslink.capital.manager.dao.AgencyTypeDao;
import com.haiercash.pluslink.capital.manager.dao.LogInfoDao;
import com.haiercash.pluslink.capital.manager.data.AgencyType;
import com.haiercash.pluslink.capital.manager.data.AgencyTypeDetail;
import com.haiercash.pluslink.capital.manager.service.AgencyTypeService;
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
 * > 机构类别
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 15:49
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class AgencyTypeServiceImpl implements AgencyTypeService {
    private static final String LOG_NAME = "机构类别配置";
    @Autowired
    private AgencyTypeDao agencyTypeDao;
    @Autowired
    private LogInfoService logInfoService;
    @Override
    public List<AgencyType> getAgencyTypeList() {
        return agencyTypeDao.getAgencyTypeList(CommonConstant.DICTIONARY_PL0202, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer addAgencyType(AgencyType agencyType, Map<String, Object> operator) {
        int count;
        String newAgencyTypeId;
        int i = 1;
        do {
            String maxAgencyTypeId = agencyTypeDao.getMaxAgencyTypeId(CommonConstant.DICTIONARY_PL0202, PL0101Enum.PL0101_2_NORMAL.getCode());
            int agencyTypeIdNum = 0;
            if (maxAgencyTypeId != null && !maxAgencyTypeId.isEmpty())  {
                agencyTypeIdNum = Integer.valueOf(maxAgencyTypeId.trim().substring(3));
            }
            if (agencyTypeIdNum < 10) {
                newAgencyTypeId = "ORG0" + (agencyTypeIdNum + i);
            } else {
                newAgencyTypeId = "ORG" + (agencyTypeIdNum + i);
            }
            i++;
            count = agencyTypeDao.getAgencyTypeId(CommonConstant.DICTIONARY_PL0202, newAgencyTypeId, PL0101Enum.PL0101_2_NORMAL.getCode());
        } while (count != 0);
        DictionarySub dictionarySub = new DictionarySub();
        dictionarySub.setDictionaryId(CommonConstant.DICTIONARY_PL0202);
        dictionarySub.setSubValue(newAgencyTypeId);
        dictionarySub.setSubName(agencyType.getAgencyTypeName());
        dictionarySub.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        //TODO 创建者
        dictionarySub.setCreateBy(operator != null ? (String) operator.get("userId") : "没有获取到用户信息");
        dictionarySub.setCreateDate(new Date());
        Integer num = agencyTypeDao.insert(dictionarySub);
        //TODO 操作人
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_1_INSERT.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "新增机构类别ID:" + dictionarySub.getSubValue());
        return num;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer updateAgencyType(AgencyType agencyType, Map<String, Object> operator) {
        agencyType.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        Integer num = agencyTypeDao.update(agencyType);
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_2_UPDTE.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "修改机构类别ID:" + agencyType.getAgencyTypeId());
        return num;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer delAgencyType(List<AgencyType> agencyTypes, Map<String, Object> operator) {
        List<String> idList = new ArrayList<>();
        int num = 0;
        for (AgencyType agencyType: agencyTypes) {
            idList.add(agencyType.getAgencyTypeId());
            agencyType.setDelFlag(PL0101Enum.PL0101_1_DEL.getCode());
            num += agencyTypeDao.update(agencyType);
        }
        logInfoService.insertLog(
                LOG_NAME, PL0104Enum.PL0104_3_DELETE.getCode(),
                operator != null ? (String) operator.get("userId") : "没有获取到用户信息",
                "删除机构类别ID集合:" + JsonUtils.safeObjectToJson(idList));
        return num;
    }

    @Override
    public List<AgencyTypeDetail> getAgencyTypeDetail(String agencyType) {
        return agencyTypeDao.getAgencyTypeDetail(PL0101Enum.PL0101_2_NORMAL.getCode(), agencyType);
    }
}
