package com.haiercash.pluslink.capital.common.mybatis.support;

import com.haiercash.pluslink.capital.common.utils.TypeUtils;
import org.mybatis.mapper.entity.EntityColumn;
import org.mybatis.mapper.entity.EntityTable;
import org.mybatis.mapper.mapperhelper.EntityHelper;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by lihua on 2016/7/14.
 */
public class Select {
    // 是否为自定义查询
    private boolean isCustom = false;
    private Map<String, Object> parameterMap;
    private Class<?> cls;
    private EntityTable entityTable;
    private Set<EntityColumn> columnSet;

    public Select(Class<?> cls) {
        this.parameterMap = new HashMap<String, Object>();
        this.cls = cls;
        this.entityTable = EntityHelper.getEntityTable(this.cls);
        this.columnSet = EntityHelper.getColumns(this.cls);

        if (this.entityTable != null) {
            this.parameterMap.put("entityName", this.entityTable.getEntityClass().getName());
            this.parameterMap.put("tableName", this.entityTable.getName());
        }
    }
    public Select(String entityOrTableName) {
        this.parameterMap = new HashMap<String, Object>();
        this.entityTable = EntityHelper.getEntityTable(entityOrTableName);
        this.cls = this.entityTable.getEntityClass();
        this.columnSet = EntityHelper.getColumns(this.cls);

        if (this.entityTable != null) {
            this.parameterMap.put("entityName", this.entityTable.getEntityClass().getName());
            this.parameterMap.put("tableName", this.entityTable.getName());
        }
    }

    public Select isCustom(boolean isCustom) {
        this.isCustom = isCustom;
        return this;
    }

