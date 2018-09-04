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
 * @Title: LoanRequest 放款详情的通用返回体
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1820:36
 */
@Getter
@Setter
@ApiModel("放款接口通用返回")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplInfoAppApptReponse implements Serializable {

    private AppInfoAppApptSecondResponse response;
}
