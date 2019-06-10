package com.asion.business.module.permission.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.asion.business.module.permission.model.Menu;
import com.asion.common.base.BaseMapper;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 获取当前最大排序值
	 * 
	 * @param parentId
	 *            上级编号
	 * @return 排序值
	 */
	@Select("SELECT MAX(SORT) FROM LOC_MENU WHERE PARENT_ID = #{parentId}")
	Integer currSort(@Param("parentId") String parentId);
	
	/**
	 * 查询菜单路径
	 * 
	 * @param permissions
	 *            权限
	 * @return 菜单路径
	 */
	String menuPath(@Param("permissions") String[] permissions);

}
