package com.asion.business.module.user.service;

import java.util.List;

import com.asion.business.module.user.model.User;
import com.asion.business.module.user.model.UserRole;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 用户表 : 系统管理用户表 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface UserService extends BaseService<User> {

	/**
	 * 用户默认密码
	 */
	String DEFAULT_PASSWORD = "ZAQ!xsw2";
	
	/**
	 * 根据用户编号获取用户信息
	 * 
	 * @param userId
	 *            用户编号
	 * @return 用户信息
	 */
	User getUser(String userId);
	
	/**
	 * 根据用户编号获取用户角色标识
	 * 
	 * @param userId
	 *            用户编号
	 * @return 角色标识
	 */
	List<String> getRoles(String userId);

	/**
	 * 根据用户编号获取用户权限标识
	 * 
	 * @param userId
	 *            用户编号
	 * @return 权限标识
	 */
	List<String> getPermissions(String userId);
	
	/**
	 * 重置用户密码
	 * 
	 * @param user
	 *            用户
	 * @return 操作记录数
	 */
	boolean resetPwd(User user);

	/**
	 * 用户密码修改
	 * 
	 * @param oldPwd
	 *            旧密码
	 * @param newPwd
	 *            新密码
	 * @return 操作记录数
	 */
	boolean modPwd(String oldPwd, String newPwd);

	/**
	 * 用户角色关系列表
	 * 
	 * @param userId
	 *            用户编号
	 * @return 关系列表
	 */
	List<UserRole> userRoleList(String userId);

}
