package com.asion.common.gis.service.gaode;

/**
 * 高德WEB服务请求参数基类
 * 
 * @author chenboyang
 *
 */
public abstract class GaodeParameter {

	/**
	 * JSON输出类型
	 */
	public static final String OUTPUT_JSON = "json";

	/**
	 * XML输出类型
	 */
	public static final String OUTPUT_XML = "xml";

	/**
	 * 返回基本结果
	 */
	public static final String EXTENSIONS_BASE = "base";

	/**
	 * 返回全部结果
	 */
	public static final String EXTENSIONS_ALL = "all";

	/**
	 * 密钥
	 */
	private String key = GaodeConfig.KEY;

	/**
	 * 返回结果控制
	 */
	private String extensions = EXTENSIONS_BASE;

	/**
	 * 签名
	 */
	private String sig;

	/**
	 * 输出类型
	 */
	private String output = OUTPUT_JSON;

	/**
	 * 回调函数
	 */
	private String callback;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
