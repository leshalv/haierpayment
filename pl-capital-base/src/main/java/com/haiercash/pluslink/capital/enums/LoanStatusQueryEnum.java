package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * > 前置放款状态查询返回码
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/31 15:04
 */
@Getter
public enum LoanStatusQueryEnum {
    LOAN_STATUS_SUCCESS("00000", "放款成功"),
    LOAN_STATUS_EXCEPTION("PLCO3505", "通信异常"),
    LOAN_STATUS_FAIL("PLCO3201", "放款失败");

    /**
     * The code
     **/
    private String code;
    /**
     * The desc
     **/
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static LoanStatusQueryEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            LoanStatusQueryEnum[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                LoanStatusQueryEnum a = var1[var3];
                if (a.code.equals(code)) {
                    return a;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + LoanStatusQueryEnum.class);
        }
    }

    LoanStatusQueryEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
