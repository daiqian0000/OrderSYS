package com.asion.common.mongo.storage;

import java.util.Map;

import com.asion.common.mongo.annotation.MongoStorage;

/**
 * MongoDB默认数据存储实现类
 * 
 * @author chenboyang
 *
 */
@MongoStorage(name = "default", type = Map.class)
public class DefaultMongoDBStorage implements MongoDBStorage<Map<Object, Object>> {

}
