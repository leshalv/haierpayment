package com.haiercash.pluslink.capital.enums.dictionary;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 状态(PL0601)
 *
 * @Auther: WDY
 * @Date: 2018/7/30 20:06
 * @rmk:
 */
@Getter
public enum PL0601Enum {

    PL0601_1_10("10", "已受理"),
    PL0601_2_11("11", "头寸不足"),
    PL0601_3_20("20", "额度审批中"),
    PL0601_4_21("21", "额度审批拒绝"),
    PL0601_5_22("22", "额度审批通过"),
    PL0601_6_23("23", "审批额度不足"),
    PL0601_7_24("24", "提现审批中"),
    PL0601_8_25("25", "提现审批拒绝"),
    PL0601_9_26("26", "提现审批通过"),
    PL0601_10_30("30", "放款中"),
    PL0601_11_31("31", "放款成功"),
    PL0601_12_32("32", "放款失败"),
    PL0601_13_14("14", "数据组装异常"),
    PL0601_14_12("12", "非营业时间"),
    PL0601_15_13("13", "资方系统异常"),

    QUERY_LIMIT("查询已放款的状态", "10", "20", "22", "24", "26", "30", "31");

    private String code;
    private String desc;
    private List<String> codes;

    PL0601Enum(String desc, String... codes) {
        this.desc = desc;
        this.codes = Arrays.asList(codes);
    }

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static PL0601Enum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (PL0601Enum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + PL0601Enum.class);
        }
    }

    PL0601Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
