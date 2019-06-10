package com.asion.common.oss;

import java.util.List;
import java.util.stream.Collectors;

import com.asion.common.io.bucket.BucketManager;

/**
 * OSS对象存储板块管理服务
 * 
 * @author chenboyang
 *
 */
public class OSSBucketManager implements BucketManager {

	/**
	 * OSS对象存储服务接口
	 */
	private ObjectStorageService oss;

	/**
	 * 实例化OSS对象存储板块管理接口
	 * 
	 * @param oss
	 *            OSS对象存储服务接口
	 */
	public OSSBucketManager(ObjectStorageService oss) {
		this.oss = oss;
	}

	@Override
	public void create(String bucket) {
		oss.createBucket(bucket);
	}

	@Override
	public List<String> list() {
		return oss.listBuckets().stream().map((b) -> b.getName()).collect(Collectors.toList());
	}

	@Override
	public boolean exists(String bucket) {
		return oss.doesBucketExist(bucket);
	}

	@Override
	public void delete(String bucket) {
		oss.deleteBucket(bucket);
	}

	@Override
	public long count(String bucket) {
		return oss.getClient().getBucketStat(bucket).getObjectCount().longValue();
	}

}
