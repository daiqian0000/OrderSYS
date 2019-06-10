package com.asion.common.db;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <h1>数据源动态代理</h1>
 * <p>
 * 动态代理数据源主要用于动态创建、设置、调用、管理数据源
 * </p>
 * 
 * @author chenboyang
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 默认数据源名称
	 */
	private static final String DEFAULT_KEY = "dataSource";

	/**
	 * 当前数据源名称
	 */
	private static final ThreadLocal<String> KEY = new ThreadLocal<String>();

	/**
	 * 数据源池
	 */
	private Map<Object, Object> _targetDataSources;

	/**
	 * 获取当前数据源名称
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceName = KEY.get();
		if (StringUtils.isBlank(dataSourceName)) {
			dataSourceName = DEFAULT_KEY;
		}
		return dataSourceName;
	}

	/**
	 * 设置数据源池
	 * 
	 * @param targetDataSources
	 *            数据源池
	 */
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		_targetDataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
		afterPropertiesSet();
	}

	/**
	 * 新增数据源
	 * 
	 * @param key
	 *            数据源名称
	 * @param dataSource
	 *            数据源
	 */
	public void addTargetDataSource(String key, DataSource dataSource) {
		_targetDataSources.put(key, dataSource);
		setTargetDataSources(_targetDataSources);
	}

	/**
	 * 创建数据源
	 * 
	 * @param driverClassName
	 *            驱动类
	 * @param url
	 *            服务地址
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 数据源
	 */
	public BasicDataSource createDataSource(String driverClassName, String url,
			String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	/**
	 * 创建数据源
	 * 
	 * @param dataSourceInfo
	 *            数据源连接信息
	 * @return 数据源
	 */
	public BasicDataSource createDataSource(DataSourceInfo dataSourceInfo) {
		return createDataSource(dataSourceInfo.getDriverClassName(), dataSourceInfo.getUrl(),
				dataSourceInfo.getUsername(), dataSourceInfo.getPassword());
	}

	/**
	 * 数据源是否存在
	 * 
	 * @param key
	 *            数据源名称
	 * @return 是否存在
	 */
	public boolean isExist(String key) {
		return _targetDataSources.containsKey(key)
				&& _targetDataSources.get(key) != null;
	}

	/**
	 * 设置当前数据源
	 * 
	 * @param key
	 *            数据源名称
	 */
	public void setDataSource(String key) {
		if (StringUtils.isNotBlank(key) && !key.equals(KEY.get())
				&& _targetDataSources.get(key) == null) {
			KEY.set(null);
		}
		KEY.set(key);
	}

}
