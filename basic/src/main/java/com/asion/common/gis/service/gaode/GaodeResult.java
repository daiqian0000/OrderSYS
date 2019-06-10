package com.asion.common.gis.service.gaode;

/**
 * 高德WEB服务请求参数基类
 * 
 * @author chenboyang
 *
 */
public abstract class GaodeResult {

	/**
	 * 请求成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 请求失败
	 */
	public static final int FAIL = 0;

	/**
	 * 响应状态
	 */
	private int status;

	/**
	 * 响应信息
	 */
	private String info;

	/**
	 * 状态码
	 */
	private int infocode;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getInfocode() {
		return infocode;
	}

	public void setInfocode(int infocode) {
		this.infocode = infocode;
	}

}
