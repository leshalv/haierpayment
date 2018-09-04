package com.haiercash.pluslink.capital.common.mybatis.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.ibatis.utils.PaginationList;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihua on 2018/1/6.
 */
public class PaginationListSerializer extends JsonSerializer<PaginationList> {
    @Override
    public void serialize(PaginationList value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("count", value.getCount());
        map.put("data", value.getData());
        map.put("sum", value.getSum());
        gen.writeObject(map);
    }
}
