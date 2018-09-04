package com.haiercash.pluslink.capital.common.controller;

import com.haiercash.pluslink.capital.common.mybatis.support.RestError;
import com.haiercash.pluslink.capital.common.utils.RestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public abstract class AbstractController {
    public Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    protected HttpServletRequest httpServletRequest;
    @Autowired
    protected HttpServletResponse httpServletResponse;
    @Autowired
    protected HttpSession httpSession;

    private String moduleNo;
    @Value("${common.address.gateUrl:}")
    private String gateUrl;

    public AbstractController() {
    }

    public AbstractController(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    protected String getGateUrl() {
        return gateUrl;
    }

    protected Map<String, Object> success() {
        return RestUtils.success();
    }

    protected Map<String, Object> success(Object result) {
        return RestUtils.success(result);
    }

    protected Map<String, Object> successShow(String showMsg) {
        return RestUtils.successShow(showMsg);
    }

    protected Map<String, Object> successShow(String showMsg, Object result) {
        return RestUtils.successShow(showMsg, result);
    }

    protected boolean isSuccess(Map<String, Object> resultMap) {
        return RestUtils.isSuccess(resultMap);
    }

    protected Map<String, Object> getUser() {
        try {
            return (Map<String, Object>) httpSession.getAttribute("portal_user");
        } catch (Exception e) {
            return null;
        }
    }

    protected Object getUser(String property) {
        Map<String, Object> userMap = getUser();
        if (userMap != null && property != null) {
            return userMap.get(property);
        }
        return null;
    }

    protected List<Map<String, Object>> getRole(String appId) {
        try {
            Map<String, Object> map = (Map<String, Object>) httpSession.getAttribute("portal_roles");
            if (map != null) {
                return (List<Map<String, Object>>) map.get(appId);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    protected Object getRole(String appId, int index, String property) {
        List<Map<String, Object>> roleList = getRole(appId);
        if (roleList != null && index >= 0 && property != null) {
            Map<String, Object> roleMap = roleList.get(index);
            if (roleMap != null) {
                return roleMap.get(property);
            }
        }
        return null;
    }

    public String getModuleNo() {
        return moduleNo;
    }

    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    /**
     * Controller参数异常
     * Post、Put、Patch请求@Valid引起
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e);
        BindingResult errors = e.getBindingResult();
        StringBuilder strBuilder = new StringBuilder();
        for (FieldError fieldError : errors.getFieldErrors()) {
            strBuilder.append(fieldError.getDefaultMessage() + "\n");
        }
        RestError restError = RestError.build(RestUtils.Type.ERROR_REQUEST, strBuilder.toString());
        return new ResponseEntity<RestError>(restError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Entity中的校验异常
     */
/*    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e);
        Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : errors) {
            strBuilder.append(violation.getDesc() + "\n");
        }
        RestError restError = RestError.build(RestUtil.Type.ERROR_VALIDATION, strBuilder.toString());
        return new ResponseEntity<>(restError, HttpStatus.BAD_REQUEST);
    }*/

    /**
     * Controller参数异常
     * Get请求@RequestParam引起
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error(e);
        RestError restError = RestError.build(RestUtils.Type.ERROR_REQUEST, e.getLocalizedMessage());
        return new ResponseEntity<RestError>(restError, HttpStatus.BAD_REQUEST);
    }

    /**
     * 其他未处理的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<RestError> handleException(Exception e) {
        logger.error(e);
        logger.error(e.getCause());
        RestError restError = RestError.build(RestUtils.Type.ERROR_INTERNAL, e.getMessage());
        return new ResponseEntity<RestError>(restError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
