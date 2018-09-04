package com.haiercash.pluslink.capital.common.exception;

import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.ReturnCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Descirption: 资金路由部分业务异常
 * @author WDY
 * 20180626
 */
public class PlCapitalException extends BaseException {

    protected final Log logger = LogFactory.getLog(getClass());

    public PlCapitalException() {
        super();
    }

    public PlCapitalException(String defineCode) {
        super(defineCode);
        logger.error("业务异常，错误码：" + defineCode);
    }

    public PlCapitalException(String defineCode, String defineMessage) {
        super(defineCode, defineMessage);
        logger.error("业务异常，错误码：" + defineCode + ",错误信息：" + defineMessage);
    }

    public PlCapitalException(ReturnCode returnCode) {
        super(
                "status - 【" + returnCode.getStatus() +"】," +
                "code - 【" + returnCode.getCode() + "】",
                returnCode.getDesc());
        logger.error("业务异常，返回状态：" + returnCode.getStatus() + ",错误码：" + returnCode.getCode() + ",错误信息：" + returnCode.getDesc());
    }

    public PlCapitalException(ReturnCode returnCode, String defineMessage) {
        super(
                "status - 【" + returnCode.getStatus() +"】," +
                "code - 【" + returnCode.getCode() + "】",
                StringUtils.isBlank(defineMessage) ? returnCode.getDesc() : (returnCode.getDesc() + " ：" + defineMessage));
        logger.error("业务异常，返回状态：" + returnCode.getStatus() + ",错误码：" + returnCode.getCode() + ",错误信息：" + (returnCode.getDesc() + " ：" + defineMessage));
    }

}
