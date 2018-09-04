package com.haiercash.pluslink.capital.processer.server.pvm.handler;

import cn.jbinfo.integration.busflow.context.BusContext;
import com.haiercash.pluslink.capital.processer.server.pvm.log.HandlerLog;

import java.util.LinkedList;

/**
 * @author xiaobin
 * @create 2018-08-06 上午10:29
 **/
public class HandlerManager {

    public static LinkedList<HandlerLog> get(BusContext busContext) {
        Object obj = busContext.getValue(IHandler.HANDLER_LINK_LIST);
        if (obj != null) {
            return (LinkedList<HandlerLog>) obj;
        }
        return null;
    }

}
