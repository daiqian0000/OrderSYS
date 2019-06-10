package com.asion.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * JDBC帮助类
 * 
 * @author chenboayng
 *
 */
public abstract class JdbcUtil {

	/**
	 * 日志记录
	 */
	private static Logger logger = Logger.getLogger(JdbcUtil.class);
	
	/**
	 * 执行SQL查询
	 * 
	 * @param driver
	 *            驱动类
	 * @param url
	 *            连接地址
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param sql
	 *            SQL语句
	 * @return 查询结果
	 */
	public static List<Map<String, Object>> query(String driver, String url, String user, String password, String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> item = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					item.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				list.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.error(e.getMessage(), e);
			try {
				conn.rollback();
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return list;
	}
	
	/**
	 * 执行SQL更新
	 * 
	 * @param driver
	 *            驱动类
	 * @param url
	 *            连接地址
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param sql
	 *            SQL语句
	 * @return 更新记录数
	 */
	public static int update(String driver, String url, String user, String password, String sql) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			logger.error(e.getMessage(), e);
			try {
				conn.rollback();
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

}
