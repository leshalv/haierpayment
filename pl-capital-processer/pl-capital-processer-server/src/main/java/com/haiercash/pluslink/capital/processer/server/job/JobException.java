package com.haiercash.pluslink.capital.processer.server.job;

/**
 * @author xiaobin
 * @create 2018-08-21 上午10:24
 **/
public class JobException extends RuntimeException {

    public JobException(String message) {
        super(message);
    }

    public JobException(Throwable throwable) {
        super(throwable);
    }

    public JobException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
