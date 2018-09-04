package com.haiercash.pluslink.capital.router.server.rest.controller;

import com.haiercash.pluslink.capital.router.server.entity.LastLoanIn;

/**
 * 用户是否30天前贷款查询接口数据处理控制
 * @author WDY
 * @date 2018-07-25
 * @rmk
 */
public interface ILastLoanController {

    int checkLastLoanController(LastLoanIn lastLoanIn,String serNo,String applSeq);
}
