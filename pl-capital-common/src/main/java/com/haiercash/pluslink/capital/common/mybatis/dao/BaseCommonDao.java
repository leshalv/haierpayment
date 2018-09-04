package com.haiercash.pluslink.capital.common.mybatis.dao;

import com.haiercash.pluslink.capital.entity.AlreadyPositionIn;
import com.haiercash.pluslink.capital.entity.AlreadyPositionOut;
import org.springframework.stereotype.Repository;

/**
 * 公共数据查询接口
 * @author WDY
 * @date 20180718
 * @rmk
 */
@Repository
public interface BaseCommonDao {

    /**获取机构已放款头寸,当日放款头寸,当月放款头寸**/
    AlreadyPositionOut selecAlreadyPosition(AlreadyPositionIn alreadyPositionIn);

    /**
     * 获取uuid
     * @return [uuid]
     */
    String getUUID();

}
