package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: HeadResponse
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadResponse implements Serializable {


    //报文流水号
    private String retFlag;

    //返回标志
    private String retMsg;

    //返回消息
    private String serno;
}
