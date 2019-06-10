package com.asion.common.mongo.storage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.asion.common.io.bucket.BucketManager;

/**
 * MongoDB存储板块管理服务
 * 
 * @author chenboyang
 *
 */
public class GridFsBucketManager extends MongoTemplate implements BucketManager {

	/**
	 * 存储板块后缀
	 */
	private static final String BUCKET_SUFFIX = ".files";

	/**
	 * 实例化MongoDB存储板块管理接口
	 * 
	 * @param mongoDbFactory
	 *            MongoDb工厂
	 * @param mongoConverter
	 *            MongoDb转换器
	 */
	public GridFsBucketManager(MongoDbFactory mongoDbFactory, MongoConverter mongoConverter) {
		super(mongoDbFactory, mongoConverter);
	}

	@Override
	public void create(String bucket) {

	}

	@Override
	public List<String> list() {
		return getCollectionNames().stream().filter((name) -> name.endsWith(BUCKET_SUFFIX))
				.collect(Collectors.toList());
	}

	@Override
	public boolean exists(String bucket) {
		return collectionExists(bucket + BUCKET_SUFFIX);
	}

	@Override
	public void delete(String bucket) {
		dropCollection(bucket + BUCKET_SUFFIX);
	}

	@Override
	public long count(String bucket) {
		return count(null, bucket + BUCKET_SUFFIX);
	}

}
