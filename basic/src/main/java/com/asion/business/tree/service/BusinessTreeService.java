package com.asion.business.tree.service;

import java.util.List;
import java.util.function.Function;

import com.asion.business.tree.model.Tree;

/**
 * 业务树服务接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务类型
 */
public interface BusinessTreeService<T> {

	/**
	 * 业务对象转树状对象功能函数
	 * 
	 * @return 功能函数
	 */
	Function<T, Tree> function();

	/**
	 * 业务树状对象
	 * 
	 * @return 树
	 */
	List<Tree> tree();

}
