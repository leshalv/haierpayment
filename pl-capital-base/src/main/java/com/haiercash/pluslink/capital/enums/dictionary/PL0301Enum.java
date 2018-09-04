package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**资料类型(PL0301)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0301Enum {

    PL0301_1_IMAGE("1", "影像"),
    PL0301_2_RISK_PARAM("2", "风险参数"),
    PL0301_3_PROTOCOL("3", "协议模板");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0301Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0301Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0301Enum.class);
        }
    }

    PL0301Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
