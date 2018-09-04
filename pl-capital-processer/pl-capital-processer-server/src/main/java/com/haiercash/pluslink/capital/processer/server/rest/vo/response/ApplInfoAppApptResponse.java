package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lzh
 * @Title: ApplInfoAppApptResponse appt信息
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplInfoAppApptResponse implements Serializable {

    //("申请人类型")
    @JsonProperty("appt_typ")
    private String apptTyp;

    //("性别")
    @JsonProperty("appt_indiv_sex")
    private String apptIndivSex;

    //("出生日期")
    @JsonProperty("appt_start_date")
    private String apptStartDate;

    //("职务")
    @JsonProperty("indiv_position")
    private String indivPosition;

    //("单位性质")
    @JsonProperty("indiv_emp_typ")
    private String indivEmpTyp;

    //("个人年收入")
    @JsonProperty("annual_earn")
    private BigDecimal annualEarn;

    //("教育程度")
    @JsonProperty("indiv_edu")
    private String indivEdu;

    //("婚姻")
    @JsonProperty("indiv_marital")
    private String indivMarital;

    //"家庭地址")
    @JsonProperty("live_addr")
    private String liveAddr;

    //("单位详情地址")
    @JsonProperty("empaddr")
    private String empAddr;

    //联系人信息
    @JsonProperty("relList")
    private ApplInfoAppRelListResponse relList;

    //("省")
    @JsonProperty("live_province")
    private String liveProvince;

    //("市")
    @JsonProperty("live_city")
    private String liveCity;

    //("区")
    @JsonProperty("live_area")
    private String liveArea;

    //("邮政编码")
    @JsonProperty("indiv_emp_zip")
    private String indivEmpZip;

    //("配偶姓名")
    @JsonProperty("spouse_name")
    private String spouseName;

    //("配偶手机号")
    @JsonProperty("spouse_mobile")
    private String spouseMobile;

}
