package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.IdGen;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xiaobin
 * @create 2018-08-20 下午4:11
 **/
@Getter
@Setter
public class LcApplyFileQueryRequest {

    private RequestHead head;

    private LcApplyFileQueryBody body;


    public LcApplyFileQueryRequest() {
        head = new RequestHead();
        head.setTradeCode("100057");
        head.setSerno(IdGen.uuid());
        head.setSysFlag("23");
        head.setTradeDate(DateUtils.format(new Date(), DateUtils.PATTERN_YYYY_MM_DD));
        head.setTradeTime(DateUtils.format(new Date(), "HH:mm:ss"));
        head.setChannelNo("99");
    }

    public LcApplyFileQueryRequest(String applSeq) {
        this();
        body = new LcApplyFileQueryBody();
        body.setSysId("00");
        body.setBusId("LcAppl");
        body.setApplSeq(applSeq);
    }
}
