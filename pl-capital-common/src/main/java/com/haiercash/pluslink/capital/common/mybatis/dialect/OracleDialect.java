package com.haiercash.pluslink.capital.common.mybatis.dialect;

import org.apache.ibatis.dialect.Dialect;

public class OracleDialect extends Dialect {
	@Override
	public String getDatabaseName() {
		return "oracle";
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
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		if (sql.indexOf(";") > 0) {
		    sql = sql.substring(sql.indexOf(";") + 1);
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("SELECT * FROM ( SELECT A.*, ROWNUM rownum_ FROM ( ");
		pagingSelect.append(sql);
		// int end = offset+limit;
		///String endString = offsetPlaceholder + "+" + limitPlaceholder;
		pagingSelect.append(" ) A WHERE ROWNUM <= " + (offset + limit) + ") WHERE rownum_ > " + offset);
		return pagingSelect.toString();
	}

	@Override
	public String charToDate(String column, String format) {
		if (format == null || format.length() == 0 || format.equals("date")) {
			format = "'yyyy-mm-dd'";
		} else if (format.equals("datetime")) {
			format = "'yyyy-mm-dd hh24:mi:ss'";
		} else if (format.equals("time")) {
			format = "'hh24:mi:ss'";
		}
		return "to_date(" + column + ", " + format + ")";
	}
	@Override
	public String dateToChar(String column, String format) {
		if (format == null || format.length() == 0 || format.equals("date")) {
			format = "'yyyy-mm-dd'";
		} else if (format.equals("datetime")) {
			format = "'yyyy-mm-dd hh24:mi:ss'";
		} else if (format.equals("time")) {
			format = "'hh24:mi:ss'";
		}
		return "to_char(" + column + ", " + format + ")";
	}
}
