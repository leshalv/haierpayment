package com.haiercash.pluslink.capital.common.mybatis.support;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.ibatis.utils.PaginationList;
//import org.apache.ibatis.utils.PaginationList;

/**
 * Created by lihua on 2018/1/6.
 */
public class CustomJacksonModule extends SimpleModule {
    public CustomJacksonModule() {
        addDeserializer(PaginationList.class, new PaginationListDeserializer());
        addSerializer(PaginationList.class, new PaginationListSerializer());
        //addDeserializer(Number.class, new NumberDeserializer());
        //addSerializer(Number.class, new NumberSerializer());
    }
}
