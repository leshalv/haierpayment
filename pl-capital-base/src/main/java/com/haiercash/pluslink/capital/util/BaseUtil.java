package com.haiercash.pluslink.capital.util;

import cn.jbinfo.common.utils.DateUtils;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * 参数工具类
 * @author WDY
 * @date 2018-07-14
 * @rmk
 */
public class BaseUtil{

    private final static Log logger = LogFactory.getLog(BaseUtil.class);

    /**返回yyyyMMdd**/
    public static String changeDate(Date date){
        return DateUtils.formatDate(date,CommonConstant.DATE_PATTERN_YYYYMMDD);
    }

    /**返回yyyyMM**/
    public static String changeYearMonth(Date date){
        return DateUtils.formatDate(date,CommonConstant.DATE_PATTERN_YYYYMM);
    }
}
