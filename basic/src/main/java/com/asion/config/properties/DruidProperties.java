package com.asion.config.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * Druid数据源配置属性类
 * 
 * @author chenboyang
 *
 */
public class DruidProperties extends DataSourceProperties {

	/**
	 * 初始化时建立物理连接的个数
	 */
	private int initialSize = 1;

	/**
	 * 最小连接池数量
	 */
	private int minIdle = 1;

	/**
	 * 最大连接池数量
	 */
	private int maxActive = 20;

	/**
	 * 获取连接时最大等待时间(单位:毫秒)
	 */
	private long maxWait = 60000L;

	/**
	 * 线程检测连接的间隔时间(单位:毫秒)
	 */
	private long timeBetweenEvictionRunsMillis = 60000L;

	/**
	 * 最小线程连接检测时间(单位:毫秒)
	 */
	private long minEvictableIdleTimeMillis = 30000L;

	/**
	 * 最大池内连接数量
	 */
	private int maxPoolPreparedStatementPerConnectionSize = 100;

	/**
	 * 是否缓存预编译语句
	 */
	private boolean poolPreparedStatements = false;

	/**
	 * 检测连接是否有效SQL语句
	 */
	private String validationQuery = "SELECT 1 FROM DUAL";

	/**
	 * 扩展插件
	 */
	private String filters = "stat";

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

}
