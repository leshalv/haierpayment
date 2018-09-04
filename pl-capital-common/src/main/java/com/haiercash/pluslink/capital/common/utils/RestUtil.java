package com.haiercash.pluslink.capital.common.utils;/**
 * Created by Administrator on 2018/1/17.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * @author lipengfei
 * @date 2018-01-17 8:51
 * @describe
 **/
public class RestUtil {
    private RestUtil() {
    }

    public static Map<String, Object> fail(String retFlag, String retMsg) {
        Map<String, Object> head = new HashMap();
        head.put("retFlag", retFlag);
        head.put("retMsg", retMsg);
        HashMap<String, Object> result = new HashMap();
        result.put("head", head);
        return result;
    }

    public static Map<String, Object> success() {
        return fail("00000", "处理成功");
    }

    public static Map<String, Object> success(Object body) {
        Map<String, Object> result = fail("00000", "处理成功");
        result.put("body", body);
        return result;
    }
}
