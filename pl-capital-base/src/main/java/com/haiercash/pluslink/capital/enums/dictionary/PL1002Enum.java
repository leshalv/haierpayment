package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**事件结束方式(PL1002)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL1002Enum {

    PL1002_1_AUTOMATIC("1", "自动完成"),
    PL1002_2_BY_DATA("2", "根据数据返回的元数据执行完成");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL1002Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL1002Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL1002Enum.class);
        }
    }

    PL1002Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
