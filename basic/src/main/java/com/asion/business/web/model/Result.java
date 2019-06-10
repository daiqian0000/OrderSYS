package com.asion.business.web.model;

import com.asion.business.web.constants.StatusCode;

/**
 * 返回结果封装对象
 * 
 * @author chenboyang
 *
 * @param <T>
 *            结果类型
 */
public class Result<T> {
	
	/**
	 * 返回状态码
	 */
	private String code;

	/**
	 * 返回消息
	 */
	private String msg;
	
	/**
	 * 返回结果
	 */
	private T result;
	
	/**
	 * 构建函数
	 */
	public Result() {
		
	}
	
	/**
	 * 构建函数
	 * 
	 * @param result
	 *            返回结果
	 */
	public Result(T result) {
		this(StatusCode.SUCCESS, "操作成功！", result);
	}
	
	/**
	 * 构建函数
	 * 
	 * @param code
	 *            返回状态码
	 * @param msg
	 *            返回消息
	 * @param result
	 *            返回结果
	 */
	public Result(String code, String msg, T result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	/**
	 * 创建结果对象
	 * 
	 * @return 结果对象
	 */
	public static <T> Result<T> result() {
		return new Result<T>(null);
	}
	
	/**
	 * 创建结果对象
	 * 
	 * @param result
	 *            返回结果
	 * @return 结果对象
	 */
	public static <T> Result<T> result(T result) {
		return new Result<T>(result);
	}

	/**
	 * 创建结果对象
	 * 
	 * @param code
	 *            返回状态码
	 * @param msg
	 *            返回消息
	 * @param result
	 *            返回结果
	 * @return 结果对象
	 */
	public static <T> Result<T> result(String code, String msg, T result) {
		return new Result<T>(code, msg, result);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
