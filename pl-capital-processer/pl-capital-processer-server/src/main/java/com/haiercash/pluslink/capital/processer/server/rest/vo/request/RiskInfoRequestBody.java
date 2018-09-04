package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzh
 * @Title: RiskInfoRequestBody
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1815:35
 */
@Setter
@Getter
public class RiskInfoRequestBody implements Serializable {

    //大数据风险分析接口请求体包的list
    private List<RiskInfoRequestBodyDetail> list;

}
