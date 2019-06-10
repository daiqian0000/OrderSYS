package com.asion.common.mongo.factory;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.asion.common.util.SpringContextUtil;

/**
 * MongoDB数据库操作模板构建工厂接口实现类
 * 
 * @author chenboyang
 *
 */
public class MongoTemplateFactoryImpl implements MongoTemplateFactory {
	
	/**
	 * MongoDb工厂
	 */
	private MongoDbFactory factory;

	/**
	 * MongoDb转换器
	 */
	private MongoConverter converter;

	public MongoTemplateFactoryImpl() {
		this(SpringContextUtil.getBean(MongoDbFactory.class), SpringContextUtil.getBean(MongoConverter.class));
	}

	public MongoTemplateFactoryImpl(MongoDbFactory factory, MongoConverter converter) {
		this.factory = factory;
		this.converter = converter;
	}
	
	public MongoTemplate buildTemplate() {
		return new MongoTemplate(factory, converter);
	}

	public MongoTemplate buildTemplate(MongoDbFactory factory, MongoConverter converter) {
		return new MongoTemplate(factory, converter);
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
