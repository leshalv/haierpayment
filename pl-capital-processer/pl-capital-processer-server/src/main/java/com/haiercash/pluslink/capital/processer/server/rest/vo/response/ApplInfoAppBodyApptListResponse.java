package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzh
 * @Title: RiskInfoBodyResponse appt的list信息
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplInfoAppBodyApptListResponse implements Serializable {

    //Appt所需信息列表
    private List<ApplInfoAppApptResponse> appt;

}
