package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import cn.jbinfo.common.utils.MD5Util;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: ApplInfoAppRequestBody  放款详情forapp接口
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1815:35
 */
@Getter
public class ApplInfoAppRequestBody implements Serializable {

    //申请流水号
    private String applSeq;
    //证件号码
    private String certNo;
    //渠道号
    private String channelNo;
    //请求ip
    private String requestIp;

    public ApplInfoAppRequestBody() {
    }

    public ApplInfoAppRequestBody(String applSeq, String certNo, String channelNo, String requestIp) {
        this.applSeq = applSeq;
        this.certNo = certNo;
        this.channelNo = channelNo;
        this.requestIp = requestIp;
    }

    public String toString() {
        return MD5Util.getMD5String(StringUtils.join(applSeq, certNo, channelNo, requestIp));
    }
}
