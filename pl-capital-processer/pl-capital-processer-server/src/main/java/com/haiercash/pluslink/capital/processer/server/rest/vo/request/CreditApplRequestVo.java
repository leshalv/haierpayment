package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 授信申请
 *
 * @author xiaobin
 * @create 2018-07-17 上午11:30
 **/
@Getter
@Setter
public class CreditApplRequestVo {

    /**
     * 申请人姓名
     */
    @NotBlank
    private String appName;

    /**
     * 客户Id
     */
    private String cooprUserId;

    /**
     * 证件号码
     */
    @NotBlank
    private String certCode;

    /**
     * 银行卡号
     */
    @NotBlank
    private String cardNo;

    /**
     * 手机号
     */
    @NotBlank
    private String mvblno;

    /**
     * 证件有效期
     */
    private String certCodeValidDt;

    /**
     * 性别
     */
    @NotBlank
    private String sex;

    /**
     * 出生日期
     */
    @NotBlank
    private String birthdate;

    /**
     * 工作开始日期
     */
    private String empStartDate;

    /**
     * 职务
     */
    @NotBlank
    private String duty;

    /**
     * 单位性质
     */
    @NotBlank
    private String compNature;

    /**
     * 个人年收入（16，3）
     */
    @NotNull
    private BigDecimal incY;

    /**
     * 家庭年收入（16，3）
     */
    private BigDecimal famY;

    /**
     * 教育程度
     */
    @NotBlank
    private String edu;

    /**
     * 婚姻
     */
    @NotBlank
    private String marry;

    /**
     * 供养人数（可以为空）
     */
    private Integer noDepm;

    /**
     * 省(名称）
     */
    @NotBlank
    private String province;

    /**
     * 市
     */
    @NotBlank
    private String city;

    /**
     * 区（县）
     */
    @NotBlank
    private String county;

    /**
     * 家庭详细地址
     */
    @NotBlank
    private String homeAddress;

    /**
     * 单位详细地址
     */
    @NotBlank
    private String compAddress;

    /**
     * 申请人邮箱(可以为空)
     */
    private String appEmail;

    /**
     * 邮政编号
     */
    @NotBlank
    private String postCde;

    /**
     * 固话区号
     */
    private String areaCde;

    /**
     * 固定电话--------------------------
     */
    private String noBody;

    /**
     * 申请人IP地址
     */
    @NotBlank
    private String addrIp;

    /**
     * 申请人设备编号
     */
    private String equipNo;

    /**
     * 联系人1电话
     */
    @NotBlank
    private String contactATel;

    /**
     * 联系人1姓名
     */
    @NotBlank
    private String contactAName;

    /**
     * 联系人2手机
     */
    private String contactBTel;

    /**
     * 联系人2姓名
     */
    private String contactBName;

    /**
     * 拆分后金额
     * 合作方建议授信金额（核心授信额度）
     */
    @NotNull
    private BigDecimal promAmt;

    /**
     * 总金额
     * 申请人期望金额（核心授信额度）
     */
    private BigDecimal promAmtCust;

    /**
     * 认证方式
     *//*
    private String certType;*/

    /**
     * 消息ID
     */
    private String corpMsgId;

}
