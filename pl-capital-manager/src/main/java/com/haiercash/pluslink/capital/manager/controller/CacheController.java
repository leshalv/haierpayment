package com.haiercash.pluslink.capital.manager.controller;


import com.haiercash.pluslink.capital.manager.config.Global;
import org.mybatis.mapper.entity.EntityColumn;
import org.mybatis.mapper.entity.EntityTable;
import org.mybatis.mapper.mapperhelper.EntityHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by lihua on 2016/7/20.
 */
@RestController
@RequestMapping("/api/message/cache")
public class CacheController extends BaseController {
    public CacheController( ) {
        super("92");
    }

    @RequestMapping(value = "/enums", method = RequestMethod.GET)
    public Map<String, Object> enums() {
        return super.success(Global.enumsMap);
    }

    @RequestMapping(value = "/entities", method = RequestMethod.GET)
    public Map<String, Object> entities() {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        Iterator<?> iterator = EntityHelper.getAllTable().entrySet().iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            List<String> keys = new ArrayList<String>();

            Map.Entry<Class<?>, EntityTable> entry = (Map.Entry<Class<?>, EntityTable>) iterator.next();

            Map<String, Object> entityDefine = new HashMap<String, Object>();
            String entityName = entry.getKey().getSimpleName();
            if (entityName.indexOf(".") > 0) {
                entityName = entityName.substring(entityName.lastIndexOf(".") + 1);
            }
            EntityTable entityTable = entry.getValue();
            Set<EntityColumn> columnSet = entityTable.getEntityTransientColumns();
            columnSet.addAll(entityTable.getEntityClassColumns());
            Iterator<EntityColumn> columnIterator = columnSet.iterator();
            while (columnIterator.hasNext()) {
                EntityColumn entityColumn = columnIterator.next();
                entityDefine.put(entityColumn.getProperty(), null);
                if (entityColumn.isId()) {
                    keys.add(entityColumn.getProperty());
                }
            }

            map.put("define", entityDefine);
            map.put("keys", keys);

            entityMap.put(entityName.substring(0, 1).toLowerCase() + entityName.substring(1), map);
        }
        return super.success(entityMap);
    }
}
