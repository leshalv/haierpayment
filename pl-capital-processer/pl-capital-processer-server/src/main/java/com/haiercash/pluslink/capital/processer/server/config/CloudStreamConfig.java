package com.haiercash.pluslink.capital.processer.server.config;

import com.haiercash.pluslink.capital.processer.server.stream.queue.ConsumerProcessor;
import com.haiercash.pluslink.capital.processer.server.stream.queue.ProducerProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

/**
 * SpringCloudStream配置类
 *
 * @author keliang.jiang
 * @date 2017/12/17
 */

@Configuration
@EnableBinding({ConsumerProcessor.class, ProducerProcessor.class})
@IntegrationComponentScan
public class CloudStreamConfig {

}
