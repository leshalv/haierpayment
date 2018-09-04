package com.haiercash.pluslink.capital.processer.server.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobin
 * @create 2018-07-16 下午5:40
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Map<String, HttpMessageConverter<?>> defaultConverters = getDefaultMessageConverters();
        MappingJackson2HttpMessageConverter scalaConverter = createJsonMessageConverterWithScalaSupport();
        defaultConverters.put(scalaConverter.getClass().toString(), scalaConverter);
        converters.addAll(defaultConverters.values());
    }

    private Map<String, HttpMessageConverter<?>> getDefaultMessageConverters() {
        RestTemplate dummyTemplate = new RestTemplate();
        Map<String, HttpMessageConverter<?>> defaultConverters = Maps.newHashMap();
        dummyTemplate.getMessageConverters().forEach(c -> defaultConverters.put(c.getClass().toString(), c));

        return defaultConverters;
    }

    private MappingJackson2HttpMessageConverter createJsonMessageConverterWithScalaSupport() {
        ObjectMapper mapper = createObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.registerModule(new DefaultScalaModule());
        MappingJackson2HttpMessageConverter converter = createJacksonMessageConverter();
        converter.setObjectMapper(mapper);
        return converter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter createJacksonMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
