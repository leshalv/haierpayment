package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/31 15:03
 * @Description:查询账户信息请求
 */
@Getter
@Setter
public class PgwQueryByCrmNoRequest {
    /**
     * 客户编号
     */
    private String crmNo;
    /**
     * 客户类型 PL1301Enum
     */
    private String crmType;
    /**
     * 账户类型
     */
    private String accountType;
    /**
     * 是否开通
     */
    private String isOpen;


}
