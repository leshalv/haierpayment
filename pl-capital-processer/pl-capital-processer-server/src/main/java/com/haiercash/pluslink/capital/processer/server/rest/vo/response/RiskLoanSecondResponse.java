package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh  放贷调用其他接口返回体第二层信息
 * @create 2018-07-16 下午1:16
 **/
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskLoanSecondResponse implements Serializable {
    //请求头
    private HeadResponse head;

    //请求体
    private RiskInfoBodyResponse body;
}
