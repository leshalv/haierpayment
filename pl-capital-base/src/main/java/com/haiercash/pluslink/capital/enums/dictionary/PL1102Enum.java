package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**入账状态(PL1102)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1102Enum {

    PL1102_1_NORECORDED("NORECORDED", "未入账"),
    PL1102_2_SUCCESS("SUCCESS", "入账成功"),
    PL1102_3_FAIL("FAIL", "入账失败");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1102Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1102Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1102Enum.class);
        }
    }

    PL1102Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
