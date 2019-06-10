package com.asion.config.properties;

/**
 * Shiro配置属性类
 * 
 * @author chenboyang
 *
 */
public class ShiroProperties {

	/**
	 * 登录地址
	 */
	private String loginUrl;

	/**
	 * 登出地址
	 */
	private String logoutUrl;

	/**
	 * 单点登录服务路径
	 */
	private String casService;

	/**
	 * 单点登录服务器地址前缀
	 */
	private String casServerUrlPrefix;

	/**
	 * 登录成功跳转地址
	 */
	private String successUrl;

	/**
	 * 登录失败跳转地址
	 */
	private String failureUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getCasService() {
		return casService;
	}

	public void setCasService(String casService) {
		this.casService = casService;
	}

	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

}
