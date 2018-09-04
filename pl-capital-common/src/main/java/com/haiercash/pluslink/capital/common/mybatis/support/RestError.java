package com.haiercash.pluslink.capital.common.mybatis.support;

import com.haiercash.pluslink.capital.common.utils.RestUtils;

import java.io.Serializable;

public class RestError implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private String retFlag;
    private String retMsg;

    public RestError() {
    }

    public RestError(int code, String message, String retFlag, String retMsg) {
        this.code = code;
        this.message = message;
        this.retFlag = retFlag;
        this.retMsg = retMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public static RestError build(RestUtils.Type errorType, String retMsg) {
        RestError error = new RestError();
        error.setCode(errorType.getCode());
        error.setMessage(errorType.getMessage());
        error.setRetFlag(String.valueOf(errorType.getCode()));
        error.setRetMsg(retMsg);
        return error;
    }

    public static RestError build(RestUtils.Type errorType, String retFlag, String retMsg) {
        RestError error = new RestError();
        error.setCode(errorType.getCode());
        error.setMessage(errorType.getMessage());
        error.setRetFlag(retFlag);
        error.setRetMsg(retMsg);
        return error;
    }

}
