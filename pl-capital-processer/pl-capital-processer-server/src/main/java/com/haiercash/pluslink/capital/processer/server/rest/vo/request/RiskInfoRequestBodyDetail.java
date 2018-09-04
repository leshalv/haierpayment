package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: RiskInfoRequestBodyDetail
 * @ProjectName pl-capital
 * @Description: TODO   大数据分析分析请求体里面字段
 * @date 2018/7/1815:46
 */
@Getter
@Setter
public class RiskInfoRequestBodyDetail implements Serializable {
    //序号
    private String sortNo;

    //证件类型
    private String idTyp;

    //证件号码
    private String idNo;

    //数据类型
    private String dataTyp;

    //申请流水号
    private String applSeq;
}
