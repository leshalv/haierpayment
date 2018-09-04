package com.haiercash.pluslink.capital.common.utils;

import java.util.Date;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/14 16:31
 * @Description:
 */
public class SequenceUtil {
    /**
     * 传入枚举code 和 自增序列 生成主键ID
     */
    public static String getSequence(String enumType, String sequence) {
        StringBuffer id = new StringBuffer();
        id.append(enumType);
        id.append(DateUtils.formatDate(new Date()));
        id.append(sequence);
        return id.toString();
    }

    public static String getTransNo(String enumType, String sequence) {
        StringBuffer id = new StringBuffer();
        id.append(enumType);
        id.append(DateUtils.formatDate(new Date(), "yyyyMMdd"));
        id.append(sequence);
        return id.toString();
    }
}
