package com.asion.business.web.token;

/**
 * token业务封装类
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务类型
 */
public class Token<T> {

	/**
	 * 业务编号
	 */
	private String id;

	/**
	 * 业务信息
	 */
	private T info;

	/**
	 * 构建方法
	 */
	public Token() {

	}

	/**
	 * 构建方法
	 * 
	 * @param id
	 *            业务编号
	 */
	public Token(String id) {
		this.id = id;
	}

	/**
	 * 构建方法
	 * 
	 * @param id
	 *            业务编号
	 * @param info
	 *            业务信息
	 */
	public Token(String id, T info) {
		this.id = id;
		this.info = info;
	}

	/**
	 * 创建token业务对象
	 * 
	 * @return token业务对象
	 */
	public static <T> Token<T> token() {
		return new Token<T>();
	}

	/**
	 * 创建token业务对象
	 * 
	 * @param id
	 *            业务编号
	 * @return token业务对象
	 */
	public static <T> Token<T> token(String id) {
		return new Token<T>(id);
	}

	/**
	 * 创建token业务对象
	 * 
	 * @param id
	 *            业务编号
	 * @param info
	 *            业务信息
	 * @return token业务对象
	 */
	public static <T> Token<T> token(String id, T info) {
		return new Token<T>(id, info);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

}
