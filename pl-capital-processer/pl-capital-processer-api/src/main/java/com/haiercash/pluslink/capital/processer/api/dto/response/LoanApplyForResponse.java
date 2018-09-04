package com.haiercash.pluslink.capital.processer.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author xiaobin
 * @create 2018-07-05 下午7:18
 **/
@Getter
@Setter
@ApiModel("放款处理接口返回报文体")
public class LoanApplyForResponse implements Serializable {

    @ApiModelProperty("报文流水号")
    private String serNo;

    //放款状态00
    @ApiModelProperty("放款状态")
    private String propertyType;

}
