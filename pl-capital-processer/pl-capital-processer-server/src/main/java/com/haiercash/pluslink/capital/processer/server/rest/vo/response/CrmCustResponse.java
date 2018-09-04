package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * @author lzh
 * @create 2018-07-16 下午1:17
 **/
@Getter
@Setter
@ApiModel("查询实名认证客户信息返回消息")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrmCustResponse implements Serializable {

    //请求头
    private HeadResponse head;

    //请求体
    private CrmCustBodyResponse body;


}
