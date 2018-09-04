package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * 时间枚举
 * 年月日时分秒
 */
@Getter
public enum TimeEnum {

    year("year","年"),
    month("month","个月"),
    day("day","天"),
    hour("hour","小时"),
    minute("minute","分钟"),
    second("second","秒");


    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static TimeEnum getEnum(String code) {
        TimeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TimeEnum a = var1[var3];
            if (a.code.equals(code)) {
                return a;
            }
        }
        throw new IllegalArgumentException("No enum code '" + code + "'. " + TimeEnum.class);

    }

    TimeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
