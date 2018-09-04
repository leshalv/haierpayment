package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzh
 * @Title: RiskInfoBodyResponse
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskInfoBodyResponse implements Serializable {
    //这里我们只查询dataTyp=A506一种,sortNo默认传1所以只返回list1这个报文
    private List<RiskInfoBodyDetailResponse> list1;
}
