package com.haiercash.pluslink.capital.common.mybatis.support;

import com.haiercash.pluslink.capital.common.utils.TypeUtils;
import org.mybatis.mapper.entity.EntityColumn;
import org.mybatis.mapper.entity.EntityTable;
import org.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.*;

/**
 * Created by lihua on 2016/7/15.
 */
public class Update {
    private Map<String, Object> parameterMap;
    private Class<?> cls;
    private EntityTable entityTable;
    private Set<EntityColumn> columnSet;
    private Set<EntityColumn> keySet;

    public Update(Class<?> cls) {
        this.parameterMap = new HashMap<String, Object>();
        this.cls = cls;
        this.entityTable = EntityHelper.getEntityTable(this.cls);
        this.columnSet = EntityHelper.getColumns(this.cls);
        this.keySet = EntityHelper.getPKColumns(this.cls);
        if (this.entityTable != null) {
            this.parameterMap.put("tableName", this.entityTable.getName());
        }
    }

    public Update init(Map<String, Object> entityMap, Map<String, Object> keyValues) {
        List<Column> columnList = new ArrayList<Column>();
        Iterator<EntityColumn> iterator = columnSet.iterator();
        while (iterator.hasNext()) {
            EntityColumn entityColumn = iterator.next();
            if (entityColumn.isUpdatable()/* && !keySet.contains(entityColumn)*/) {
                Column column = new Column();
                column.setName(entityColumn.getColumn());
                column.setValue(entityMap.get(entityColumn.getProperty()));
                column.setType(TypeUtils.simpleClassName(entityColumn.getJavaType()));
                columnList.add(column);
            }
        }

        List<Column> keys = new ArrayList<Column>();
        iterator = keySet.iterator();
        while (iterator.hasNext()) {
            EntityColumn entityColumn = iterator.next();
            Column column = new Column();
            column.setName(entityColumn.getColumn());
            column.setValue(keyValues.get(entityColumn.getProperty()));
            keys.add(column);
        }
        this.parameterMap.put("columns", columnList);
        this.parameterMap.put("keys", keys);
        return this;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
