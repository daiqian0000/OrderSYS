package com.asion.config.properties;

/**
 * OSS对象存储配置属性类
 * 
 * @author chenboyang
 *
 */
public class OSSProperties {

	/**
	 * OSS服务地址
	 */
	private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

	/**
	 * 服务访问ID
	 */
	private String accessId;

	/**
	 * 服务访问KEY
	 */
	private String accessKey;

	/**
	 * 分块Size
	 */
	private long partSize = 1024l * 1024 * 1;

	/**
	 * 并发线程数
	 */
	private int taskNum = 10;

	/**
	 * 过期时长
	 */
	private long timeLength = 1000l * 60 * 60 * 24 * 30 * 12 * 100;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public long getPartSize() {
		return partSize;
	}

	public void setPartSize(long partSize) {
		this.partSize = partSize;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public long getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(long timeLength) {
		this.timeLength = timeLength;
	}

}
