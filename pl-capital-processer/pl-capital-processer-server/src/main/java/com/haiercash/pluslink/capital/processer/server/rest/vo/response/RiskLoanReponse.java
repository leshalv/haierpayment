package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author lzh
 * @Title: LoanRequest 调用放款信息的通用返回头
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1820:36
 */
@Getter
@Setter
@ApiModel("放款接口通用返回")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskLoanReponse implements Serializable {
    private RiskLoanSecondResponse response;
}
