package com.haiercash.pluslink.capital.router.server.entity;

import com.haiercash.pluslink.capital.data.RouteResultRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * 路由中心处理接口(出参参数)
 * @author WDY
 * @date 2018-07-16
 * @rmk
 */
@Data
@Getter
@Setter
public class RouterResult implements Serializable{

    /**路由结果**/
    private RouteResultRecord routeResultRecord;
    /**合作机构名称**/
    private String agencyName;
    /**合作项目名称**/
    private String projectName;
}
