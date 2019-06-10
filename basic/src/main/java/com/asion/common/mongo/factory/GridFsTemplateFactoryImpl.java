package com.asion.common.mongo.factory;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.asion.common.util.SpringContextUtil;

/**
 * MongoDB数据库文件存储系统操作模板构建工厂实现类
 * 
 * @author chenboyang
 *
 */
public class GridFsTemplateFactoryImpl implements GridFsTemplateFactory {
	
	/**
	 * MongoDb工厂
	 */
	private MongoDbFactory factory;

	/**
	 * MongoDb转换器
	 */
	private MongoConverter converter;

	public GridFsTemplateFactoryImpl() {
		this(SpringContextUtil.getBean(MongoDbFactory.class), SpringContextUtil.getBean(MongoConverter.class));
	}

	public GridFsTemplateFactoryImpl(MongoDbFactory factory, MongoConverter converter) {
		this.factory = factory;
		this.converter = converter;
	}
	
	@Override
	public GridFsTemplate buildTemplate() {
		return buildTemplate(factory, converter);
	}

	@Override
	public GridFsTemplate buildTemplate(String bucket) {
		return buildTemplate(factory, converter, bucket);
	}

	@Override
	public GridFsTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter) {
		return new GridFsTemplate(factory, converter);
	}

	@Override
	public GridFsTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter, String bucket) {
		return new GridFsTemplate(factory, converter, bucket);
	}

	public MongoDbFactory getFactory() {
		return factory;
	}

	public void setFactory(MongoDbFactory factory) {
		this.factory = factory;
	}

	public MongoConverter getConverter() {
		return converter;
	}

	public void setConverter(MongoConverter converter) {
		this.converter = converter;
	}

}
