package com.haiercash.pluslink.capital.common.mybatis.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by lihua on 2016/8/31.
 */
public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String entityName;
    private Map<String, Object> define;
    private Map<String, Object> keys;
    private Map<String, Object> keyValues;
    private List<Map<String, Object>> keyValuesList;
    private String generator;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Map<String, Object> getDefine() {
        return define;
    }

    public void setDefine(Map<String, Object> define) {
        this.define = define;
    }

    public Map<String, Object> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, Object> keys) {
        this.keys = keys;
    }

    public Map<String, Object> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Map<String, Object> keyValues) {
        this.keyValues = keyValues;
    }

    public List<Map<String, Object>> getKeyValuesList() {
        return keyValuesList;
    }

    public void setKeyValuesList(List<Map<String, Object>> keyValuesList) {
        this.keyValuesList = keyValuesList;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }
}
