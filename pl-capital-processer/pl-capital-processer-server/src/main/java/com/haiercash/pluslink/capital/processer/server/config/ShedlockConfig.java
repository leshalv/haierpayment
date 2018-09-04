package com.haiercash.pluslink.capital.processer.server.config;

import cn.jbinfo.integration.shedlock.redis.JedisClusterLockProvider;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

import java.time.Duration;

/**
 * @author xiaobin
 * @create 2018-07-28 上午10:10
 **/
@Configuration
public class ShedlockConfig {

    @Autowired
    private JedisCluster jedisCluster;

    @Bean
    public ScheduledLockConfiguration taskScheduler(LockProvider lockProvider) {
        return ScheduledLockConfigurationBuilder
                .withLockProvider(lockProvider)
                .withPoolSize(10)
                .withDefaultLockAtMostFor(Duration.ofMinutes(10))
                .build();
    }

    @Bean
    public JedisClusterLockProvider lockProvider() {
        return new JedisClusterLockProvider(jedisCluster);
    }

}
