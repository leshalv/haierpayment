package com.haiercash.pluslink.capital.common.mybatis.support;

import java.io.Serializable;

/**
 * Created by lihua on 2016/7/14.
 */
public class Column implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 列名
     */
    protected String name;
    /**
     * 类型，支持：String,Date,Integer,BigDecimal
     */
    protected String type;
    protected Object value;
    /**
     * 属性名
     */
    protected String property;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
