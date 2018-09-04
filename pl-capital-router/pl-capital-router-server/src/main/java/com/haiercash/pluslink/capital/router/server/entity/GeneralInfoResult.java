package com.haiercash.pluslink.capital.router.server.entity;

import com.haiercash.pluslink.capital.data.CooperationPeriod;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.data.RepaymentInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 资金方综合查询接口(出参参数)
 * @author WDY
 * @date 2018-07-16
 * @rmk
 */
@Data
@Getter
@Setter
public class GeneralInfoResult implements Serializable{

    /**合作项目信息**/
    CooperationProject project;
    /**还款方式**/
    List<RepaymentInfo> repayList;
    /**支持期限**/
    List<CooperationPeriod> periodList;

}
