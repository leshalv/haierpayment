package com.haiercash.pluslink.capital.processer.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author xiaobin
 * @create 2018-07-16 下午1:16
 **/
@Getter
@Setter
@ApiModel("放款查询请求信息")
public class LoanSearchRequest implements Serializable {

    @ApiModelProperty("支付请求号")
    private String applSeq;

    @ApiModelProperty("合同号")
    private String contractNo;

}
