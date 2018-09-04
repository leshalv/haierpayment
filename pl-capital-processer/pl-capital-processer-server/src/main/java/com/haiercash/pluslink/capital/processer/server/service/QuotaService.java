package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.cloud.core.utils.IdGen;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.data.Quota;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.processer.server.dao.QuotaDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 额度信息
 *
 * @author xiaobin
 * @create 2018-07-19 上午10:11
 **/
@Service
@Transactional(readOnly = true)
@Slf4j
public class QuotaService extends BaseService {

    @Autowired
    private QuotaDao quotaDao;

    /**
     * 授信回调，插入到额度表
     * <p>
     * 插入额度信息
     *
     * @param quota
     */
    @Transactional
    public void insert(Quota quota) {
        quota.setId(IdGen.uuid());
        quota.setCreateDate(new Date());
        quota.setUpdateDate(new Date());
        quota.setCreateBy("SYSTEM");
        quotaDao.insert(quota);
    }

    /**
     * 根据合作机构编号、身份证号查询额度编号
     *
     * @param cooprAgencyId
     * @param certCode
     * @return
     */
    public Quota selectByAgencyIdAndCertCode(String cooprAgencyId, String certCode) {
        return quotaDao.selectByAgencyIdAndCertCode(cooprAgencyId, certCode, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 根据消息ID查询
     *
     * @param corpMsgId
     * @return
     */
    public Quota selectByCorpMsgId(String corpMsgId) {
        return quotaDao.selectByCorpMsgId(corpMsgId, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 接受授信回调；更新额度信息状态
     */
    @Transactional
    public void updateByCallBackData(Quota quota) {
        log.info(">>>>>>>>>>>>>>>开始更新额度表<<<<<<<<<<<<<<");
        if (StringUtils.isBlank(quota.getCinoMemno()))
            return;
        log.info(">>>>>>>>>>>>>>>授信回调，更新额度信息，额度表主键：{},额度编号：{}", quota.getId(), quota.getCinoMemno());
        if(StringUtils.isBlank(quota.getAssetsSplitItemId())){
            quotaDao.updateByCallBackData(quota.getCinoMemno(), quota.getCooprUserId(), Quota.BACK_OK, quota.getId(),
                    PL0101Enum.PL0101_2_NORMAL.getCode(), new Date());
        }else{
            quotaDao.updateByCallBackDataWithAssetsSplitItemId(quota.getCinoMemno(), quota.getCooprUserId(), Quota.BACK_OK, quota.getId(), quota.getAssetsSplitItemId(),
                    PL0101Enum.PL0101_2_NORMAL.getCode(), new Date());
        }
    }

}
