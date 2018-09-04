package com.haiercash.pluslink.capital.common.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Format util.
 */
public class FormatUtil {

    /**
     * global logger.
     */
    private static Logger logger = LoggerFactory.getLogger(FormatUtil.class);

    /**
     * reuse ObjectMapper.
     */
    private static ObjectMapper jsonMapper = new ObjectMapper();

    static {
        jsonMapper.setSerializationInclusion(Include.NON_EMPTY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonMapper.setDateFormat(sdf);
        DeserializationConfig deCfg = jsonMapper.getDeserializationConfig();
        deCfg.withFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        jsonMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }


    /**
     * 将map中的所有key由驼峰式改为下划线式
     *
     * @param camelKeyMap
     * @return
     */
    public static Map<String, Object> camelKeyToUnderScore(Map<String, Object> camelKeyMap) {
        Map<String, Object> resultMap = new HashMap<>();
        camelKeyMap.forEach((key, value) -> resultMap.put(camelToUnderScore(key), value));
        return resultMap;
    }

    public static Map<String, Object> underScoreKeyToCamel(Map<String, Object> underScoreKeyMap) {
        Map<String, Object> resultMap = new HashMap<>();
        underScoreKeyMap.forEach((key, value) -> resultMap.put(underScoreToCamel(key), value));
        return resultMap;
    }

    /**
     * 将一个驼峰式字符串改为一个下划线式字符串
     *
     * @param camel
     * @return
     */
    public static String camelToUnderScore(String camel) {
        if (StringUtils.isEmpty(camel)) {
            return "";
        }
        char[] chars = camel.toCharArray();
        List<Character> resultList = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                resultList.add('_');
                resultList.add(Character.toLowerCase(chars[i]));
            } else {
                resultList.add(chars[i]);
            }
        }
        StringBuffer sb = new StringBuffer();
        resultList.stream().forEach(ch -> sb.append(ch));
        return sb.toString();
    }

    /**
     * 将一个下划线式的字符串转成驼峰式
     *
     * @param underScore
     * @return
     */
    public static String underScoreToCamel(String underScore) {
        if (StringUtils.isEmpty(underScore)) {
            return "";
        }
        char[] chars = underScore.toCharArray();
        List<Character> resultList = new ArrayList<>();
        boolean isAfterUnderScore = false;
        for (int i = 0; i < chars.length; i++) {
            if (Objects.equals(chars[i], '_')) {
                isAfterUnderScore = true;
            } else {
                if (isAfterUnderScore) {
                    resultList.add(Character.toUpperCase(chars[i]));
                } else {
                    resultList.add(chars[i]);
                }
                isAfterUnderScore = false;
            }
        }
        StringBuffer sb = new StringBuffer();
        resultList.stream().forEach(ch -> sb.append(ch));
        return sb.toString();
    }

    /**
     * 将一个pojo类转换为map
     *
     * @param object
     * @return map
     */
    public static Map<String, Object> obj2Map(Object object) {
        String objJson = JSON.toJSONString(object);
        return JSON.parseObject(objJson, Map.class);
    }

    /**
     * 把一个Object转换成指定pojo类
     *
     * @param object
     * @param clazz
     * @return
     */
    public static <T> T obj2Obj(Object object, Class<T> clazz) {
        String objJson = JSON.toJSONString(object);
        return JSON.parseObject(objJson, clazz);
    }

}
