package com.haiercash.pluslink.capital.common.mybatis.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 配置信息实现类，支持自动注入 Created by Liuhongbin on 2016/4/7.
 */
@ConfigurationProperties(prefix = "common")
@Component
public class CommonProperties {
    private static Map<String, Object> sign;
    private static Map<String, Object> redis;
    private static Map<String, Object> address;
    private static Map<String, Object> path;
    private static Map<String, Object> file;
    private static Map<String, Object> other;
    private static Map<String, Object> sms;
    private static Map<String, Object> app;

    public Map<String, Object> getSign() {
        return sign;
    }

    public void setSign(Map<String, Object> sign) {
        CommonProperties.sign = sign;
    }

    public Map<String, Object> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, Object> redis) {
        CommonProperties.redis = redis;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        CommonProperties.address = address;
    }

    public Map<String, Object> getPath() {
        return path;
    }

    public void setPath(Map<String, Object> path) {
        CommonProperties.path = path;
    }

    public Map<String, Object> getFile() {
        return file;
    }

    public void setFile(Map<String, Object> file) {
        CommonProperties.file = file;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        CommonProperties.other = other;
    }

    public Map<String, Object> getSms() {
        return sms;
    }

    public void setSms(Map<String, Object> sms) {
        CommonProperties.sms = sms;
    }

    public Map<String, Object> getApp() {
        return app;
    }

    public void setApp(Map<String, Object> app) {
        CommonProperties.app = app;
    }

    public static Object get(String key) {
        if (key == null || key.equals("")) {
            return null;
        }
        if (key.contains(".")) {
            String[] keys = key.split("\\.");
            if (keys.length == 2) {
                Map<String, Object> map = (Map<String, Object>) get(keys[0]);
                if (map != null) {
                    return map.get(keys[1]);
                }
            }
        } else if (key.equals("sign")) {
            return sign;
        } else if (key.equals("redis")) {
            return redis;
        } else if (key.equals("address")) {
            return address;
        } else if (key.equals("path")) {
            return path;
        } else if (key.equals("file")) {
            return file;
        } else if (key.equals("other")) {
            return other;
        } else if (key.equals("sms")) {
            return sms;
        } else if (key.equals("app")) {
            return app;
        }
        return null;
    }
}
