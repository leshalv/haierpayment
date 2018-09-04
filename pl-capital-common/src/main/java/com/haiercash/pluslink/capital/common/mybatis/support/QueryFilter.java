package com.haiercash.pluslink.capital.common.mybatis.support;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihua on 2016/7/14.
 */
public class QueryFilter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String value;
    private String columns;
    /**
     * lower-转小写
     * upper-转大写
     */
    private String type;
    private String operate;
    private List<QueryColumn> columnList;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public List<QueryColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<QueryColumn> columnList) {
        this.columnList = columnList;
    }
}
