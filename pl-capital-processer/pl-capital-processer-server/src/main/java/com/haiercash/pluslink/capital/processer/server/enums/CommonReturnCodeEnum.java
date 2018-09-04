package com.haiercash.pluslink.capital.processer.server.enums;


import lombok.Getter;

/**
 * 通知接口枚举值
 *
 * @author lzh
 * @date 2018/2/2
 * 通用接口（错误）返回码
 */
@Getter
public enum CommonReturnCodeEnum {


    /*********************放款端（处理中心）通用错误码**************************/
    TRADE_SUCCESS("00000", "交易成功状态码"),

    //--------------start API层--------------------------------

    UNSUPPORT_CALLER("PLCP2001", "不支持调用方"),

    ERROR_SERVICENAME("PLCP2002", "错误的服务名称"),

    NULL_FILED("PLCP2003", "[xx]字段为空"),

    OVERLONG_FILED("PLCP2004", "字段超长"),

    //--------------end API层--------------------------------

    //--------------start Service--------------------------------

    DATA_NOT_EXIST("PLCP2105", "数据不存在"),

    DATA_QUERY_EXCEPTION("PLCP2106", "查询数据异常"),

    DATA_COMM_EXCEPTION("PLCP2107", "数据通信异常"),

    DATA_INSERT_EXCEPTION("PLCP2108", "插入数据异常"),

    REPEATED("PLCP2109", "数据重复");

    //--------------end Service--------------------------------
    private String code;
    private String desc;


    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static CommonReturnCodeEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            for (CommonReturnCodeEnum codeEnum : values()) {
                if (codeEnum.code.equals(code)) {
                    return codeEnum;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + CommonReturnCodeEnum.class);
        }
    }

    CommonReturnCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
