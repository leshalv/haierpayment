package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**还款方式(PL0406)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0406Enum {

    PL0406_1_ADVANCE("1", "提前结清"),
    PL0406_2_ONTIME("2", "按期足额还款"),
    PL0406_3_PART("3", "部分还款");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0406Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0406Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0406Enum.class);
        }
    }

    PL0406Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
