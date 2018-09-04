package com.haiercash.pluslink.capital.processer.server.pvm.log;

import com.haiercash.pluslink.capital.data.ProcesserFlowLog;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author xiaobin
 * @create 2018-08-06 上午10:15
 **/
@Getter
@Setter
public class HandlerLog {

    private String id;

    /**
     * 业务编号
     */
    private String applSeq;

    /**
     * 名称
     */
    private String handlerName;

    /**
     * 即将走向
     */
    private String handlerRouting;

    /**
     * 是否异常（1：异常）
     */
    private String isError;

    /**
     * 异常说明
     */
    private String exception;

    private Date createDate;

    private Integer fdIndex;

    public ProcesserFlowLog build() {
        ProcesserFlowLog log = new ProcesserFlowLog();
        log.setId(id);
        log.setApplSeq(applSeq);
        log.setHandlerName(handlerName);
        log.setHandlerRouting(handlerRouting);
        log.setIsError(isError);
        if (StringUtils.isNotBlank(exception)) {
            log.setException(StringUtils.substring(exception, 0, 990));
        }
        log.setCreateDate(createDate);
        log.setFdIndex(fdIndex);
        return log;
    }
}
