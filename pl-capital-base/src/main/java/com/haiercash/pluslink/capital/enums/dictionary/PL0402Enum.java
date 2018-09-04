package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**合作项目模式(PL0402)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0402Enum {

    PL0402_1_UNION_MODLE("1", "联合放款模式");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0402Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0402Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0402Enum.class);
        }
    }

    PL0402Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
