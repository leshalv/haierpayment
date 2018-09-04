package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**还款状态(PL1502)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1502Enum {

    PL1502_1_0("0", "未还款"),
    PL1502_2_1("1", "已还款");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1502Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1502Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1502Enum.class);
        }
    }

    PL1502Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
