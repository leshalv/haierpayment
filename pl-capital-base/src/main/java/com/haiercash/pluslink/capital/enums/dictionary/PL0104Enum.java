package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**操作类型(PL0104)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0104Enum {

    PL0104_1_INSERT("1", "新增"),
    PL0104_2_UPDTE("2", "修改"),
    PL0104_3_DELETE("3", "删除"),
    PL0104_4_ALL("4", "全部");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0104Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0104Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0104Enum.class);
        }
    }

    PL0104Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
