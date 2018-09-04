package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.common.controller.AbstractController;
import com.haiercash.pluslink.capital.common.utils.RestUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class BaseController extends AbstractController {
    @Value("${common.errorPrefix}")
    private String errorPrefix;
    
    public BaseController(String moduleNo) {
        super(moduleNo);
    }
    
/*    protected Map<String, Object> fail(String errorId) {
        if (!StringUtils.isEmpty(errorId)) {
            SysError sysError = GlobalUtils.sysErrorMap.get(errorId);
            if (sysError != null) {
                return this.fail(errorPrefix + this.getModuleNo() + sysError.getErrorCode(), sysError.getErrorInfo());
            }
        }
        return this.fail(RestUtils.ERROR_UNKNOW_CODE, RestUtils.ERROR_UNKNOW_MSG);
    }*/
    
    protected Map<String, Object> fail(String errorCode, String errorInfo) {
        return RestUtils.fail(errorPrefix + this.getModuleNo() + errorCode, errorInfo);
    }
}
