package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * 生成流水号信息枚举值
 *
 * @author
 * @date 2018/07/18
 */
@Getter
public enum SerialNoEnum {
    //前4位（自己定义）+14位日期+8位流水序号,统一规则，合计26位
    router_match_result("PL_ROUTER_RESULT_SEQUENCE", 26, "RULE", "路由结果表"),
    PL_ASSETS_SPLIT("PL_ASSETS_SPLIT_SEQUENCE", 26, "PLAS", "资产表序"),
    PL_ASSETS_SPLIT_ITEM("PL_ASSETS_SPLIT_ITEM_SEQUENCE", 26, "PLAI", "资产拆分表"),
    PL_BALANCE_PAY_INFO("PL_BALANCE_PAY_INFO_SEQUENCE", 26, "PBPI", "余额支付信息列表"),
    PL_ACCOUNT_TRANSACTION(" PL_ACCOUNT_TRANS_SEQUENCE", 26, "PLAT", "入账事务表"),
    PL_ACCOUNT_ENTRY("PL_ACCOUNT_ENTRY_SEQUENCE", 26, "PLAE", "入账分录表");

    private String seqName;//使用序列名称
    private int length;//流水长度
    private String typeName;//4位业务标识
    private String desc;//描述

    SerialNoEnum(String seqName, int length, String typeName, String desc) {
        this.seqName = seqName;
        this.length = length;
        this.typeName = typeName;
        this.desc = desc;
    }
}
