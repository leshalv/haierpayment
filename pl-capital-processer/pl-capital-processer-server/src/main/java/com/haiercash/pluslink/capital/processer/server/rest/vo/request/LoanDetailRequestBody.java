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
public class LoanDetailRequestBody implements Serializable {

    //申请流水号
    private String applSeq;
    //证件号码
    private String certNo;
    //渠道号
    private String  channelNo;

    public LoanDetailRequestBody() {
    }

    public LoanDetailRequestBody(String applSeq, String certNo, String channelNo) {
        this.applSeq = applSeq;
        this.certNo = certNo;
        this.channelNo=channelNo;
    }
}
