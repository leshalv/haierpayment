package com.haiercash.pluslink.capital.common.exception;

import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.RedisStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Descirption: 资金路由部分业务异常
 * @author WDY
 * 20180626
 */
public class RedisException extends BaseException {

    protected final Log logger = LogFactory.getLog(getClass());

    public RedisException(RedisStatusEnum returnCode, String defineMessage) {
        super(
                "status - 【" + returnCode.getStatus() +"】," +
                        "code - 【" + returnCode.getCode() + "】",
                StringUtils.isBlank(defineMessage) ? returnCode.getDesc() : (returnCode.getDesc() + " ：" + defineMessage));
        logger.error("业务异常，返回状态：" + returnCode.getStatus() + ",错误码：" + returnCode.getCode() + ",错误信息：" + (returnCode.getDesc() + " ：" + defineMessage));
    }
}
