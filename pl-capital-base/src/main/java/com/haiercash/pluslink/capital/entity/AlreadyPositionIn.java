package com.haiercash.pluslink.capital.entity;

import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import com.haiercash.pluslink.capital.util.BaseUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询机构项目已放款头寸实体(入参)
 * @author WDY
 * @date 2018-07-19
 * @rmk
 */
@Data
@Getter
@Setter
public class AlreadyPositionIn implements Serializable{

    /**当月**/
    private String curYearMonth;
    /**当日**/
    private String curYearMonthDay;
    /**项目编号**/
    private String projectId;
    /**交易状态**/
    private String delFlag;
    /**删除状态**/
    private List<String> statusList;

    public AlreadyPositionIn(){};

    public AlreadyPositionIn(String projectId,Date date){
        this.curYearMonth = BaseUtil.changeYearMonth(date);
        this.curYearMonthDay = BaseUtil.changeDate(date);
        this.projectId = projectId;
        this.delFlag = PL0101Enum.PL0101_2_NORMAL.getCode();
        this.statusList = PL0601Enum.QUERY_LIMIT.getCodes();
    }
}
