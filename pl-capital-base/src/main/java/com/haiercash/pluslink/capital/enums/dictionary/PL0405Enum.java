package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**用户性别维度(PL0405)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0405Enum {

    PL0405_1_MAN("0", "女"),
    PL0405_2_WOMAN("1", "男"),
    PL0405_3_ALL("2", "全部");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0405Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0405Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0405Enum.class);
        }
    }

    PL0405Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
