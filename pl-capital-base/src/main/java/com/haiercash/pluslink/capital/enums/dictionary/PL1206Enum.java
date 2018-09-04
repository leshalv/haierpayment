package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**
 * @Auther: yu jianwei
 * @Date: 2018/8/3 11:36
 * @Description:账户类型枚举
 */
@Getter
public enum PL1206Enum {
    PL1206_1_1001("1001", "银存账户"),
    PL1206_2_4000("4000", "其他应收款/第三方平台"),
    PL1206_3_1004("1004", "应收个人"),
    PL1206_4_1002("1002", "应收商户"),
    PL1206_5_4001("4001", "联合放款中间户"),
    PL1206_6_1000("1000", "机构账户");
    private String code;
    private String desc;

    PL1206Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
