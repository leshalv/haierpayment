package com.haiercash.pluslink.capital.router.client.impl;

import cn.jbinfo.common.rest.RestClient;
import cn.jbinfo.common.rest.RestClientException;
import com.haiercash.pluslink.capital.router.client.ICommonSendClient;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 路由中心处理调用实现类
 * @author WDY
 * @date 2018-07-04
 * @rmk
 */
@Service("commonSendClient")
public class CommonSendClientImpl implements ICommonSendClient {

    public Log logger = LogFactory.getLog(CommonSendClientImpl.class);

    /**通知路由调用接口**/
    public Map<String, Object> routerMatch(Object request,String server,String port,String url,String charaCode){
        Map<String, Object> map = createRouterSend(request,server,port,url,charaCode);
        return map;
    }


    private Map<String, Object> createRouterSend(Object request,String server,String port,String url,String charaCode){
        Map<String, Object> map = new HashedMap();
        try{
            map = commonDeal(request,server,port,url,charaCode);
        } catch (Exception e) {
            logger.error("消息发送失败", e);
        }
        return map;
    }

    private Map<String,Object> commonDeal(Object request,String server,String port,String serverUrl,String charaCode){
        Map<String,String> urlMap = new HashMap();
        String url = "http://" + server + ":"+ port + serverUrl;
        logger.info("客户端调用服务端访问接口地址为：" + url);
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, Object> map = exchange(url,HttpMethod.POST, httpHeaders,request,Map.class,urlMap);
        for (String key : map.keySet()) {
            logger.info("key=" + key + ",value=" + map.get(key));
        }
        return map;
    }

    /**
     * 执行请求
     *
     * @param url          请求地址
     * @param method       请求方式
     * @param responseType 返回的数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     */
    public static <T> T exchange(String url, HttpMethod method, HttpHeaders httpHeaders, Object body, Class<T> responseType,Object... uriVariables) throws RestClientException {
        return RestClient.exchange(url, method, httpHeaders, body, responseType, uriVariables);
    }
}
