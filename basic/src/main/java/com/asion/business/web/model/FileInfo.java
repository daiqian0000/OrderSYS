package com.asion.business.web.model;

/**
 * 文件保存信息
 * 
 * @author chenboyang
 *
 */
public class FileInfo {

	/**
	 * 文件原名称
	 */
	private String originName;

	/**
	 * 文件保存至服务器名称
	 */
	private String fileName;

	/**
	 * 文件保存至服务器的相对路径
	 */
	private String path;

	/**
	 * 文件后缀
	 */
	private String suffix;

	/**
	 * 文件大小
	 */
	private long size;

	/**
	 * 构建文件信息
	 */
	public FileInfo() {

	}

	/**
	 * 构建文件信息
	 * 
	 * @param originName
	 *            文件原名
	 * @param fileName
	 *            文件名
	 * @param path
	 *            地址
	 * @param suffix
	 *            文件后缀
	 * @param size
	 *            文件大小
	 */
	public FileInfo(String originName, String fileName, String path, String suffix, long size) {
		this.originName = originName;
		this.fileName = fileName;
		this.path = path;
		this.suffix = suffix;
		this.size = size;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
