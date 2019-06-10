package com.asion.business.module.role.service;

import java.util.List;

import com.asion.business.module.role.model.Role;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 角色菜单关系绑定
	 * 
	 * @param roleId
	 *            角色编号
	 * @param menuIds
	 *            菜单编号集
	 * @return 操作记录数
	 */
	int roleMenuBind(String roleId, String[] menuIds);

	/**
	 * 查询菜单编号列表
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 编号列表
	 */
	List<String> menuIdList(String roleId);

}
