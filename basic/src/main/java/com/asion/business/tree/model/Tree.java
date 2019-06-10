package com.asion.business.tree.model;

import java.util.List;

import com.asion.business.tree.service.TreeService;

/**
 * 普通树状数据结构模型
 * 
 * @author chenboyang
 *
 */
public class Tree {

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 上级编号
	 */
	private String parentId;

	/**
	 * 等级
	 */
	private Integer level;

	/**
	 * 是否展开
	 */
	private boolean spread;

	/**
	 * 上级对象
	 */
	private Tree parent;

	/**
	 * 下级对象列表
	 */
	private List<Tree> children;

	/**
	 * 创建根节点
	 * 
	 * @param name
	 *            根节点名称
	 * @return 根节点
	 */
	public static Tree createTop(String name) {
		return createTop(name, false);
	}

	/**
	 * 创建根节点
	 * 
	 * @param name
	 *            根节点名称
	 * @param spread
	 *            是否展开
	 * @return 根节点
	 */
	public static Tree createTop(String name, boolean spread) {
		Tree tree = new Tree();
		tree.setId(TreeService.TOP_LEVEL);
		tree.setName(name);
		tree.setLevel(0);
		tree.setSpread(spread);
		return tree;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public Tree getParent() {
		return parent;
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

}
