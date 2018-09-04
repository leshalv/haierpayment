package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 额度信息
 *
 * @author xiaobin
 * @create 2018-07-19 上午9:59
 **/
@Getter
@Setter
@Table(name = "PL_QUOTA")
public class Quota extends BaseModel {

    /**
     * 额度编号
     * <p>
     * (如果状态为 2（授信申请中），则无此额度编号）
     * 当额度回调成功后，产生此额度编号
     */
    private String cinoMemno;
    /**
     * 资产拆分明细表主键ID
     */
    private String assetsSplitItemId;
    /**
     * 申请时间
     */
    private String applDate;

    /**
     * 合作机构编号
     */
    private String cooprAgencyId;

    /**
     * 合作机构名称
     */
    private String cooprAgencyName;

    /**
     * 身份证号
     */
    private String certCode;

    /**
     * 客户编号
     */
    private String cooprUserId;

    /**
     * 状态（1：额度回调成功；2：授信申请中，0：无效）
     */
    private String status;

    /**
     * 消息Id（授信申请、授信回调）
     */
    private String orgCorpMsgId;


    /**
     * 额度回调成功
     */
    public static final String BACK_OK = "1";

    /**
     * 授信申请中
     */
    public static final String APPLYING = "2";

    /**
     * 无效、授信失败
     */
    public static final String INVALID = "0";
}
