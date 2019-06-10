package com.asion.business.handler.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Service;

import com.asion.business.handler.constants.SystemConstants;
import com.asion.business.module.permission.model.Menu;
import com.asion.business.module.permission.service.MenuService;
import com.asion.business.module.role.model.Role;
import com.asion.business.module.role.service.RoleService;
import com.asion.business.module.user.model.User;
import com.asion.business.sys.service.SysService;
import com.asion.business.web.service.ApplyService;
import com.asion.common.util.BaseUtil;

/**
 * 系统信息服务接口实现类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@Service
public class SystemServiceImpl implements SystemService {

	/**
	 * 系统服务接口
	 */
	@Autowired
	private SysService sysService;

	/**
	 * 系统应用服务接口
	 */
	@Autowired(required = false)
	private ApplyService applyService;

	/**
	 * 角色服务接口
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 菜单服务接口
	 */
	@Autowired
	private MenuService menuService;

	public User currUser() {
		return applyService.getSessionAttribute(SystemConstants.USER);
	}

	public Set<String> currRoles() {
		return applyService.getSessionAttribute(SystemConstants.ROLES);
	}

	public Set<String> currPermissions() {
		return applyService.getSessionAttribute(SystemConstants.PERMISSIONS);
	}

	public Map<String, Object> currDataPermissions() {
		return applyService.getSessionAttribute(SystemConstants.DATA_PERMISSIONS);
	}

	public Map<String, Object> currInfo() {
		Map<String, Object> info = null;
		User user = currUser();
		if (user != null) {
			info = BaseUtil.createMap();
			List<Role> role = null;
			List<Menu> menu = null;
			if (user != null && StringUtils.isNotBlank(user.getUserId())) {
				Map<String, Object> roleMap = BaseUtil.createMap(SystemConstants.USER_ID, user.getUserId());
				roleMap.put(SystemConstants.ENABLE, true);
				role = roleService.list(roleMap);
				if (SystemConstants.ADMIN.equals(user.getUserId())) {
					menu = menuService.tree(sysService.systemStorage().get(SystemConstants.SYS_ID), null);
				} else if (CollectionUtils.isNotEmpty(role)) {
					List<String> roleIds = role.stream().map(Role::getRoleId).collect(Collectors.toList());
					menu = menuService.tree(sysService.systemStorage().get(SystemConstants.SYS_ID), roleIds);
				}
			}
			info.put(SystemConstants.USER, user);
			info.put(SystemConstants.ROLE, role);
			info.put(SystemConstants.MENU, menu);
			info.put(SystemConstants.ROLES, currRoles());
			info.put(SystemConstants.PERMISSIONS, currPermissions());
		}
		return info;
	}

}
