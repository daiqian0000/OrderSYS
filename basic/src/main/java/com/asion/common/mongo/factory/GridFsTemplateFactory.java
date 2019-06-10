package com.asion.common.mongo.factory;

import java.util.concurrent.TimeUnit;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * MongoDB数据库文件存储系统操作模板构建工厂接口
 * 
 * @author chenboyang
 *
 */
public interface GridFsTemplateFactory {
	
	/**
	 * 板块操作模板过期时间
	 */
	long GRIDFS_OPERATE_TIMEOUT = 10;

	/**
	 * 板块操作模板过期时间单位
	 */
	TimeUnit GRIDFS_OPERATE_TIMEUNIT = TimeUnit.MINUTES;
	
	/**
	 * 构建文件存储处理模板
	 * 
	 * @return 处理模板
	 */
	GridFsTemplate buildTemplate();

	/**
	 * 构建文件存储处理模板
	 * 
	 * @param bucket
	 *            板块
	 * @return 处理模板
	 */
	GridFsTemplate buildTemplate(String bucket);

	/**
	 * 构建文件存储处理模板
	 * 
	 * @param factory
	 *            MongoDb工厂
	 * @param converter
	 *            MongoDb转换器
	 * @return 处理模板
	 */
	GridFsTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter);

	/**
	 * 构建文件存储处理模板
	 * 
	 * @param factory
	 *            MongoDb工厂
	 * @param converter
	 *            MongoDb转换器
	 * @param bucket
	 *            板块
	 * @return 处理模板
	 */
	GridFsTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter, String bucket);
	
}
