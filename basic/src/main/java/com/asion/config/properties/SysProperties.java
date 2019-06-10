package com.asion.config.properties;

import java.util.Map;

/**
 * 系统配置属性类
 * 
 * @author chenboyang
 *
 */
public class SysProperties {

	/**
	 * 服务器IP地址
	 */
	private String address;
	
	/**
	 * 服务器端口
	 */
	private int port = 8080;
	
	/**
	 * 服务器名称
	 */
	private String name;
	
	/**
	 * 服务器访问地址
	 */
	private String url;
	
	/**
	 * 会话过期时间
	 */
	private long timeout = 30L;
	
	/**
	 * 文件上传临时目录
	 */
	private String uploadDir;
	
	/**
	 * 系统统一字符集
	 */
	private String charset = "UTF-8";
	
	/**
	 * 日期数据默认格式
	 */
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 系统业务配置数据
	 */
	private Map<String, Object> business;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Map<String, Object> getBusiness() {
		return business;
	}

	public void setBusiness(Map<String, Object> business) {
		this.business = business;
	}

}
