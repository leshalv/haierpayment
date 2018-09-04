package com.haiercash.pluslink.capital.enums;

import lombok.Getter;

/**
 * 系统标识枚举值
 *
 * @author
 * @date 2018/07/18
 */
@Getter
public enum SystemFlagEnum {
    //系统标识枚举
    PLCRT("PLCRT", "路由中心"),
    PLCPS("PLCPS", "处理中心"),
    PLCPP("PLCPP", "前置机");
    private String systemFlag;//系统标识编码
    private String desc;//描述

    SystemFlagEnum(String systemFlag, String desc) {
        this.systemFlag = systemFlag;
        this.desc = desc;
    }
    }
