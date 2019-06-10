package com.asion.dynamic.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asion.dynamic.base.mapper.Mapper;

/**
 * Mybatis数据访问通用服务
 * 
 * @author chenboyang
 */
@Service
public class MyBatisServiceImpl implements MyBatisService {

	/**
	 * 持久化接口
	 */
	@Autowired(required = false)
	private Mapper mapper;
	
	public Mapper mapper() {
		return mapper;
	}

}
