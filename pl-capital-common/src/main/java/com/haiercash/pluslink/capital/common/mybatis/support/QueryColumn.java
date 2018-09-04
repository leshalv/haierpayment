package com.haiercash.pluslink.capital.common.mybatis.support;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihua on 2016/7/14.
 */
public class QueryColumn extends Column implements Serializable {
    private static final long serialVersionUID = 1L;

    private Object valueFrom;
    private Object valueTo;
    /**
     * 操作方式，支持：like,=,>=,<=
     */
    private String operate;
    private String format;
    /**
     * 排序类型，支持asc,desc
     */
    private String sort;
    private List<QueryColumn> ors;

    public Object getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(Object valueFrom) {
        this.valueFrom = valueFrom;
    }

    public Object getValueTo() {
        return valueTo;
    }

    public void setValueTo(Object valueTo) {
        this.valueTo = valueTo;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<QueryColumn> getOrs() {
        return ors;
    }

    public void setOrs(List<QueryColumn> ors) {
        this.ors = ors;
    }
}
