package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xiaobin
 * @create 2018-08-20 下午4:40
 **/
@Getter
@Setter
@ToString
public class LcApplyFileQueryResponse {

    private HeadResponse head;

    private LcApplyFileQueryBody body;
}
