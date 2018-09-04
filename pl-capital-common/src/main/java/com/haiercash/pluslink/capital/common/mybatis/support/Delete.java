package com.haiercash.pluslink.capital.common.mybatis.support;

import org.mybatis.mapper.entity.EntityColumn;
import org.mybatis.mapper.entity.EntityTable;
import org.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.*;

/**
 * Created by lihua on 2016/7/15.
 */
public class Delete {
    private Map<String, Object> parameterMap;
    private Class<?> cls;
    private EntityTable entityTable;
    private Set<EntityColumn> columnSet;
    private Set<EntityColumn> keySet;

    public Delete(Class<?> cls) {
        this.parameterMap = new HashMap<String, Object>();
        this.cls = cls;
        this.entityTable = EntityHelper.getEntityTable(this.cls);
        this.columnSet = EntityHelper.getColumns(this.cls);
        this.keySet = EntityHelper.getPKColumns(this.cls);
        if (this.entityTable != null) {
            this.parameterMap.put("tableName", this.entityTable.getName());
        }
    }

    public Delete init(List<Map<String, Object>> keyValuesList) {
        if (keyValuesList != null && keyValuesList.size() > 0) {
            List<List<Column>> keysList = new ArrayList<List<Column>>();
            for (int i=0; i<keyValuesList.size(); i++) {
                Map<String, Object> keyValues = keyValuesList.get(i);

                List<Column> keys = new ArrayList<Column>();
                Iterator<EntityColumn> iterator = this.keySet.iterator();
                while (iterator.hasNext()) {
                    EntityColumn entityColumn = iterator.next();
                    Column column = new Column();
                    column.setName(entityColumn.getColumn());
                    column.setValue(keyValues.get(entityColumn.getProperty()));
                    keys.add(column);
                }
                keysList.add(keys);
            }
            this.parameterMap.put("keysList", keysList);
        }
        return this;
    }

    // 唯一主键专用
    public Delete filter(List<String> keyValues) {
        if (keyValues != null && keyValues.size() > 0) {
            Iterator<EntityColumn> iterator = this.keySet.iterator();
            String keyColumnName = "";
            if (iterator.hasNext()) {
                EntityColumn entityColumn = iterator.next();
                keyColumnName = entityColumn.getColumn();
            }

            this.parameterMap.put("keyColumnName", keyColumnName);
            this.parameterMap.put("keyValues", keyValues);
        }
        return this;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
