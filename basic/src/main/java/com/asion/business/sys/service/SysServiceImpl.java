package com.asion.business.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asion.business.sys.storage.SystemStorage;
import com.asion.config.properties.SysProperties;

/**
 * 系统服务实现类
 * 
 * @author chenboyang
 *
 */
@Service
public class SysServiceImpl implements SysService {

	/**
	 * 系统配置属性
	 */
	@Autowired(required = false)
	private SysProperties sysProperties;

	/**
	 * 系统存储
	 */
	@Autowired
	private SystemStorage systemStorage;
	
	public SysProperties sysProperties() {
		return sysProperties;
	}
	
	public SystemStorage systemStorage() {
		return systemStorage;
	}

}
