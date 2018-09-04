package com.haiercash.pluslink.capital.processer.server.pvm.handler;

import cn.jbinfo.integration.busflow.bus.Bus;
import cn.jbinfo.integration.busflow.station.StationRoutingWrap;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaobin
 * @create 2018-07-26 下午4:03
 **/
@Slf4j
public class EndHandler extends IHandler implements StationRoutingWrap {

    @Override
    public void invokeStationMethod(Bus bus) {

    }

    @Override
    public String getName() {
        return "流程结束";
    }

    @Override
    public void doBusiness(Bus bus) {
        /*Object methodName = bus.getBusContext().getValue(METHOD_NAME);
        if(Objects.isNull(methodName)){
            //log.info("流程结束");
        }else{
            log.info("{}--结束",methodName);
        }*/
    }
}
