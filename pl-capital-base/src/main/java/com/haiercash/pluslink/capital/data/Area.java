package com.haiercash.pluslink.capital.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 省市区
 * <p>
 * 区域信息
 *
 * @author xiaobin
 * @create 2018-07-19 下午1:52
 **/
@Getter
@Setter
public class Area implements Serializable {

    @Id
    private String id;

    private String areaName;

    private String parentCode;
}
