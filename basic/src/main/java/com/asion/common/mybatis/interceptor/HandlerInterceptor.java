package com.asion.common.mybatis.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

import com.asion.common.mybatis.handler.LikeHandler;
import com.asion.common.mybatis.handler.PageHandler;
import com.asion.common.util.Page;
import com.asion.common.util.ReflectUtil;

/**
 * Mybatis各类SQL业务操作拦截器
 * 
 * @author chenboyang
 * 
 */
@SuppressWarnings("deprecation")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class HandlerInterceptor implements Interceptor {

	/**
	 * 日志记录
	 */
	protected Logger logger = Logger.getLogger(getClass());

	/**
	 * 拦截方法
	 * 
	 * @param invocation
	 *            调用执行器
	 * @return 执行结果
	 * @throws Throwable
	 *             所有异常
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
		BoundSql boundSql = delegate.getBoundSql();
		String sql = boundSql.getSql();
		Connection conn = (Connection) invocation.getArgs()[0];
		String databaseProductName = conn.getMetaData().getDatabaseProductName().toLowerCase();
		Object object = boundSql.getParameterObject();
		if (sql.toUpperCase().contains("LIKE")) {
			sql = LikeHandler.INSTANCE.handler(sql, databaseProductName);
		}
		if (object instanceof Page<?>) {
			sql = PageHandler.INSTANCE.handler(object, sql, mappedStatement, conn, databaseProductName);
		}
		ReflectUtil.setFieldValue(boundSql, "sql", sql);
		return invocation.proceed();
	}

	/**
	 * 插件封装
	 * 
	 * @param object
	 *            封装对象
	 * @return 封装结果
	 */
	public Object plugin(Object object) {
		return Plugin.wrap(object, this);
	}

	/**
	 * 设置属性
	 * 
	 * @param properties
	 *            属性信息
	 */
	public void setProperties(Properties properties) {

	}

}
