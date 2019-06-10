package com.asion.common.io.bucket;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 简单存储板块管理服务
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractBucketManager implements BucketManager {

	/**
	 * 文件存储根目录
	 */
	protected abstract String directory();

	@Override
	public void create(String bucket) {
		File file = new File(directory() + "/" + bucket);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	@Override
	public List<String> list() {
		File file = new File(directory());
		if (file.exists()) {
			Arrays.asList(file.list());
		}
		return null;
	}

	@Override
	public boolean exists(String bucket) {
		return new File(directory() + "/" + bucket).exists();
	}

	@Override
	public void delete(String bucket) {
		File file = new File(directory() + "/" + bucket);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public long count(String bucket) {
		File file = new File(directory() + "/" + bucket);
		if (file.exists()) {
			return file.list().length;
		}
		return 0;
	}

}
