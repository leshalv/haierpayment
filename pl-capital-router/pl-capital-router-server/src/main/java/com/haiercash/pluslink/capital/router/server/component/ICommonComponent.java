package com.haiercash.pluslink.capital.router.server.component;

import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchIn;

/**
 * 为资金路由提供基本公共接口
 * @author WDY
 * 2018-06-07
 */
public interface ICommonComponent {

    /**生成统一的流水号**/
    String doGeneratorCommonSerialNo(SerialNoEnum serialNoEnum,String serNo);


    /**获取头寸拆分的入参数据**/
    PositionSplitIn getPositionSplitParam(RouterMatchIn routerMatch, CooperationProject project);
}
