package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件列表
 *
 * @author xiaobin
 * @create 2018-08-20 下午4:45
 **/
@Getter
@Setter
@ToString
public class LcApplyFileQueryList {

    /**
     * 文件路径
     */
    private String attachPath;

    /**
     * 原文件名称
     */
    private String attachName;

    /**
     * 文件名称
     */
    private String attachNameNew;

    /**
     * 是否有效
     * 1：有效
     * 0：无效
     */
    private String state;
}