    public Select init(QueryParam queryParam) {
        if (queryParam.getDistinct() != null) {
            this.parameterMap.put("distinct", queryParam.getDistinct());
        }
        if (!StringUtils.isEmpty(queryParam.getColumns())) {
            List<Map<String, Object>> columnList = new ArrayList<>();
            String[] columns = queryParam.getColumns().split(",");
            for (String columnName : columns) {
                Map<String, Object> map = new HashMap<>();
                QueryColumn queryColumn = getColumn(columnName.trim());
                if (queryColumn != null) {
                    map.put("name", queryColumn.getName());
                    if (queryParam.getConfuses() != null && queryParam.getConfuses().containsKey(queryColumn.getProperty())) {
                        map.put("confuse", queryParam.getConfuses().get(queryColumn.getProperty()));
                    }
                    columnList.add(map);
                }
            }
            this.parameterMap.put("columnList", columnList);
        } else if (queryParam.getConfuses() != null) {
            Set<EntityColumn> columnSet;
            if (this.isCustom) {
                columnSet = EntityHelper.getColumnsWithTransient(this.cls);
            } else {
                columnSet = EntityHelper.getColumns(this.cls);
            }
            List<Map<String, Object>> columnList = new ArrayList<>();
            Iterator<EntityColumn> iterator = columnSet.iterator();
            while (iterator.hasNext()) {
                EntityColumn column = iterator.next();
                Map<String, Object> map = new HashMap<>();
                QueryColumn queryColumn = getColumn(column);
                if (queryColumn != null) {
                    map.put("name", queryColumn.getName());
                    if (queryParam.getConfuses() != null && queryParam.getConfuses().containsKey(queryColumn.getProperty())) {
                        map.put("confuse", queryParam.getConfuses().get(queryColumn.getProperty()));
                    }
                    columnList.add(map);
                }
            }
            this.parameterMap.put("columnList", columnList);
        }
        if (!StringUtils.isEmpty(queryParam.getValue())) {
            this.parameterMap.put("value", queryParam.getValue());
        }
        if (queryParam.getFilter() != null) {
            if (queryParam.getColumns() != null && queryParam.getFilter().getColumns() == null) {
                queryParam.getFilter().setColumns(queryParam.getColumns());
            }
            this.filter(queryParam.getFilter());
        } else if (!StringUtils.isEmpty(queryParam.getQ()) && queryParam.getColumns() != null) {
            QueryFilter queryFilter = new QueryFilter();
            queryFilter.setValue("%" + queryParam.getQ() + "%");
            queryFilter.setColumns(queryParam.getColumns());
            queryFilter.setType("lower");
            this.filter(queryFilter);
        }
        if (queryParam.getFilterMoreList() != null && queryParam.getFilterMoreList().size() > 0) {
            this.filterMore(queryParam.getFilterMoreList());
        }
        if (!StringUtils.isEmpty(queryParam.getForceFilter())) {
            this.parameterMap.put("forceFilter", queryParam.getForceFilter());
        }
        if (queryParam.getOrder() != null) {
            List<QueryColumn> orderList = queryParam.getOrderList();
            if (orderList == null) {
                orderList = new ArrayList<>();
            }
            String[] orders = queryParam.getOrder().split(",");
            for (String order : orders) {
                QueryColumn queryColumn = new QueryColumn();
                String[] arr = order.replaceAll("  ", " ").split(" ");
                if (arr.length > 0) {
                    queryColumn.setName(arr[0]);
                }
                if (arr.length > 1) {
                    queryColumn.setSort(arr[1]);
                }
                orderList.add(queryColumn);
            }
            queryParam.setOrderList(orderList);
        }
        if (queryParam.getOrderList() != null && queryParam.getOrderList().size() > 0) {
            this.order(queryParam.getOrderList());
        }
        if (queryParam.getInitParam() != null) {
            List<QueryColumn> initParamList = queryParam.getInitParamList();
            if (initParamList == null) {
                initParamList = new ArrayList<>();
            }
            Iterator<Map.Entry<String, Object>> iterator = queryParam.getInitParam().entrySet().iterator();
            while (iterator.hasNext()) {
                QueryColumn queryColumn = new QueryColumn();
                Map.Entry<String, Object> entry = iterator.next();
                queryColumn.setName(entry.getKey());
                queryColumn.setOperate("=");
                queryColumn.setValue(entry.getValue());
                initParamList.add(queryColumn);
            }
            queryParam.setInitParamList(initParamList);
        }
        if (queryParam.getInitParamList() != null && queryParam.getInitParamList().size() > 0) {
            this.init(queryParam.getInitParamList());
        }
        return this;
    }

    public Select init(List<QueryColumn> initParamList) {
        if (initParamList != null && initParamList.size() > 0) {
            parameterMap.put("initParamList", transColumn(initParamList, "="));
        }
        return this;
    }

    public Select filter(QueryFilter filter) {
        if (filter != null) {
            Map<String, Object> filterMap = new HashMap<String, Object>();
            String value = filter.getValue();
            if (StringUtils.isEmpty(value)) {
                return this;
            }
            value = value.trim().replaceAll("  ", " ").replaceAll("，", ",");
            if (StringUtils.isEmpty(value)) {
                return this;
            }
            String operate = filter.getOperate();
            if (StringUtils.isEmpty(operate)) {
                if (value.contains(" ")) {
                    operate = "and";
                    String[] values = value.split(" ");
                    List<String> valueList = new ArrayList<>();
                    for (String v : values) {
                        valueList.add("%" + v + "%");
                    }
                    filterMap.put("valueList", valueList);
                } else if (value.contains(",")) {
                    operate = "or";
                    String[] values = value.split(",");
                    List<String> valueList = new ArrayList<>();
                    for (String v : values) {
                        valueList.add("%" + v + "%");
                    }
                    filterMap.put("valueList", valueList);
                } else {
                    operate = "default";
                    if (!value.startsWith("%") && !value.endsWith("%")) {
                        value = "%" + value + "%";
                    }
                    filterMap.put("value", value);
                }
            } else {
                if (!value.startsWith("%") && !value.endsWith("%")) {
                    value = "%" + value + "%";
                }
                filterMap.put("value", value);
            }
            filterMap.put("operate", operate);

            List<QueryColumn> columnList = filter.getColumnList();
            if (columnList == null) {
                columnList = new ArrayList<QueryColumn>();
                if (filter.getColumns() == null) {
                    Iterator<EntityColumn> iterator = columnSet.iterator();
                    while (iterator.hasNext()) {
                        columnList.add(getColumn(iterator.next()));
                    }
                } else {
                    String[] columns = filter.getColumns().split(",");
                    for (String columnName : columns) {
                        columnList.add(getColumn(columnName.trim()));
                    }
                }
            }
            filterMap.put("columnList", columnList);
            if (!StringUtils.isEmpty(filter.getType())) {
                filterMap.put("type", filter.getType());
            }
            parameterMap.put("filter", filterMap);
        }
        return this;
    }

