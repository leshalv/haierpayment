package com.haiercash.pluslink.capital.common.mybatis.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.redis")
@Component
public class RedisProperties {
    private static String host;
    private static String port;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        RedisProperties.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        RedisProperties.port = port;
    }
}
