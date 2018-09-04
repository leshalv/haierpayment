package com.haiercash.pluslink.capital.common.utils;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 请求工具类
 * Created by zhouwushuang on 2017.08.10.
 */
public class ParamCheckUtils {
    /**
     * 请求参数检查
     *
     * @param param
     * @param checkParam
     * @return
     */
    public static void checkIfParamError(Map<String, Object> param, String... checkParam) {
        Stream.of(checkParam).forEach(checkKey -> {
            if (StringUtils.isEmpty(param.get(checkKey))) {
                throw new PlCapitalException("98", "请求参数【" + checkKey + "】不能为空！");
            }
        });
    }
}
