package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否出现异常(PL0107)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0107Enum {

    PL0107_1_ERROR("1", "异常"),
    PL0107_1_NORMAL("0", "非异常");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0107Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0107Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0107Enum.class);
        }
    }

    PL0107Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
