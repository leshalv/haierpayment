package com.haiercash.pluslink.capital.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class JsonSerializer {
    private static final FastJsonConfig GLOBAL_CONFIG = new FastJsonConfig();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public JsonSerializer() {
    }

    public static FastJsonConfig getGlobalConfig() {
        return GLOBAL_CONFIG;
    }

    public static String serialize(Object obj) {
        return JSON.toJSONString(obj, getGlobalConfig().getSerializeConfig(), getGlobalConfig().getSerializeFilters(), getGlobalConfig().getDateFormat(), Feature.of(getGlobalConfig().getFeatures()), getGlobalConfig().getSerializerFeatures());
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T deserialize(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

    public static <T> List<T> deserializeArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    public static Map<String, Object> deserializeMap(String json) {
        return JSON.parseObject(json);
    }

    static {
        GLOBAL_CONFIG.setDateFormat("yyyy-MM-dd HH:mm:ss");
        GLOBAL_CONFIG.setFeatures(Feature.AutoCloseSource, Feature.InternFieldNames, Feature.UseBigDecimal, Feature.AllowUnQuotedFieldNames, Feature.AllowSingleQuotes, Feature.AllowArbitraryCommas, Feature.SortFeidFastMatch, Feature.IgnoreNotMatch);
        GLOBAL_CONFIG.setSerializerFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.SkipTransientField, SerializerFeature.SortField, SerializerFeature.WriteEnumUsingName, SerializerFeature.WriteDateUseDateFormat);
        GLOBAL_CONFIG.setSerializeFilters(new CommonValueFilter());
    }

    private static final class CommonValueFilter implements ValueFilter {
        private CommonValueFilter() {
        }

        public Object process(Object object, String name, Object value) {
            if(value == null) {
                return null;
            } else {
                String type = value.getClass().getSimpleName();
                byte var6 = -1;
                switch(type.hashCode()) {
                case -672261858:
                    if(type.equals("Integer")) {
                        var6 = 1;
                    }
                    break;
                case 2374300:
                    if(type.equals("Long")) {
                        var6 = 2;
                    }
                    break;
                case 1438607953:
                    if(type.equals("BigDecimal")) {
                        var6 = 0;
                    }
                }

                switch(var6) {
                case 0:
                    BigDecimal valueD = (BigDecimal)value;
                    if(valueD.compareTo(BigDecimal.valueOf(1.0E16D)) >= 0) {
                        return String.valueOf(value);
                    }
                    break;
                case 1:
                    Integer valueN = (Integer)value;
                    if(valueN.intValue() > 1000000000) {
                        return String.valueOf(value);
                    }
                    break;
                case 2:
                    Long valueL = (Long)value;
                    if(valueL.longValue() > 1000000000L) {
                        return String.valueOf(value);
                    }
                }
                return value;
            }
        }
    }
}
