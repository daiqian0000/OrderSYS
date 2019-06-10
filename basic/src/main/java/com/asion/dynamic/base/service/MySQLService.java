package com.asion.dynamic.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asion.dynamic.base.mapper.DDLMapper;
import com.asion.dynamic.base.mapper.Mapper;
import com.asion.dynamic.base.mapper.MySQLMapper;

/**
 * Mybatis数据访问通用服务
 * 
 * @author chenboyang
 */
@Service
public class MySQLService implements MyBatisService, DDLService {

	/**
	 * 数据库通用数据访问接口
	 */
	@Autowired(required = false)
	private Mapper mapper;

	/**
	 * MySQL数据库DDL操作接口
	 */
	@Autowired(required = false)
	private MySQLMapper mysqlMapper;
	
	public Mapper mapper() {
		return mapper;
	}

	public DDLMapper ddlMapper() {
		return mysqlMapper;
	}

}
