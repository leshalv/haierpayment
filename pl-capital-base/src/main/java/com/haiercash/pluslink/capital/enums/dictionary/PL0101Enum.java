package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否删除(PL0101)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0101Enum {

    PL0101_1_DEL("DEL", "删除"),
    PL0101_2_NORMAL("NORMAL", "正常");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0101Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0101Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0101Enum.class);
        }
    }

    PL0101Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
