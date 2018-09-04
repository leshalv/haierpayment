package com.haiercash.pluslink.capital.manager.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * >
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/08/07 18:04
 */
public interface LogInfoService {
    void insertLog(String logName, String businessType, String operator, String logContent);
}
