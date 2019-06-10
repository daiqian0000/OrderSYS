package com.asion.business.tree.service;

import java.util.List;

import com.asion.business.tree.model.Tree;

/**
 * 树状数据服务接口
 * 
 * @author chenboyang
 *
 */
public interface TreeService {

	/**
	 * 顶级
	 */
	String TOP_LEVEL = "0";

	/**
	 * 顶级代码
	 */
	String TOP_CODE = "-";

	/**
	 * 将列表数据封装成包含根节点的树状数据
	 * 
	 * @param list
	 *            列表
	 * @param topCodes
	 *            顶级代码集
	 * @return 树状数据
	 */
	List<Tree> tree(List<Tree> list, String... topCodes);

	/**
	 * 将列表数据封装成树状数据
	 * 
	 * @param list
	 *            列表
	 * @return 树状数据
	 */
	List<Tree> tree(List<Tree> list);

	/**
	 * 将列表数据封装成包含根节点的树状数据
	 * 
	 * @param list
	 *            列表
	 * @param top
	 *            顶级节点
	 * @return 树状数据
	 */
	List<Tree> tree(List<Tree> list, Tree top);

	/**
	 * 将列表数据封装成包含上级和根节点的树状数据
	 * 
	 * @param list
	 *            列表
	 * @param topCodes
	 *            顶级代码集
	 * @return 树状数据
	 */
	List<Tree> fullTree(List<Tree> list, String... topCodes);

	/**
	 * 将列表数据封装成包含上级的树状数据
	 * 
	 * @param list
	 *            列表
	 * @return 树状数据
	 */
	List<Tree> fullTree(List<Tree> list);

	/**
	 * 将列表数据封装成包含上级和根节点的树状数据
	 * 
	 * @param list
	 *            列表
	 * @param top
	 *            顶级节点
	 * @return 树状数据
	 */
	List<Tree> fullTree(List<Tree> list, Tree top);

}
