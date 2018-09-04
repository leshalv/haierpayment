package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**征信查询方式(PL0403)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0403Enum {

    PL0403_1_CASH("1", "消金映射"),
    PL0403_2_ANGENCY("2", "机构自查");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0403Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0403Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0403Enum.class);
        }
    }

    PL0403Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
