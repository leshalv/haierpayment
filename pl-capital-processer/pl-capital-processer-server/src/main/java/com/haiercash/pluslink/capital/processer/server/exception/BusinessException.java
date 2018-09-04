package com.haiercash.pluslink.capital.processer.server.exception;


import cn.jbinfo.api.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 异常类
 * </p>
 *
 * @author xiaobin
 * @Date 2016-01-23
 */
@Getter
@Setter
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    private static final long serialVersionUID = 8604424364318396626L;

    public BusinessException(String code, String message, Throwable throwable) {
        super(code, message, throwable);
    }

    public BusinessException(String code, String message, String serNo, Throwable throwable) {
        super(code, message, serNo, throwable);
    }

}
