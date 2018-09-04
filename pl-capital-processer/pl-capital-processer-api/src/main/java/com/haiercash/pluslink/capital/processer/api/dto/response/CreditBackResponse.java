package com.haiercash.pluslink.capital.processer.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzh授信回调返回体
 * @create 2018-07-16 下午1:17
 **/
@Getter
@Setter
@ApiModel("授信回调返回体")
public class CreditBackResponse implements Serializable {

    @ApiModelProperty("消息ID")
    private String corpMsgId;

}
