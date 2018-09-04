package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: RequestHead
 * @ProjectName pl-capital
 * @Description: TODO 大数据风险分析请求头字段/放款详情
 * @date 2018/7/1815:24
 */
@Getter
@Setter
public class RequestHead implements Serializable {

    //交易码
    private String tradeCode;

    //报文流水号
    private String serno;

    //系统标识
    private String sysFlag;

    //交易类型
    private String tradeType;

    //交易日期
    private String tradeDate;

    //交易时间
    private String tradeTime;

    //渠道编码
    private String channelNo;

    //合作方编码
    private String cooprCode;


}
