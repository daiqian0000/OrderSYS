package com.asion.business.module.org.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.asion.business.module.org.model.Org;
import com.asion.common.base.BaseMapper;

/**
 * <p>
 * 机构表 : 系统操作员的机构管理 Mapper 接口
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface OrgMapper extends BaseMapper<Org> {

	/**
	 * 向下递归查询机构代码
	 * 
	 * @param orgId
	 *            机构代码
	 * @return 机构代码字符串
	 */
	@Select("SELECT ORG_DOWN_RECURSIVE(#{orgId})")
	String downRecursive(@Param("orgId") String orgId);

	/**
	 * 向上递归查询机构代码
	 * 
	 * @param orgId
	 *            机构代码
	 * @return 机构代码字符串
	 */
	@Select("SELECT ORG_UP_RECURSIVE(#{orgId})")
	String upRecursive(@Param("orgId") String orgId);

	/**
	 * 获取当前最大排序值
	 * 
	 * @param parentId
	 *            上级代码
	 * @return 排序值
	 */
	@Select("SELECT MAX(SORT) FROM LOC_ORG WHERE PARENT_ID = #{parentId}")
	Integer currSort(@Param("parentId") String parentId);

}
