package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**是否联合放款(PL0505)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0505Enum {


    PL0505_1_UNION("1", "联合放款"),
    PL0505_2_NOT_UNION("0", "非联合放款"),
    PL0505_3_UNKNOW("2", "未知");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0505Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0505Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0505Enum.class);
        }
    }

    PL0505Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
