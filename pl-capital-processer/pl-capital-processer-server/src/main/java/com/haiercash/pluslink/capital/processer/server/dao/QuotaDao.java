package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.Quota;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 额度信息
 *
 * @author xiaobin
 * @create 2018-07-19 上午10:03
 **/
@Repository
public interface QuotaDao extends BaseMapper<Quota> {

    /**
     * 根据合作机构编号、身份证号查询额度编号
     *
     * @param cooprAgencyId 合作机构编号
     * @param certCode      身份证号
     * @return
     */
    Quota selectByAgencyIdAndCertCode(@Param("cooprAgencyId") String cooprAgencyId, @Param("certCode") String certCode, @Param("delFlag") String delFlag);


    Quota selectByCorpMsgId(@Param("corpMsgId") String corpMsgId, @Param("delFlag") String delFlag);

    /**
     * 接受授信回调；更新额度信息状态
     *
     * @param cinoMemno
     * @param status
     * @param id
     */
    void updateByCallBackDataWithAssetsSplitItemId(@Param("cinoMemno") String cinoMemno, @Param("cooprUserId") String cooprUserId, @Param("status") String status, @Param("id") String id,
                              @Param("assetsSplitItemId") String assetsSplitItemId, @Param("delFlag") String delFlag, @Param("updateDate") Date updateDate);

    void updateByCallBackData(@Param("cinoMemno") String cinoMemno, @Param("cooprUserId") String cooprUserId, @Param("status") String status, @Param("id") String id,
                              @Param("delFlag") String delFlag, @Param("updateDate") Date updateDate);

    /**
     * 根据原消息ID查询授信状态
     *
     * @param orgCorpMsgId [原消息ID]
     * @return [授信状态]
     */
    String getCreditStatus(@Param("orgCorpMsgId") String orgCorpMsgId, @Param("delFlag") String delFlag);

}
