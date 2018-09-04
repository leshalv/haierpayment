package com.haiercash.pluslink.capital.router.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xiaobin
 * @create 2018-08-08 下午1:47
 **/
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getPrimaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
