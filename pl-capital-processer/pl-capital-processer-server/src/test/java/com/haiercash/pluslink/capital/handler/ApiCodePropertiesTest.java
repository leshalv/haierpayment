package com.haiercash.pluslink.capital.handler;

import cn.jbinfo.api.handler.ApiCode;
import cn.jbinfo.api.handler.ApiCodeProperties;
import cn.jbinfo.common.utils.JsonUtils;
import com.haiercash.pluslink.capital.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiaobin
 * @create 2018-07-14 下午3:48
 **/
public class ApiCodePropertiesTest extends BaseTest {

    @Autowired
    private ApiCodeProperties apiCodeProperties;

    @Test
    public void testLoadValue(){
        ApiCode apiCode = apiCodeProperties.getSuccess();
        System.out.println(JsonUtils.writeObjectToJson(apiCode));
    }
}
