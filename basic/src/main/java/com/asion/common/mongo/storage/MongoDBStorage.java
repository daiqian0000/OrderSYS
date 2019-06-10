package com.asion.common.mongo.storage;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.asion.common.mongo.annotation.MongoStorage;
import com.asion.common.mongo.handler.MongoDBHandler;
import com.asion.common.util.SpringContextUtil;
import com.mongodb.WriteResult;

/**
 * MongoDB存储接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务对象类型
 */
@SuppressWarnings("unchecked")
public interface MongoDBStorage<T> {

	/**
	 * 获取存储集合名称
	 * 
	 * @return 存储集合名称
	 */
	default String collectionName() {
		String name = "";
		MongoStorage mongoStorage = this.getClass().getAnnotation(MongoStorage.class);
		if (mongoStorage != null) {
			name = mongoStorage.name();
		}
		return name;
	}

	/**
	 * 实体对象类型
	 * 
	 * @return 类型
	 */
	default Class<T> entityType() {
		Class<?> clazz = Object.class;
		MongoStorage mongoStorage = this.getClass().getAnnotation(MongoStorage.class);
		if (mongoStorage != null) {
			clazz = mongoStorage.type();
		}
		return (Class<T>) clazz;
	}

	/**
	 * Mongo操作对象
	 * 
	 * @return 操作对象
	 */
	default MongoOperations ops() {
		return SpringContextUtil.getBean(MongoDBHandler.class);
	}

	/**
	 * 删除存储集合
	 */
	default void drop() {
		ops().dropCollection(collectionName());
	}

	/**
	 * 查询对象是否存在
	 * 
	 * @param query
	 *            查询条件
	 * @return 是否存在
	 */
	default boolean exists(Query query) {
		return ops().exists(query, collectionName());
	}
	
	/**
	 * 列表查询
	 * 
	 * @param query
	 *            查询条件
	 * @return 对象列表
	 */
	default List<T> find(Query query) {
		return ops().find(query, entityType(), collectionName());
	}

	/**
	 * 查询存储集合全部数据
	 * 
	 * @return 对象列表
	 */
	default List<T> findAll() {
		return ops().findAll(entityType(), collectionName());
	}

	/**
	 * 查询单个对象
	 * 
	 * @param query
	 *            查询条件
	 * @return 对象
	 */
	default T findOne(Query query) {
		return ops().findOne(query, entityType(), collectionName());
	}

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 *            对象ID
	 * @return 对象
	 */
	default T findById(Object id) {
		return ops().findById(id, entityType(), collectionName());
	}

	/**
	 * 查询记录数
	 * 
	 * @param query
	 *            查询条件
	 * @return 记录数
	 */
	default long count(Query query) {
		return ops().count(query, collectionName());
	}

	/**
	 * 查询全部记录数
	 * 
	 * @return 记录数
	 */
	default long count() {
		return ops().count(null, collectionName());
	}

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	default void save(T entity) {
		ops().save(entity, collectionName());
	}

	/**
	 * 插入对象集合
	 * 
	 * @param colletion
	 *            对象集合
	 */
	default void insertAll(Collection<? extends T> colletion) {
		ops().insert(colletion, collectionName());
	}

	/**
	 * 根据查询条件更新数据
	 * 
	 * @param query
	 *            查询条件
	 * @param update
	 *            更新信息
	 * @return 写入结果
	 */
	default WriteResult update(Query query, Update update) {
		return ops().updateMulti(query, update, collectionName());
	}

	/**
	 * 删除对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 写入结果
	 */
	default WriteResult remove(T entity) {
		return ops().remove(entity, collectionName());
	}

	/**
	 * 根据条件删除对象
	 * 
	 * @param query
	 *            查询条件
	 * @return 写入结果
	 */
	default WriteResult remove(Query query) {
		return ops().remove(query, collectionName());
	}

}
