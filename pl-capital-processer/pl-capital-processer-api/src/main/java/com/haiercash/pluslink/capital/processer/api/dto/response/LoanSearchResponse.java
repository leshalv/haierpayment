package com.haiercash.pluslink.capital.processer.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaobin
 * @create 2018-07-16 下午1:17
 **/
@Getter
@Setter
@ApiModel("返回查询放款报文体")
public class LoanSearchResponse implements Serializable {

    @ApiModelProperty("支付请求号")
    private String applSeq;

    @ApiModelProperty("交易流水号")
    private String serNo;

    @ApiModelProperty("是否联合放款")
    private String projectType;

    @ApiModelProperty("产品是否支持买断")
    private String prodBuyOut;
    
    @ApiModelProperty("放款状态")
    private String loanStatus;

    @ApiModelProperty("合同号")
    private String  contractNo;

    //资产拆分明细表信息
    private List<LoanAssetsSplitItemResponse> items;

}
