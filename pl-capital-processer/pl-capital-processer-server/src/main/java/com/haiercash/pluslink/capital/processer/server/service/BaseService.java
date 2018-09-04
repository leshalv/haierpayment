package com.haiercash.pluslink.capital.processer.server.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author Administrator
 * @Title: BaseService
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/715:10
 */
public abstract class BaseService {

    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    protected final Log logger = LogFactory.getLog(this.getClass());

}
