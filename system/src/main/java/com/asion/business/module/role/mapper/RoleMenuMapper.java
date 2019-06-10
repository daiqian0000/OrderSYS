package com.asion.business.module.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.asion.business.module.role.model.RoleMenu;
import com.asion.common.base.BaseMapper;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * 根据角色编号获取菜单编号列表
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 菜单编号列表
	 */
	@Select("SELECT MENU_ID FROM LOC_ROLE_MENU WHERE ROLE_ID = #{roleId}")
	List<String> menuIdList(@Param("roleId") String roleId);

}
