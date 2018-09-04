package com.haiercash.pluslink.capital.processer.server.pvm.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaobin
 * @create 2018-07-16 下午1:17
 **/
@Getter
@Setter
//放款结果mq推送vo
public class LoanResultVo implements Serializable {

    //支付请求号
    private String applSeq;

    //合同号
    private String  contractNo;

    //是否联合放款
    private String projectType;

    //产品是否支持买断
    private String prodBuyOut;

    //放款状态
    private String loanStatus;

    //资产拆分明细表信息
    private List<LoanResultAssetsSplitItemVo> items;

}
