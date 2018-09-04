package com.haiercash.pluslink.capital.processer.server.enums;

import lombok.Getter;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/16 16:55
 * @Description:资产拆分借款类型枚举
 */
@Getter
public enum AssetsSplitItemLoanTypeEnum {
    LOAN_TYPE_INTERNAL("1", "自有"),

    LOAN_TYPE_EXTERNAL("2", "外部资方");

    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static AssetsSplitItemLoanTypeEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (AssetsSplitItemLoanTypeEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + AssetsSplitItemLoanTypeEnum.class);
        }
    }

    AssetsSplitItemLoanTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
