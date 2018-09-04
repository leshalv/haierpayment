package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * 返回码枚举值(系统内部调用)
 */
@Getter
public enum ReturnCode {


    msg_request_is_null("F", "MSG1060001", "请求对象不能为空"),
    msg_request_param_is_null("F", "MSG1060002", "请求参数不能为空"),
    msg_request_param_not_valid("F", "MSG1060003", "请求参数未通过校验"),
    msg_param_lack("F", "MSG1060004", "参数缺失"),
    un_known("F", "99999", "请求参数未通过校验"),
    msg_approve_role_not_exists("F","MSG30060001", "下一级审批角色无相关负责人"),
    msg_workflow_fail("F","MSG30060002", "开启工作流失败");

    private String status;
    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static ReturnCode getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            ReturnCode[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ReturnCode a = var1[var3];
                if (a.code.equals(code)) {
                    return a;
                }
            }
            throw new IllegalArgumentException("No enum code '" + code + "'. " + ReturnCode.class);
        }
    }

    ReturnCode(String status, String code, String desc) {
        this.status = status;
        this.code = code;
        this.desc = desc;
    }

}
