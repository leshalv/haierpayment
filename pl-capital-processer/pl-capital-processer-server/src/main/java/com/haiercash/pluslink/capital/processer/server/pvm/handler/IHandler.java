package com.haiercash.pluslink.capital.processer.server.pvm.handler;

import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.integration.busflow.context.BusContext;
import com.google.common.collect.Lists;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.processer.server.pvm.log.HandlerLog;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author xiaobin
 * @create 2018-08-06 上午10:03
 **/
public abstract class IHandler {

    String METHOD_NAME = "method_name";

    public static final String HANDLER_LINK_LIST = "HANDLER_LINK_LIST";

    String CREDIT_APPLY_BACK_ERROR = "CREDIT_APPLY_BACK_ERROR";

    protected void setHandler(BusContext busContext, String applSeq, String handlerName) {
        HandlerLog log = new HandlerLog();
        log.setId(IdGen.uuid());
        log.setApplSeq(applSeq);
        log.setHandlerName(handlerName);
        log.setCreateDate(new Date());

        Object obj = busContext.getValue(HANDLER_LINK_LIST);
        LinkedList<HandlerLog> logLinkedList;
        if (obj == null) {
            logLinkedList = Lists.newLinkedList();
            logLinkedList.add(log);

        } else {
            logLinkedList = (LinkedList<HandlerLog>) obj;
            logLinkedList.add(log);
        }
        busContext.put(HANDLER_LINK_LIST, logLinkedList);
    }
}
