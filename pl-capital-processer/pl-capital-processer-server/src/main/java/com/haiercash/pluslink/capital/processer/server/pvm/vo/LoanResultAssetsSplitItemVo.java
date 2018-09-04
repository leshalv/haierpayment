package com.haiercash.pluslink.capital.processer.server.pvm.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzh
 * @Title: LoanAssetsSplitItemResponse 放款结果放款明细vo
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1715:50
 */
@Getter
@Setter
//放款结果放款明细vo
public class LoanResultAssetsSplitItemVo implements Serializable {

    //借据号
    private String loanNo;

    //借据类型
    private String loanType;

    //合作项目Id
    private String projectId;

    //资方放款占比
    private String agencyRate;

    //申请金额
    private BigDecimal applAmt;

    //放款金额
    private BigDecimal transAmt;

    //明细状态
    private String status;

    //资方编号
    private String agencyId;

    //资方业务编号
    private String capLoanNo;
}
