package com.haiercash.pluslink.capital.router.server.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 资金方综合查询接口(入参参数)
 * @author WDY
 * @date 2018-07-16
 * @rmk
 */
@Data
@Getter
@Setter
public class GeneralInfoMatch implements Serializable{

    /**合作机构主键ID**/
    private String agencyId;
    /**合作项目主键ID**/
    private String projectId;
}
