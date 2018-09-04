package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * 配置缓存加载部分枚举
 * @author WDY
 * 2018-07-13
 * (1)合作机构表缓存数据加载
 * (2)合作机构需求资料表缓存数据加载
 * (3)合作机构需求资料明细表缓存数据加载
 * (4)合作项目表缓存数据加载
 * (5)银行信息表缓存数据加载
 * (6)合作项目支持银行表缓存数据加载
 * (7)合作项目期限表缓存数据加载
 * (8)还款方式表缓存数据加载
 * (9)消金产品表缓存数据加载
 * (10)入件渠道表缓存数据加载
 * (11)客户标签表缓存数据加载
 * (12)调度规则表缓存数据加载
 * (13)规则阶段任务表缓存数据加载
 * (14)数据字典主表缓存数据加载
 * (15)数据字典子表缓存数据加载
 * (16)路由规则明细表缓存数据加载(包含路由规则数据)
 */
@Getter
public enum RedisCacheEnum {

    cooperation_agency("PL_CAPITAL_COOPERATION_AGENCY","合作机构表缓存数据加载"),
    agency_demand_info("PL_CAPITAL_AGENCY_DEMAND_INFO","合作机构需求资料表缓存数据加载"),
    agency_demand_item("PL_CAPITAL_AGENCY_DEMAND_ITEM","合作机构需求资料明细表缓存数据加载"),
    cooperation_project("PL_CAPITAL_COOPERATION_PROJECT","合作项目表缓存数据加载"),
    bank_info("PL_CAPITAL_BANK_INFO","银行信息表缓存数据加载"),
    project_bank("PL_CAPITAL_PROJECT_BANK","合作项目支持银行表缓存数据加载"),
    cooperation_period("PL_CAPITAL_COOPERATION_PERIOD","合作项目期限表缓存数据加载"),
    repayment_info("PL_CAPITAL_REPAYMENT_INFO","还款方式表缓存数据加载"),
    cash_product("PL_CAPITAL_CASH_PRODUCT","消金产品表缓存数据加载"),
    inserts_channel("PL_CAPITAL_INSERTS_CHANNEL","入件渠道表缓存数据加载"),
    cash_cust_sign("PL_CAPITAL_CASH_CUST_SIGN","客户标签表缓存数据加载"),
    schedule_rule("PL_CAPITAL_SCHEDULE_RULE","调度规则表缓存数据加载"),
    schedule_rule_phase("PL_CAPITAL_SCHEDULE_RULE_PHASE","规则阶段任务表缓存数据加载"),
    dictionary("PL_CAPITAL_DICTIONARY","数据字典主表缓存数据加载"),
    dictionary_sub("PL_CAPITAL_DICTIONARY_SUB","数据字典子表缓存数据加载"),
    route_rule_item("PL_CAPITAL_ROUTE_RULE_ITEM","路由规则明细表缓存数据加载");


    private String code;
    private String desc;

    /**
     * 根据编码获取枚举.
     *
     * @param code 枚举定义的编码
     * @return 查找到的枚举
     */
    public static RedisCacheEnum getEnum(String code) {
        RedisCacheEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RedisCacheEnum a = var1[var3];
            if (a.code.equals(code)) {
                return a;
            }
        }
        throw new IllegalArgumentException("No enum code '" + code + "'. " + RedisCacheEnum.class);

    }

    RedisCacheEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
