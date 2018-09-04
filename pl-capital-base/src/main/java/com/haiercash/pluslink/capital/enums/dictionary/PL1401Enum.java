package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**状态(PL1401)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1401Enum {

    PL1401_1_0("0", "无效"),
    PL1401_2_1("1", "有效"),
    PL1401_3_2("2", "授信申请中");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1401Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1401Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1401Enum.class);
        }
    }

    PL1401Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
