package com.haiercash.pluslink.capital.processer.server.rest.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 前置 rest vo
 * @author xiaobin
 * @create 2018-07-23 下午4:28
 **/
@Setter
@Getter
public class PrepositionResponseRestVo {

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField1;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField2;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField3;
}
