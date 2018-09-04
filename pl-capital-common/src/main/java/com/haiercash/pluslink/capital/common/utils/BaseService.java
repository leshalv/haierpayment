package com.haiercash.pluslink.capital.common.utils;/**
 * Created by Administrator on 2018/1/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author lipengfei
 * @date 2018-01-17 8:50
 * @describe
 **/
public class BaseService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseService() {
    }

    protected final Map<String, Object> fail(String retFlag, String retMsg) {
        return RestUtil.fail(retFlag, retMsg);
    }

    protected final Map<String, Object> success() {
        return RestUtil.success();
    }

    protected final Map<String, Object> success(Object result) {
        return RestUtil.success(result);
    }

}
