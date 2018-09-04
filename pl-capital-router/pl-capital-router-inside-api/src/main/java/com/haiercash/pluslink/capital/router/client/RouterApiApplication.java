package com.haiercash.pluslink.capital.router.client;

import com.haiercash.pluslink.capital.common.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

/**
 * 应用程序入口
 *
 * @author keliang.jiang
 */
@SpringBootApplication
@ComponentScan("com.haiercash.pluslink.capital")
@Import(SpringUtil.class)
@ImportResource({"classpath*:spring/*"}) //加入spring的bean的xml文件
public class RouterApiApplication {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(RouterApiApplication.class, args);
    }
}
