package com.haiercash.pluslink.capital.router.server.rest.controller.enums;

import lombok.Getter;

/**
 * 用户是否30天前贷款查询接口数据枚举值
 * @author WDY
 * @date 2018-07-25
 * @rmk
 */
@Getter
public enum LastLoanEnum {

    SUCCESS("00000", "成功"),
    FAIL("E0001", "失败"),

    YES("YES", "是"),
    NO("NO", "否");

    /**The code**/
    private String code;
    /**The desc**/
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static LastLoanEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            LastLoanEnum[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                LastLoanEnum a = var1[var3];
                if (a.code.equals(code)) {
                    return a;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + LastLoanEnum.class);
        }
    }

    LastLoanEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
