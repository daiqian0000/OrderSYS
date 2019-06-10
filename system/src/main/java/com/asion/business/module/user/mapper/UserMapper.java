package com.asion.business.module.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.asion.business.module.user.model.User;
import com.asion.common.base.BaseMapper;

/**
 * <p>
 * 用户表 : 系统管理用户表 Mapper 接口
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据用户编号获取角色信息
	 * 
	 * @param userId
	 *            用户编号
	 * @return 角色信息
	 */
	List<String> getRoles(@Param("userId") String userId);

	/**
	 * 根据用户编号获取权限信息
	 * 
	 * @param userId
	 *            用户编号
	 * @return 权限信息
	 */
	List<String> getPermissions(@Param("userId") String userId);

}
