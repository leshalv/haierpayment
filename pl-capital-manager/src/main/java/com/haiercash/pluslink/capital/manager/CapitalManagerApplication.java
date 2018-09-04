package com.haiercash.pluslink.capital.manager;

import com.haiercash.common.jedis.cluster.EnableHcJedis;
import com.haiercash.pluslink.capital.common.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;



/**
 * 消息平台管理后台
 *
 * @author keliang.jiang
 * @date 2017/12/25
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableHcJedis
@ServletComponentScan
@Import(SpringUtil.class)
@ComponentScan("com.haiercash.pluslink.capital")
public class CapitalManagerApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(@Autowired RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CapitalManagerApplication.class, args);
    }

}
