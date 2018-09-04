package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class RiskInfoBodyDetailResponse implements Serializable {

    //数据类型
    private String dataTyp;
    //数据内容
    private String content;

}