    public Select filterMore(List<QueryColumn> filterMoreList) {
        if (filterMoreList != null && filterMoreList.size() > 0) {
            parameterMap.put("filterMoreList", transColumn(filterMoreList, "="));
        }
        return this;
    }

    public Select filterMore(String name, Object value) {
        this.filterMore(name, value, "=");
        return this;
    }

    public Select filterMore(String name, Object value, String operate) {
        if (StringUtils.isEmpty(name) || value == null) {
            return this;
        }
        List<QueryColumn> filterMoreList = (List<QueryColumn>) parameterMap.get("filterMoreList");
        if (filterMoreList == null) {
            filterMoreList = new ArrayList<QueryColumn>();
        }
        QueryColumn queryColumn = this.getColumn(name);
        if (queryColumn == null) {
            queryColumn = new QueryColumn();
            queryColumn.setName(name);
            queryColumn.setType(TypeUtils.simpleClassName(value.getClass()));
        }
        // queryColumn.setName(name);
        // queryColumn.setType(value.getClass().getSimpleName());
        String op = operate(operate);
        if (op != null) {
            queryColumn.setOperate(operate);
            if (operate.equals("or")) {
                queryColumn.setValue(((String) value).split(","));
            } else if (operate.equals("in")) {
                String[] arr = ((String) value).split(",");
                String v = "(";
                for (String s : arr) {
                    if (!v.equals("(")) {
                        v += ",";
                    }
                    v += "'" + s + "'";
                }
                v += ")";
                queryColumn.setValue(v);
            } else {
                queryColumn.setValue(value);
            }
            filterMoreList.add(queryColumn);
        }
        parameterMap.put("filterMoreList", filterMoreList);
        return this;
    }
    public String operate(String operate) {
        if (!StringUtils.isEmpty(operate)) {
            if (operate.equals("=") || operate.equals("like") || operate.equals("or") || operate.equals(">=") || operate.equals("<=") || operate.equals(">") || operate.equals("<")
                    || operate.equals("in") || operate.equals("is") || operate.equals("is not")) {
                return operate;
            }
        }
        return null;
    }

