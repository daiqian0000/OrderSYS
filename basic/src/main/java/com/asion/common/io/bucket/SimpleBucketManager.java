package com.asion.common.io.bucket;

import com.asion.common.io.storage.SimpleFileStorage;

/**
 * 简单存储板块管理服务
 * 
 * @author chenboyang
 *
 */
public class SimpleBucketManager extends AbstractBucketManager {

	@Override
	protected String directory() {
		return SimpleFileStorage.UPLOAD;
	}

}
