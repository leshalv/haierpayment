package com.haiercash.pluslink.capital.processer.server.utils;

import cn.jbinfo.api.exception.ApiException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;


/**
 * 参数工具类
 *
 * @author lzh 参考路由接口编写
 * @date 2018-07-14
 * @rmk
 */
public class ParamCheckUtil extends BaseService {

    private final static Log logger = LogFactory.getLog(ParamCheckUtil.class);

    /**
     * 参数长度校验
     *
     * @author WDY
     * @date 20180714
     * @rmk
     */
    public static String checkApiParamLength(String param, int length, String message, String serNo) {
        String paramMsg = "(" + param + ")";
        if ("".equals(param.trim()) || length < param.length()) {
            logger.error("【" + serNo + "】" + message + paramMsg + "最大长度不能超过【" + length + "】位");
            throw new ApiException(CommonReturnCodeEnum.OVERLONG_FILED.getCode(), "," + "【" + serNo + "】" + message + paramMsg + "最大长度不能超过【" + length + "】位");
        }
        return param.trim();
    }

    /**
     * 交易流水表检查参数是否为空(BigDecimal验证)
     *
     * @author WDY
     * @date 20180714
     * @rmk
     */
    public static BigDecimal checkApiParamLength(String param, int length, int smallNum, String message, String serNo) {
        String paramMsg = "(" + param + ")";
        BigDecimal bigParam;
        try {
            bigParam = new BigDecimal(param);
        } catch (Exception e) {
            logger.error("【" + serNo + "】" + message + paramMsg + "不能是金额类型");
            throw new ApiException(CommonReturnCodeEnum.OVERLONG_FILED.getCode(), "," + "【" + serNo + "】" + message + paramMsg + "不能是金额类型");
        }
        if ((length + 1 < String.valueOf(bigParam).length()) || bigParam.compareTo(bigParam.setScale(smallNum, BigDecimal.ROUND_DOWN)) != 0) {
            logger.error("【" + serNo + "】" + message + paramMsg + "最大长度不能超过" + length + "位,小数长度不能超过" + smallNum + "位");
            throw new ApiException(CommonReturnCodeEnum.OVERLONG_FILED.getCode(), "," + "【" + serNo + "】" + message + paramMsg + "最大长度不能超过" + length + "位,小数长度不能超过" + smallNum + "位");
        }
        return bigParam;
    }
}
