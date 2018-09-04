package com.haiercash.pluslink.capital.common.mybatis.support;

import com.haiercash.pluslink.capital.common.utils.RestUtils;
import com.haiercash.pluslink.capital.common.utils.TypeUtils;
import org.mybatis.mapper.entity.EntityColumn;
import org.mybatis.mapper.entity.EntityTable;
import org.mybatis.mapper.mapperhelper.EntityHelper;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by lihua on 2016/7/15.
 */
public class Insert {
    private Map<String, Object> parameterMap;
    private Class<?> cls;
    private EntityTable entityTable;
    private Set<EntityColumn> columnSet;

    public Insert(Class<?> cls) {
        this.parameterMap = new HashMap<String, Object>();
        this.cls = cls;
        this.entityTable = EntityHelper.getEntityTable(this.cls);
        this.columnSet = EntityHelper.getColumns(this.cls);
        if (this.entityTable != null) {
            this.parameterMap.put("tableName", this.entityTable.getName());
        }
    }

    public Insert init(Map<String, Object> entityMap, String generator) {
        List<Column> columnList = new ArrayList<Column>();
        Iterator<EntityColumn> iterator = columnSet.iterator();
        while (iterator.hasNext()) {
            EntityColumn entityColumn = iterator.next();
            if (entityColumn.isInsertable()) {
                Column column = new Column();
                column.setName(entityColumn.getColumn());
                if (entityColumn.isId() && !StringUtils.isEmpty(generator)) {
                    if (generator.equals("guid")) {
                        column.setValue(RestUtils.getGuid());
                    }
                } else {
                    column.setValue(entityMap.get(entityColumn.getProperty()));
                }
                column.setType(TypeUtils.simpleClassName(entityColumn.getJavaType()));
                columnList.add(column);
            }
        }
        this.parameterMap.put("columns", columnList);
        return this;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