    public Select order(List<QueryColumn> orderList) {
        if (this.entityTable != null) {
            Set<EntityColumn> keyColumns = this.entityTable.getEntityClassPKColumns();
            if (keyColumns != null) {
                Iterator<EntityColumn> iterator = keyColumns.iterator();
                while (iterator.hasNext()) {
                    EntityColumn keyColumn = iterator.next();
                    boolean found = false;
                    for (QueryColumn queryColumn : orderList) {
                        if (keyColumn.getProperty().equals(queryColumn.getName())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        orderList.add(getColumn(keyColumn));
                    }
                }
            }
        }
        if (orderList != null && orderList.size() > 0) {
            parameterMap.put("orderList", transColumn(orderList, null));
        }
        return this;
    }

    private List<QueryColumn> transColumn(List<QueryColumn> queryColumnList, String defaultOperate) {
        if (queryColumnList != null) {
            for (int i = 0; i < queryColumnList.size(); i++) {
                QueryColumn queryColumn = queryColumnList.get(i);
                if (queryColumn.getOrs() != null) {
                    queryColumn.setOrs(transColumn(queryColumn.getOrs(), defaultOperate));
                    queryColumnList.set(i, queryColumn);
                    continue;
                }
                QueryColumn column = getColumn(queryColumn.getName());
                if (column != null) {
                    queryColumn.setName(column.getName());
                    String type = column.getType();
                    if (!StringUtils.isEmpty(type)) {
                        queryColumn.setType(type);
                        if (StringUtils.isEmpty(queryColumn.getFormat())) {
                            queryColumn.setFormat("date");
                        }
                        /*if (type.equals("Date")) {
                            String format = queryColumn.getFormat();
                            if (StringUtils.isEmpty(format) || format.equals("date")) {
                                format = "'yyyy-mm-dd'";
                            } else if (format.equals("datetime")) {
                                format = "'yyyy-mm-dd hh24:mi:ss'";
                            } else if (format.equals("time")) {
                                format = "'hh24:mi:ss'";
                            }
                            queryColumn.setFormat(format);
                        }*/
                    }
                    String op = operate(queryColumn.getOperate());
                    if (op != null) {
                        if (op.equals("or")) {
                            queryColumn.setValue(((String) queryColumn.getValue()).split(","));
                        } else if (op.equals("like") && queryColumn.getValue() != null && !((String) queryColumn.getValue()).contains("%")) {
                            queryColumn.setValue("%" + queryColumn.getValue() + '%');
                        }
                    } else if (!StringUtils.isEmpty(defaultOperate)) {
                        if (defaultOperate.equals("like")) {
                            queryColumn.setValue("%" + queryColumn.getValue() + '%');
                        }
                        queryColumn.setOperate(defaultOperate);
                    }
                    queryColumnList.set(i, queryColumn);
                } else if (!StringUtils.isEmpty(defaultOperate)) {
                    if (defaultOperate.equals("like")) {
                        queryColumn.setValue("%" + queryColumn.getValue() + '%');
                    }
                    queryColumn.setOperate(defaultOperate);
                    queryColumnList.set(i, queryColumn);
                }
            }
        }
        return queryColumnList;
    }

    private QueryColumn getColumn(String columnName) {
        if (!StringUtils.isEmpty(columnName)) {
            Set<EntityColumn> columnSet;
            if (this.isCustom) {
                columnSet = EntityHelper.getColumnsWithTransient(this.cls);
            } else {
                columnSet = EntityHelper.getColumns(this.cls);
            }
            Iterator<EntityColumn> iterator = columnSet.iterator();
            String columnNameCheck = columnName;
            String prefix = "";
            if (columnName.contains(".")) {
                String[] arr = columnName.split("\\.");
                prefix = arr[0];
                columnNameCheck = arr[1];
            }
            while (iterator.hasNext()) {
                EntityColumn column = iterator.next();
                if (column.getProperty().equals(columnNameCheck)) {
                    return this.getColumn(column, prefix);
                }
            }
        }
        return null;
    }

    private QueryColumn getColumn(EntityColumn column, String prefix) {
        if (column != null) {
            QueryColumn queryColumn = new QueryColumn();
            if (!StringUtils.isEmpty(prefix)) {
                queryColumn.setName(prefix + "." + column.getColumn());
            } else {
                queryColumn.setName(column.getColumn());
            }
            queryColumn.setProperty(column.getProperty());
            queryColumn.setType(TypeUtils.simpleClassName(column.getJavaType()));
            return queryColumn;
/*            if (type.equals("java.lang.String")) {
                columnMap.put("type", column.getColumn());
            }*/
        }
        return null;
    }
    private QueryColumn getColumn(EntityColumn column) {
        return getColumn(column, null);
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
