package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaobin
 * @create 2018-08-20 下午4:10
 **/
@Getter
@Setter
public class LcApplyFileQueryBody {

    /**
     * 系统标识
     */
    private String sysId;

    /**
     * 业务标识
     */
    private String busId;

    /**
     * 业务流水
     */
    private String applSeq;

}
