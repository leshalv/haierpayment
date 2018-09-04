package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/25 14:53
 * @Description: 通用入账接口响应
 */
@Setter
@Getter
public class PgwAccountRecordResponse {
    /*入账返回号*/
    private String returnId;
    /*事物号*/
    private String transNo;
    /*关联事物号*/
    private String contextTransNo;
    /*系统跟踪号*/
    private String orderId;
    /*交易类型*/
    private String trxType;
    /*应用ID*/
    private String appId;
    /*录入人*/
    private String operatorId;
    /*返回码*/
    private String rtnCode;
    /*返回描述*/
    private String rtnMsg;
}
