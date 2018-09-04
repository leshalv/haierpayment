package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/31 15:00
 * @Description: 查询账户信息响应
 */
@Getter
@Setter
public class PgwQueryByCrmNoResponse {
    /**
     * 响应码
     */
    private String rtnCode;
    /**
     * 响应描述
     */
    private String rtnMsg;
    private Account account;

    @Setter
    @Getter
    public class Account {
        /**
         * 账户id
         */
        private String id;
    }
}