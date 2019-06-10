package com.asion.common.util;

/**
 * 分页容器代理
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务类型
 */
@Deprecated
public class PageBroker<T> {

	/**
	 * 分页容器
	 */
	private Page<T> page = new Page<T>();

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

}
