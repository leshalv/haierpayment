package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**还款类型(PL1501)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1501Enum {

    PL1501_1_1("1", "按期数还款"),
    PL1501_2_4("4", "全额还款");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1501Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1501Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1501Enum.class);
        }
    }

    PL1501Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
