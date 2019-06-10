package com.asion.business.module.permission.service;

import java.util.Collection;
import java.util.List;

import com.asion.business.module.permission.model.Menu;
import com.asion.business.tree.service.BusinessTreeService;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface MenuService extends BaseService<Menu>, BusinessTreeService<Menu> {

	/**
	 * 上移
	 * 
	 * @param menu
	 *            条件参数
	 * @return 操作是否成功
	 */
	boolean up(Menu menu);

	/**
	 * 下移
	 * 
	 * @param menu
	 *            条件参数
	 * @return 操作是否成功
	 */
	boolean down(Menu menu);

	/**
	 * 根据条件获取菜单树
	 * 
	 * @param sysId
	 *            系统编号
	 * @param roleIds
	 *            角色编号列表
	 * @return 菜单树
	 */
	List<Menu> tree(String sysId, Collection<String> roleIds);

}
