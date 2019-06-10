package com.asion.business.web.constants;

/**
 * WEB请求状态代码
 * 
 * @author chenboyang
 *
 */
public abstract class StatusCode {

	/**
	 * 请求成功
	 */
	public static final String SUCCESS = "200";

	/**
	 * 请求失败
	 */
	public static final String FAIL = "400";

	/**
	 * 无请求token
	 */
	public static final String NO_TOKEN = "110";

	/**
	 * token已过期
	 */
	public static final String TOKEN_TIMEOUT = "120";

	/**
	 * 用户密码错误
	 */
	public static final String LOGIN_INFO_ERROR = "130";

	/**
	 * 验证码错误
	 */
	public static final String VERIFY_CODE_ERROR = "140";

	/**
	 * 服务器错误
	 */
	public static final String SERVER_ERROR = "500";

	/**
	 * 业务错误
	 */
	public static final String BUSINESS_ERROR = "600";

	/**
	 * 未通过认证
	 */
	public static final String UNAUTHENTICATED = "601";

	/**
	 * 登录密码错误
	 */
	public static final String INCORRECT_CREDENTIALS = "602";

	/**
	 * 登录失败次数过多
	 */
	public static final String EXCESSIVE_ATTEMPTS = "603";

	/**
	 * 帐号已被锁定
	 */
	public static final String LOCKED_ACCOUNT = "604";

	/**
	 * 帐号已被禁用
	 */
	public static final String DISABLED_ACCOUNT = "605";

	/**
	 * 帐号已过期
	 */
	public static final String EXPIRED_CREDENTIALS = "606";

	/**
	 * 帐号不存在
	 */
	public static final String UNKNOWN_ACCOUNT = "607";

	/**
	 * 您没有相应的授权
	 */
	public static final String UNAUTHORIZED = "608";
	
}
