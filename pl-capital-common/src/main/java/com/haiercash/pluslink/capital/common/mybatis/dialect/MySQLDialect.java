package com.haiercash.pluslink.capital.common.mybatis.dialect;

import org.apache.ibatis.dialect.Dialect;

/**
 * Created by Administrator on 2017/2/27.
 */
public class MySQLDialect extends Dialect {
    public MySQLDialect() {

    }

    @Override
    public String getDatabaseName() {
        return "mysql";
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return sql + (offset > 0 ? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : " limit " + limitPlaceholder);
    }
    @Override
    public String charToDate(String column, String format) {
        if (format != null && format.length() != 0 && !format.equals("date")) {
            if (format.equals("datetime")) {
                format = "'%Y-%m-%d %H:%i:%s'";
            } else if (format.equals("time")) {
                format = "'%H:%i:%s'";
            }
        } else {
            format = "'%Y-%m-%d'";
        }

        return "str_to_date(" + column + ", " + format + ")";
    }
}
