package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: yu jianwei
 * @Date: 2018/8/15 15:13
 * @Description:
 */
@Getter
@Setter
public class PgwBatchAccountRecordRequest {
    /**
     * 批次报账事务号
     */
    private String batchTransNo;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 入账明细 accountRecordList 开始
     */
    private List<PgwAccountRecordRequest> accountRecordList;

}

