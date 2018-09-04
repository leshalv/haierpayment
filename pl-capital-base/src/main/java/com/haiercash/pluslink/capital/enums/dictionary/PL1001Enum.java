package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**事件类型(PL1001)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1001Enum {

    PL1001_1_ACTIVE("1", "主动执行"),
    PL1001_2_OTHER("2", "第三方驱动");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1001Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1001Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1001Enum.class);
        }
    }

    PL1001Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
