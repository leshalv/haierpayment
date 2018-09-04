package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

/**接口交易码(PL0108)
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0108Enum {

    PL0108_1_PLCR001("PLCR001", "通知路由处理接口(路由中心)"),
    PL0108_2_PLCR002("PLCR002", "资金方综合信息查询接口(路由中心)"),
    PL0108_3_PLCP001("PLCP001", "处理中心放款请求接口(处理中心)"),
    PL0108_4_PLCP002("PLCP002", "处理中心放款查询接口(处理中心)"),
    PL0108_5_PLCO001("PLCO001", "授信申请(前置)"),
    PL0108_6_PLCO002("PLCO002", "授信查询(前置)"),
    PL0108_7_PLCO003("PLCO003", "还款计划试算查询(前置)"),
    PL0108_8_PLCO004("PLCO004", "放款(前置)"),
    PL0108_9_PLCO005("PLCO005", "信息补录(前置)"),
    PL0108_10_PLCO006("PLCO006", "待还金额查询(前置)"),
    PL0108_11_PLCO007("PLCO007", "还款(前置)"),
    PL0108_12_PLCO008("PLCO008", "还款状态查询(前置)"),
    PL0108_13_PLCO009("PLCO009", "放款查询(前置)"),
    PL0108_14_PLCO010("PLCO010", "授信回调(前置)");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0108Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0108Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0108Enum.class);
        }
    }

    PL0108Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
