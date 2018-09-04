package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh  放贷调用其他接口请求体第二层信息
 * @create 2018-07-16 下午1:16
 **/
@Getter
@Setter
public class LoanSecondRequest<RequestHead, E> implements Serializable {
    //请求头
    private RequestHead head;

    //请求体
    private E body;
}
