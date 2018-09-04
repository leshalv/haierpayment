package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: ApplInfoAppRequestBody  放款详情forapp接口
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1815:35
 */
@Getter
public class ApplInfoAppRequestBodyTest implements Serializable {

    //申请流水号
    private String applSeq;


    public ApplInfoAppRequestBodyTest() {
    }

    public ApplInfoAppRequestBodyTest(String applSeq) {
        this.applSeq = applSeq;

    }
}
