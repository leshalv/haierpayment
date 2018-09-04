package com.haiercash.pluslink.capital.processer.server;

import cn.jbinfo.api.handler.ApiCodeProperties;
import cn.jbinfo.conf.ActionEnhanceProperties;
import cn.jbinfo.reactor.log.conf.LogActionProperties;
import com.haiercash.common.jedis.cluster.EnableHcJedis;
import com.haiercash.pluslink.capital.common.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * 应用程序入口
 *
 * @author keliang.jiang
 */

@ServletComponentScan
@Import(SpringUtil.class)
@ComponentScan("com.haiercash.pluslink.capital")
@SpringBootApplication
@EnableDiscoveryClient  //开启Eureka发现
@EnableHcJedis
@EnableFeignClients
@EnableAsync
@EnableTransactionManagement
@EnableConfigurationProperties({ApiCodeProperties.class, ActionEnhanceProperties.class, LogActionProperties.class})
@ImportResource({"classpath:spring/*"}) //加入spring的bean的xml文件
public class CapitalProcesserApplication {

    /**
     * 自定义异步线程池
     *
     * @return
     */
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Anno-Executor");
        executor.setMaxPoolSize(10);
        executor.setCorePoolSize(10);
        return executor;
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CapitalProcesserApplication.class, args);
    }
}
