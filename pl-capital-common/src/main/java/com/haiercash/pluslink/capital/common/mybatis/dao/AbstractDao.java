package com.haiercash.pluslink.capital.common.mybatis.dao;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.utils.PaginationList;

import java.util.List;
import java.util.Map;

public interface AbstractDao {
    <T> List<T> selectByFilter(Map<String, Object> parameterMap);
    List<Map<String, Object>> selectMapByFilter(Map<String, Object> parameterMap);

    <T> PaginationList<T> pageByFilter(Map<String, Object> parameterMap, RowBounds rowBounds);
    PaginationList<Map<String, Object>> pageMapByFilter(Map<String, Object> parameterMap, RowBounds rowBounds);
    int countByFilter(Map<String, Object> parameterMap);
    <T> List<T> selectForCommon(Map<String, Object> parameterMap);
    <T> List<T> selectForCommon(Map<String, Object> parameterMap, RowBounds rowBounds);
    <T> T selectById(Map<String, Object> parameterMap);
    Map<String, Object> selectMapById(Map<String, Object> parameterMap);
    <T> T initForCommon(Map<String, Object> parameterMap);

    int deleteByIds(Map<String, Object> parameterMap);

    Integer selectVersion(Map<String, Object> parameterMap);
    int insert(Map<String, Object> parameterMap);
    int update(Map<String, Object> parameterMap);
}
