package com.haiercash.pluslink.capital.common.ribbon;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

/**
 * AppManager
 * Created by jkl on 2016/8/15.
 *
 * @com haierchash
 * @update jkl
 */
@Component
public class AppManager {
    private static final Log logger = LogFactory.getLog(AppManager.class);
    //    private static final Logger log = LoggerFactory.getLogger(AppManager.class);
    /*@Autowired
    RestTemplate client;
    *//**
     * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
     *
     * @return
     *//*
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return template;
    }*/
    @Autowired
    private LoadBalancerClient loadBalancer;

    public URI getRestUrl(String serviceId, String fallbackUri) {
        URI uri = null;
        try {
            ServiceInstance instance = loadBalancer.choose(serviceId);
            uri = instance.getUri();
            logger.info("ribbon获取到服务serviceId=" + serviceId + "get uri:" + uri);
        } catch (Exception e) {
            logger.error("ribbon获取服务错误serviceId=" + serviceId + ",将采用备用地址：" + fallbackUri, e);
            uri = URI.create(fallbackUri);
        }

        return uri;
    }

    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

    public <T> T json2Object(ResponseEntity<String> response, Class<T> clazz) {
        try {

            return JSON.parseObject(response.getBody(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public <T> List<T> json2Objects(ResponseEntity<String> response, Class<T> clazz) {
        try {

            return JSON.parseArray(response.getBody(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
