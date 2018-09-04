package com.haiercash.pluslink.capital.common.utils;

import com.haiercash.pluslink.capital.common.resp.BaseResponse;
import com.haiercash.pluslink.capital.common.resp.BaseResponseHead;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 返回报文工具类
 *
 * @author keliang.jiang
 * @date 2018/1/26
 */

public class ResponseUtil {

    private static final Log logger = LogFactory.getLog(ResponseUtil.class);

    private ResponseUtil() {

    }

    public static <T extends BaseResponse> T fail(String retFlag, String retMsg, Class<T> clazz) {
        try {
            T result = clazz.newInstance();
            BaseResponseHead head = new BaseResponseHead();
            head.setRetFlag(retFlag);
            head.setRetMsg(retMsg);
            result.setHead(head);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T extends BaseResponse> T success(Class<T> clazz) {
        return fail("00000", "处理成功", clazz);
    }

}
