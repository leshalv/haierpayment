package com.haiercash.pluslink.capital.router.server;

import cn.jbinfo.api.handler.ApiCodeProperties;
import cn.jbinfo.conf.ActionEnhanceProperties;
import cn.jbinfo.reactor.log.conf.LogActionProperties;
import com.haiercash.common.jedis.cluster.EnableHcJedis;
import com.haiercash.pluslink.capital.common.utils.SpringUtil;
import com.haiercash.pluslink.capital.router.server.version.VersionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
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
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient  //  开启Eureka发现
@EnableHcJedis
@EnableFeignClients
@ComponentScan("com.haiercash.pluslink.capital")
@Import(SpringUtil.class)
@ImportResource({"classpath:spring/*"}) //加入spring的bean的xml文件
@EnableConfigurationProperties({ApiCodeProperties.class, ActionEnhanceProperties.class, LogActionProperties.class})
public class CapitalRouterApplication{

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        ApplicationContext ctx =  SpringApplication.run(CapitalRouterApplication.class, args);
        afterAppBoot(ctx);    }

    public static void afterAppBoot(ApplicationContext ctx){
        VersionConfig version = (VersionConfig)ctx.getBean("routerVersionConfig");
        version.configVersionAfterBoot();
    }
}
