package com.asion.business.sys.service;

import com.asion.business.sys.storage.SystemStorage;
import com.asion.config.properties.SysProperties;

/**
 * 系统服务接口
 * 
 * @author chenboyang
 *
 */
public interface SysService {

	/**
	 * 获取系统默认信息配置属性
	 * 
	 * @return 配置属性
	 */
	SysProperties sysProperties();

	/**
	 * 获取系统默认存储
	 * 
	 * @return 系统存储
	 */
	SystemStorage systemStorage();

}
