package com.haiercash.pluslink.capital.router.server.biz;

import com.haiercash.pluslink.capital.router.server.entity.GeneralInfoMatch;
import com.haiercash.pluslink.capital.router.server.entity.GeneralInfoResult;

/**
 * API对外查询服务公共接口类
 * @author WDY
 * 2018-07-16
 */
public interface IApiQueryBiz{

    /**资金方综合查询**/
    GeneralInfoResult querGeneralInfo(GeneralInfoMatch generalInfoMatch,String serNo);
}
