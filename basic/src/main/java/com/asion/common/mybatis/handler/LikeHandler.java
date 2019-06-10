package com.asion.common.mybatis.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * LIKE表达式处理器
 * 
 * @author chenboyang
 * 
 */
public enum LikeHandler {

	/**
	 * 全局单例
	 */
	INSTANCE;
	
	/**
	 * 设置LIKE查询表达式处理
	 * 
	 * @param sql
	 *            SQL语句
	 * @param databaseProductName
	 *            数据库产品名称
	 * @return 设置好LIKE查询表达式的SQL语句
	 */
	public String handler(String sql, String databaseProductName) {
		String regex = "(%\\?%|%\\?|\\?%)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String oldText = matcher.group().trim();
			String newText = "";
			switch (databaseProductName) {
				case "mysql":
					newText = mysqlLikeJoin(oldText);
					break;
				case "postgresql":
				case "oracle":
					newText = oracleLikeJoin(oldText);
					break;
				case "sqlserver":
					newText = sqlserverLikeJoin(oldText);
					break;
			}
			newText = newText.replace("%", "'%'");
			sql = sql.replace(oldText, newText);
		}
		return sql;
	}

	/**
	 * MySQL的LIKE表达式拼装
	 * 
	 * @param text
	 *            原始文本
	 * @return LIKE表达式
	 */
	private String mysqlLikeJoin(String text) {
		StringBuilder sb = new StringBuilder("CONCAT(");
		sb.append(StringUtils.join(text.trim().split(""), ", "));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Oracle的LIKE表达式拼装
	 * 
	 * @param text
	 *            原始文本
	 * @return LIKE表达式
	 */
	private String oracleLikeJoin(String text) {
		return StringUtils.join(text.trim().split(""), " || ");
	}

	/**
	 * SqlServer的LIKE表达式拼装
	 * 
	 * @param text
	 *            原始文本
	 * @return LIKE表达式
	 */
	private String sqlserverLikeJoin(String text) {
		return StringUtils.join(text.trim().split(""), " + ");
	}

}
