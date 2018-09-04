package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**状态(PL0701)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0701Enum {

    PL0701_1_00("00", "未启动"),
    PL0701_2_10("10", "流转中"),
    PL0701_3_20("20", "废弃"),
    PL0701_4_30("30", "结束");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0701Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0701Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0701Enum.class);
        }
    }

    PL0701Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
