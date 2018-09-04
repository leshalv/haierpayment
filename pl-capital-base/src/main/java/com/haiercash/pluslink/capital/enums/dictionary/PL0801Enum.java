package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**任务状态(PL0801)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0801Enum {

    PL0801_1_00("00", "未启动"),
    PL0801_2_10("10", "流转中"),
    PL0801_3_20("20", "废弃"),
    PL0801_4_30("30", "结束");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0801Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0801Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0801Enum.class);
        }
    }

    PL0801Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
