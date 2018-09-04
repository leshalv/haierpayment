package com.haiercash.pluslink.capital.common.mybatis.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by lihua on 2016/7/16.
 */
public class QueryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 实体类名
     */
    private String entityName;
    private String distinct;
    private String columns;
    private Map<String, String> confuses;
    private String value;
    private Map<String, Object> initParam;
    private List<QueryColumn> initParamList;
    /**
     * 参数，格式为：[{name: 'xxx', type: 'eq', value: 'aa'}]
     */
    private List<QueryColumn> paramList;
    private String q;
    /**
     * 模糊查询，格式为：{value: 'xxx', columns: 'xxx,yyy'}
     */
    private QueryFilter filter;
    /**
     * 查询条件，格式为：[{name: 'xxx', type: 'like', value: 'xxx'}]
     */
    private List<QueryColumn> filterMoreList;
    private String forceFilter;
    private String order;
    /**
     * 排序，格式为：[{name: 'xxx', type: 'desc'}, {name: 'yyy', type: 'asc'}]
     */
    private List<QueryColumn> orderList;
    private Integer offset;
    private Integer limit;
    private Map<String, Object> sessionMap;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public Map<String, String> getConfuses() {
        return confuses;
    }

    public void setConfuses(Map<String, String> confuses) {
        this.confuses = confuses;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Object> getInitParam() {
        return initParam;
    }

    public void setInitParam(Map<String, Object> initParam) {
        this.initParam = initParam;
    }

    public List<QueryColumn> getInitParamList() {
        return initParamList;
    }

    public void setInitParamList(List<QueryColumn> initParamList) {
        this.initParamList = initParamList;
    }

    public List<QueryColumn> getParamList() {
        return paramList;
    }

    public void setParamList(List<QueryColumn> paramList) {
        this.paramList = paramList;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public QueryFilter getFilter() {
        return filter;
    }

    public void setFilter(QueryFilter filter) {
        this.filter = filter;
    }

    public List<QueryColumn> getFilterMoreList() {
        return filterMoreList;
    }

    public void setFilterMoreList(List<QueryColumn> filterMoreList) {
        this.filterMoreList = filterMoreList;
    }

    public String getForceFilter() {
        return forceFilter;
    }

    public void setForceFilter(String forceFilter) {
        this.forceFilter = forceFilter;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<QueryColumn> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<QueryColumn> orderList) {
        this.orderList = orderList;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }
}
