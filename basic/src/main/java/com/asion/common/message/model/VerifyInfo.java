package com.asion.common.message.model;

/**
 * 验证码信息类
 * 
 * @author chenboyang
 *
 */
public class VerifyInfo {

	/**
	 * 验证码有效时间（单位：秒），默认值10分钟
	 */
	private long timeout;

	/**
	 * 验证码短信发送间隔时间（单位：秒），默认值1分钟
	 */
	private long sendInterval;

	/**
	 * 验证码模板
	 */
	private String verifyCodeTemplate;
	
	/**
	 * 验证码长度，默认值4
	 */
	private int verifyCodeLength;

	/**
	 * 验证码发送配置构造方法
	 */
	public VerifyInfo() {
		
	}

	/**
	 * 验证码发送配置构造方法
	 * 
	 * @param timeout
	 *            过期时间
	 * @param sendInterval
	 *            发送间隔
	 * @param verifyCodeTemplate
	 *            验证码模块
	 * @param verifyCodeLength
	 *            验证码长度
	 */
	public VerifyInfo(long timeout, long sendInterval, String verifyCodeTemplate, int verifyCodeLength) {
		this.timeout = timeout;
		this.sendInterval = sendInterval;
		this.verifyCodeTemplate = verifyCodeTemplate;
		this.verifyCodeLength = verifyCodeLength;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getSendInterval() {
		return sendInterval;
	}

	public void setSendInterval(long sendInterval) {
		this.sendInterval = sendInterval;
	}

	public String getVerifyCodeTemplate() {
		return verifyCodeTemplate;
	}

	public void setVerifyCodeTemplate(String verifyCodeTemplate) {
		this.verifyCodeTemplate = verifyCodeTemplate;
	}

	public int getVerifyCodeLength() {
		return verifyCodeLength;
	}

	public void setVerifyCodeLength(int verifyCodeLength) {
		this.verifyCodeLength = verifyCodeLength;
	}

}
