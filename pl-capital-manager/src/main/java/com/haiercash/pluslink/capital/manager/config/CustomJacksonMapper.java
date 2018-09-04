package com.haiercash.pluslink.capital.manager.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiercash.pluslink.capital.common.mybatis.support.CustomJacksonModule;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

/**
 * Created by lihua on 2018/1/6.
 */
@Configuration
public class CustomJacksonMapper extends ObjectMapper {
    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @PostConstruct
    public void configMapper () {
        // configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        if (!StringUtils.isEmpty(dateFormat)) {
            setDateFormat(new SimpleDateFormat(dateFormat));
        }

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        registerModule(new CustomJacksonModule());
    }
}
