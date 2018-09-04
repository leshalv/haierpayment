package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzh
 * @Title: ApplInfoAppRelListResponse 联系人信息列表
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplInfoAppRelListResponse implements Serializable {

    private List<ApplInfoAppRelResponse> rel;

}
