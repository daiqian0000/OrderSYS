package com.asion.common.mybatis.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import com.asion.common.util.Page;

/**
 * 分页处理器
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("deprecation")
public enum PageHandler {

	/**
	 * 全局单例
	 */
	INSTANCE;
	
	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 设置分页处理
	 * 
	 * @param object
	 *            分页参数对象
	 * @param sql
	 *            SQL语句
	 * @param mappedStatement
	 *            映射预编译处理
	 * @param conn
	 *            数据连接
	 * @param databaseProductName
	 *            数据库产品名称
	 * @return 设置好分页查询表达式的SQL语句
	 */
	public String handler(Object object, String sql, MappedStatement mappedStatement, Connection conn, String databaseProductName) {
		Page<?> page = (Page<?>) object;
		page.setTotalRecord(getTotalRecord(page, sql, mappedStatement, conn));
		return getPageSql(page, sql, databaseProductName);
	}
	
	/**
	 * 获取总记录数
	 * 
	 * @param page
	 *            分页信息
	 * @param mappedStatement
	 *            SQL语句表述信息
	 * @param conn
	 *            数据库连接
	 * @return 总记录数
	 */
	private int getTotalRecord(Page<?> page, String sql, MappedStatement mappedStatement, Connection conn) {
		int count = 0;
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String countSql = "SELECT COUNT(1) FROM (" + sql + ") A";
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, boundSql);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(countSql);
			parameterHandler.setParameters(ps);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e1);
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return count;
	}

	/**
	 * 获取分页SQL语句
	 * 
	 * @param page
	 *            分页信息
	 * @param sql
	 *            SQL语句
	 * @return 分页SQL语句
	 */
	private String getPageSql(Page<?> page, String sql, String databaseProductName) {
		switch (databaseProductName) {
			case "mysql":
			case "postgresql":
				return getPageSqlOfMySQL(page, sql);
			case "oracle":
				return getPageSqlOfOracle(page, sql);
		}
		return null;
	}

	/**
	 * 获取MySQL分页SQL语句
	 * 
	 * @param page
	 *            分页信息
	 * @param sql
	 *            SQL语句
	 * @return MySQL分页SQL语句
	 */
	private String getPageSqlOfMySQL(Page<?> page, String sql) {
		StringBuilder sb = new StringBuilder(sql);
		int offset = (page.getPageIndex() - 1) * page.getPageSize();
		sb.append(" LIMIT ").append(page.getPageSize()).append(" OFFSET ")
				.append(offset);
		return sb.toString();
	}

	/**
	 * 获取ORACLE分页SQL语句
	 * 
	 * @param page
	 *            分页信息
	 * @param sql
	 *            SQL语句
	 * @return ORACLE分页SQL语句
	 */
	private String getPageSqlOfOracle(Page<?> page, String sql) {
		StringBuilder sb = new StringBuilder(sql);
		int offset = (page.getPageIndex() - 1) * page.getPageSize() + 1;
		sb.insert(0, "SELECT PAGE_TABLE.*, ROWNUM R FROM (")
				.append(") PAGE_TABLE WHERE ROWNUM < ")
				.append(offset + page.getPageSize())
				.insert(0, "SELECT * FROM (").append(") WHERE R >= ")
				.append(offset);
		return sb.toString();
	}

}
