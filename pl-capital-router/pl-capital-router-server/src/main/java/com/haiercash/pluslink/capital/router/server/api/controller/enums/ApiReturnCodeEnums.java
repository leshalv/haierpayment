package com.haiercash.pluslink.capital.router.server.api.controller.enums;

import lombok.Getter;

/**
 * 路由中心Api错误码的统一返回枚举值
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
@Getter
public enum ApiReturnCodeEnums {

    success("S","00000","交易成功"),

    error_servicename("F","PLCR0001","错误的服务名称"),

    unsupport_caller("F","PLCR1001","不支持的调用方"),
    error_tran_code("F","PLCR1002","错误的交易码"),

    null_filed("F","PLCR2001","字段为空"),
    overlong_filed("F","PLCR2002","字段超长"),
    error_filed("F","PLCR2003","数据类型错误"),

    error_query_data("F","PLCR4001","查询结果不存在"),

    unknow("U","99999","系统内部错误");

    private String status;
    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static ApiReturnCodeEnums getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            ApiReturnCodeEnums[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ApiReturnCodeEnums a = var1[var3];
                if (a.code.equals(code)) {
                    return a;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + ApiReturnCodeEnums.class);
        }
    }

    ApiReturnCodeEnums(String status,String code, String desc) {
        this.status = status;
        this.code = code;
        this.desc = desc;
    }
}
