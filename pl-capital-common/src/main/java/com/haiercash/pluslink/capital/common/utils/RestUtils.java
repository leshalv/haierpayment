package com.haiercash.pluslink.capital.common.utils;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.haiercash.pluslink.capital.common.mybatis.support.ResultHead;
import com.haiercash.pluslink.capital.common.mybatis.support.ResultHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public abstract class RestUtils {
    public static Log logger = LogFactory.getLog(RestUtils.class);

    private static String SUCCESS_CODE = "00000";
    private static String SUCCESS_MSG = "处理成功";

    public static String ERROR_INTERNAL_CODE = "99";
    public static String ERROR_INTERNAL_MSG = "网络通讯异常";

    public static String ERROR_UNKNOW_CODE = "98";
    public static String ERROR_UNKNOW_MSG = "未知错误";

    public static Map<String, Object> success() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResultHead(SUCCESS_CODE, SUCCESS_MSG));
        return resultMap;
    }

    public static Map<String, Object> success(Object result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResultHead(SUCCESS_CODE, SUCCESS_MSG));
        resultMap.put("body", result);
        return resultMap;
    }

    public static Map<String, Object> successShow(String showMsg) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResultHead(SUCCESS_CODE, SUCCESS_MSG, showMsg));
        return resultMap;
    }

    public static Map<String, Object> successShow(String showMsg, Object result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResultHead(SUCCESS_CODE, SUCCESS_MSG, showMsg));
        resultMap.put("body", result);
        return resultMap;
    }

    public static Map<String, Object> fail(String retFlag, String retMsg) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResultHead(retFlag, retMsg));
        return resultMap;
    }

    public static boolean isSuccess(Map<String, Object> resultMap) {
        if (resultMap == null || resultMap.get("head") == null || ((Map<String, Object>) resultMap.get("head")).get("retFlag") == null) {
            return false;
        }
      return ((Map<String, Object>) resultMap.get("head")).get("retFlag").equals(SUCCESS_CODE);
    }

    public static String getString(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject != null && jsonObject.has(key)) {
            return String.valueOf(jsonObject.get(key));
        }
        return null;
    }

    public static JSONObject getObject(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.getJSONObject(key);
        }
        return null;
    }

    public static String getString(Map<String, Object> map, String key) {
        if (map != null) {
            return String.valueOf(map.get(key));
        }
        return null;
    }

    public static Map<String, Object> getObject(Map<String, Object> map, String key) {
        if (map != null) {
            return (Map<String, Object>) map.get(key);
        }
        return null;
    }

    public static String getGuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getSerial() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.getTimeInMillis()) + String.valueOf((int) (Math.random() * 1000));
    }

    public static String restGetString(String url, int responseCode) {
        try {
            RestTemplate restTemplate = initRestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
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
    public static String restPostString(String url, String data, int responseCode) {
        return restExchangeString(url, data, HttpMethod.POST, responseCode);
    }
    public static String restPutString(String url, String data, int responseCode) {
        return restExchangeString(url, data, HttpMethod.PUT, responseCode);
    }
    public static String restDeleteString(String url, int responseCode) {
        return restExchangeString(url, null, HttpMethod.DELETE, responseCode);
    }
    public static String restExchangeString(String url, String data, HttpMethod httpMethod, int responseCode) {
        try {
            HttpEntity<String> reqE = null;
            if (data != null) {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                reqE = new HttpEntity<>(data, headers);
            }
            RestTemplate restTemplate = initRestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, reqE, String.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("RestExchange失败：" + e.getMessage());
            return null;
        }
    }
    public static Map<String, Object> restGetMap(String url, int responseCode) {
        try {
            RestTemplate restTemplate = initRestTemplate();
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
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
    public static Map<String, Object> restPostMap(String url, Map<String, Object> data, int responseCode) {
        return restExchangeMap(url, data, HttpMethod.POST, responseCode);
    }
    public static Map<String, Object> restPutMap(String url, Map<String, Object> data, int responseCode) {
        return restExchangeMap(url, data, HttpMethod.PUT, responseCode);
    }
    public static Map<String, Object> restDeleteMap(String url, int responseCode) {
        return restExchangeMap(url, null, HttpMethod.DELETE, responseCode);
    }
    public static Map<String, Object> restExchangeMap(String url, Map<String, Object> data, HttpMethod httpMethod, int responseCode) {
        try {
            HttpEntity<Map<String, Object>> reqE = null;
            if (data != null) {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                reqE = new HttpEntity<>(data, headers);
            }
            RestTemplate restTemplate = initRestTemplate();

            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, httpMethod, reqE, Map.class);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("RestExchange失败：" + e.getMessage());
            return null;
        }
    }

    public static <T> T restGet(Class<T> cls, String url, int responseCode) {
        try {
            RestTemplate restTemplate = initRestTemplate();
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, cls);
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
    public static <T> T restPost(Class<T> cls, String url, Map<String, Object> data, int responseCode) {
        return restExchange(cls, url, data, HttpMethod.POST, responseCode);
    }
    public static <T> T restPut(Class<T> cls, String url, Map<String, Object> data, int responseCode) {
        return restExchange(cls, url, data, HttpMethod.PUT, responseCode);
    }
    public static <T> T restDelete(Class<T> cls, String url, int responseCode) {
        return restExchange(cls, url, null, HttpMethod.DELETE, responseCode);
    }
    public static <T> T restExchange(Class<T> cls, String url, Map<String, Object> data, HttpMethod httpMethod, int responseCode) {
        try {
            HttpEntity<Map<String, Object>> reqE = null;
            if (data != null) {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                reqE = new HttpEntity<>(data, headers);
            }
            RestTemplate restTemplate = initRestTemplate();

            ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, reqE, cls);
            HttpStatus status = responseEntity.getStatusCode();
            if (status.value() == responseCode) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("RestExchange失败：" + e.getMessage());
            return null;
        }
    }

    public static  <T> T postForEntity(String url, Object request, Class<T> responseType) {
        try {
            HttpEntity<Object> requestEntity=null ;
            if(request!=null){
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                requestEntity = new HttpEntity<Object>(request, headers);
            }

            RestTemplate restTemplate = RestUtils.initRestTemplate();
            ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestEntity, responseType);
            HttpStatus status = responseEntity.getStatusCode();
            logger.info("request response entity:"+responseEntity);
            //if (status.value() == responseCode) {
            return responseEntity.getBody();
            //} else {
            //     return null;
            // }
        } catch (Exception e) {
            logger.error("request " + url + " error", e);
            return null;
        }

    }
    public static  <T> T postForEntity(String url, Class<T> responseType) {
        T t = postForEntity(url, null, responseType);
        return t;
    }

    public static RestTemplate initRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FastJsonHttpMessageConverter4());
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    public enum Type {
        ERROR_REQUEST(9001, "请求参数异常"),
        ERROR_VALIDATION(9002, "请求非法"),
        ERROR_INTERNAL(9003, "网络通讯异常"),
        ERROR_BUSINESS(9004, "业务校验异常");

        private int code;
        private String message;

        Type(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
