package com.asion.common.io.storage;

/**
 * <h1>简单文件存储服务</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
public class SimpleFileStorage extends AbstractFileStorage {

	/**
	 * 文件存储根目录
	 */
	public static final String UPLOAD = "upload";
	
	/**
	 * 默认存储板块
	 */
	public static final String DEFAULT = "default";

	@Override
	protected String directory() {
		return UPLOAD;
	}

	@Override
	protected String defaultBucket() {
		return DEFAULT;
	}

}
