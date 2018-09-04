package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: ApplInfoAppRelResponse 联系人信息
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplInfoAppRelResponse implements Serializable {

    //("联系人手机")
    @JsonProperty("rel_mobile")
    private String relMobile;

    //("联系人姓名 ")
    @JsonProperty("rel_name")
    private String relName;

}
