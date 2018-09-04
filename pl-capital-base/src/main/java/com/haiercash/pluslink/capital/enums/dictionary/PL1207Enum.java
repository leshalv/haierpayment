package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**
 * @Auther: yu jianwei
 * @Date: 2018/8/3 11:36
 * @Description:记账辅助代码
 */
@Getter
public enum PL1207Enum {
    PL1207_1_2315("2315", "工行"),
    PL1207_2_5001("5001", "工行支付供应商");
    private String code;
    private String desc;

    PL1207Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
