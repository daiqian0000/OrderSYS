package com.asion.business.handler.service;

import java.util.Map;
import java.util.Set;

import com.asion.business.module.user.model.User;

/**
 * 系统信息服务接口
 * 
 * @author chenboyang
 *
 */
public interface SystemService {

	/**
	 * 获取当前用户信息
	 * 
	 * @return 用户信息
	 */
	User currUser();

	/**
	 * 获取当前用户角色信息
	 * 
	 * @return 角色信息
	 */
	Set<String> currRoles();

	/**
	 * 获取当前用户权限信息
	 * 
	 * @return 权限信息
	 */
	Set<String> currPermissions();

	/**
	 * 获取当前用户数据权限信息
	 * 
	 * @return 数据权限信息
	 */
	Map<String, Object> currDataPermissions();

	/**
	 * 获取当前用户所有信息（基本信息，角色，权限等）
	 * 
	 * @return 信息
	 */
	Map<String, Object> currInfo();

}
