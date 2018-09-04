package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * redis处理状态枚举值
 * @author WDY
 * 2018-07-13
 */
@Getter
public enum RedisStatusEnum {

    router_query_data_error("F","9999","路由中心数据查询异常"),

    /*********************Redis缓存操作使用枚举**************************/
    redis_oper_error("F","5001","Redis获取操作类型不存在：0-添加,1-修改,2-删除"),
    redis_add_param_error("F","5002","Redis添加时key和value均不能为空"),
    redis_update_param_error("F","5003","Redis修改时key和value均不能为空"),
    redis_delete_param_error("F","5004","Redis删除时key不能为空"),
    redis_select_param_error("F","5005","Redis查询时key和返回泛型不能为空"),
    redis_add_error("F","5006","Redis添加异常"),
    redis_update_error("F","5007","Redis修改异常"),
    redis_delete_error("F","5008","Redis删除异常"),
    redis_select_error("F","5009","Redis查询异常"),
    redis_add_false_error("F","5010","Redis添加时未返回成功状态"),
    redis_update_false_error("F","5011","Redis修改时未返回成功状态"),
    redis_delete_false_error("F","5012","Redis删除时未返回成功状态"),
    redis_add_expire_error("F","5013","Redis添加时设置过期时间异常"),
    redis_update_expire_error("F","5014","Redis修改时设置过期时间异常"),
    redis_time_change_error("F","5015","Redis计算过期时间参数转换异常");
    /*********************Redis缓存操作使用枚举**************************/

    private String status;
    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static RedisStatusEnum getEnum(String code) {
        RedisStatusEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RedisStatusEnum a = var1[var3];
            if (a.code.equals(code)) {
                return a;
            }
        }
        throw new IllegalArgumentException("No enum code '" + code + "'. " + RedisStatusEnum.class);

    }

    RedisStatusEnum(String status,String code, String desc) {
        this.status = status;
        this.code = code;
        this.desc = desc;
    }
}
