package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**事件类型(PL0702)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0702Enum {

    PL0702_1_ACTIVE("1", "主动执行"),
    PL0702_2_OTHER("2", "第三方驱动");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0702Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0702Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0702Enum.class);
        }
    }

    PL0702Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
