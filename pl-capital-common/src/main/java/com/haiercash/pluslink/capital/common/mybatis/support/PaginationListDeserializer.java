package com.haiercash.pluslink.capital.common.mybatis.support;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.ibatis.utils.PaginationList;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import org.apache.ibatis.utils.PaginationList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihua on 2018/1/6.
 */
public class PaginationListDeserializer extends JsonDeserializer<PaginationList> {
    @Override
    public PaginationList deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        HashMap map = new HashMap();
        PaginationList paginationList = new PaginationList();
        paginationList.setCount((Integer) map.get("count"));
        paginationList.setData((List) map.get("data"));
        paginationList.setSum((Map) map.get("sum"));
        return paginationList;
    }
}
