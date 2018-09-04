package com.haiercash.pluslink.capital.common.ribbon;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * BaseRestClient
 * Created by jkl on 2016/8/16.
 *
 * @com haierchash
 * @update jkl
 */
public abstract class BaseRestClient {
    public Log logger = LogFactory.getLog(getClass());
    public String serviceID;
    public String fallbackUri;
    public String basePath;
    public Boolean ribbonEnable;
    public String ip;
    public String port;
    public String callback;
    @Autowired
    AppManager appManager;
    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private RestTemplate restTemplate;

    protected String getRestUrl(String methord) {
        String url = "http://" + serviceID + basePath + methord;
        return url;
    }


    protected Map<String, Object> restGetMap(String url, int responseCode) {
        try {
            RestTemplate currentRestTemplate = getRestTemplateByConfig();
            ResponseEntity<Map> responseEntity = currentRestTemplate.getForEntity(url, Map.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("RestGet失败：" + e.getMessage());
            return null;
        }
    }

    protected List restGetList(String url, int responseCode) {
        try {
            RestTemplate currentRestTemplate = getRestTemplateByConfig();
            ResponseEntity<List> responseEntity = currentRestTemplate.getForEntity(url, List.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("RestGet失败：" + e.getMessage());
            return null;
        }
    }

    protected <T> T restGetEntity(String url, Class<T> responseType) {
        try {
            RestTemplate currentRestTemplate = getRestTemplateByConfig();
            ResponseEntity<T> responseEntity = currentRestTemplate.getForEntity(url, responseType);
            logger.info("request response entity:" + responseEntity);
            return responseEntity.getBody();
        } catch (Exception e) {
            logger.error("RestGet失败：" + e.getMessage());
            return null;
        }
    }

    protected <T> T postForEntity(String url, Class<T> responseType) {
        T t = this.postForEntity(url, null, responseType);
        return t;
    }

    protected <T> T postForEntity(String url, Object request, Class<T> responseType) {
        RestTemplate currentRestTemplate = getRestTemplateByConfig();
        try {
            HttpEntity<Object> requestEntity = null;
            if (request != null) {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                requestEntity = new HttpEntity<Object>(request, headers);
            }
            ResponseEntity<T> responseEntity = currentRestTemplate.postForEntity(url, requestEntity, responseType);
            logger.info("request response entity:" + responseEntity);
            return responseEntity.getBody();
        } catch (Exception e) {
            logger.error("request " + url + " error", e);
            return null;
        }
    }

    protected String restPost(String url, String data, int responseCode) {
        RestTemplate currentRestTemplate = getRestTemplateByConfig();
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<String> reqE = new HttpEntity<>(data, headers);

            logger.info("RestPost请求url:" + url);
            logger.info("RestPost请求报文:" + data);
            ResponseEntity<String> responseEntity = currentRestTemplate.exchange(url,
                    HttpMethod.POST, reqE, String.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                String result = responseEntity.getBody();
                return result == null ? "" : result;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("RestPost失败：" + e.getMessage(), e);
            return null;
        }
    }

    protected String restGet(String url) {
        RestTemplate currentRestTemplate = getRestTemplateByConfig();
        return currentRestTemplate.getForObject(url, String.class);
    }

    protected String restPut(String url, String data, int responseCode) {
        RestTemplate currentRestTemplate = getRestTemplateByConfig();
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<String> reqE = new HttpEntity<>(data, headers);

            ResponseEntity<String> responseEntity = currentRestTemplate.exchange(url,
                    HttpMethod.PUT, reqE, String.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                String result = responseEntity.getBody();
                return result == null ? "" : result;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("request " + url + " error", e);
            return null;
        }
    }

    protected String restDelete(String url, int responseCode) {
        RestTemplate currentRestTemplate = getRestTemplateByConfig();
        try {
            ResponseEntity<String> responseEntity = currentRestTemplate.exchange(url,
                    HttpMethod.DELETE, null, String.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                String result = responseEntity.getBody();
                return result == null ? "" : result;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("request " + url + " error", e);
            return null;
        }
    }

    /**
     * 根据配置获取restTemplate
     *
     * @return
     */
    private RestTemplate getRestTemplateByConfig() {
        RestTemplate currentRestTemplate;
        if (StringUtils.isEmpty(ribbonEnable)) {
            currentRestTemplate = restTemplate;
        } else {
            if (!ribbonEnable) {
                currentRestTemplate = new RestTemplate();
            } else {
                currentRestTemplate = restTemplate;
            }
        }
        return currentRestTemplate;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getFallbackUri() {
        return fallbackUri;
    }

    public void setFallbackUri(String fallbackUri) {
        this.fallbackUri = fallbackUri;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Boolean getRibbonEnable() {
        return ribbonEnable;
    }

    public void setRibbonEnable(Boolean ribbonEnable) {
        this.ribbonEnable = ribbonEnable;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
