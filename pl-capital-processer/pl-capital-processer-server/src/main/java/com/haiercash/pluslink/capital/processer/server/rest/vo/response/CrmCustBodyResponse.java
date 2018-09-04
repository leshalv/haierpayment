package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @create 2018-07-16 下午1:17
 **/
@Getter
@Setter
@ApiModel("查询实名认证客户信息返回体消息")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrmCustBodyResponse implements Serializable {

    @ApiModelProperty("客户编号")
    private String custNo;

}
