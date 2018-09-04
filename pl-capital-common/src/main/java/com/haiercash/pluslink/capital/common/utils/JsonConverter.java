package com.haiercash.pluslink.capital.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * json格式转化
 *
 * @author keliang.jiang
 * @date 2018/1/4
 */
public class JsonConverter {

    public static <T> T fastJson2Object(String json,TypeReference<T> type){
        return JSON.parseObject(json,type);
    }

    public static String object2Json(Object obj){
        return JSON.toJSON(obj).toString();
    }
}
