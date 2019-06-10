package com.asion.business.module.role.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.module.role.mapper.RoleMapper;
import com.asion.business.module.role.mapper.RoleMenuMapper;
import com.asion.business.module.role.model.Role;
import com.asion.business.module.role.model.RoleMenu;
import com.asion.business.module.user.mapper.UserRoleMapper;
import com.asion.business.module.user.model.UserRole;
import com.asion.common.base.AbstractService;
import com.asion.common.exception.BusinessException;
import com.asion.common.util.StringUtil;
import com.asion.dynamic.base.service.MyBatisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class RoleServiceImpl extends AbstractService<RoleMapper, Role> implements RoleService {

	/**
	 * 数据访问通用DAO
	 */
	@Autowired(required = false)
	@Qualifier("myBatisServiceImpl")
	private MyBatisService myBatisService;
	
	/**
	 * 用户角色关系数据访问接口
	 */
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	/**
	 * 角色菜单关系数据访问信息
	 */
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	protected EntityWrapper<Role> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Role> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			wrapper.and(map.get("userId") != null, "ROLE_ID IN (SELECT ROLE_ID FROM LOC_USER_ROLE WHERE USER_ID = {0})", map.get("userId"));
			wrapper.eq(map.containsKey("enable"), "ENABLE", map.get("enable"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Role.ROLE_ID, map.get("keyword").toString())
					.or().like(Role.ROLE_NAME, map.get("keyword").toString());
			}
		}
		return wrapper;
	}
	
	@Transactional
	public boolean add(Role record) {
		boolean result = false;
		if (record != null) {
			if (myBatisService.isExists("LOC_ROLE", Role.ROLE_ID, record.getRoleId())) {
				throw new BusinessException("角色编号已存在！");
			}
			result = insert(record);
		}
		return result;
	}

	@Transactional
	@Override
	public boolean del(Serializable id) {
		Role role = selectById(id);
		if (role != null && role.getRoleId() != null) {
			if (myBatisService.isExists("LOC_USER_ROLE", Role.ROLE_ID, role.getRoleId())) {
				throw new BusinessException("角色已被用户绑定，无法删除！");
			}
			userRoleMapper.delete(new EntityWrapper<UserRole>().eq(Role.ROLE_ID, role.getRoleId()));
			roleMenuMapper.delete(new EntityWrapper<RoleMenu>().eq(Role.ROLE_ID, role.getRoleId()));
		}
		return deleteById(id);
	}
	
	@Transactional
	public int roleMenuBind(String roleId, String[] menuIds) {
		int num = 0;
		if (StringUtils.isNotBlank(roleId)) {
			num = roleMenuMapper.delete(new EntityWrapper<RoleMenu>().eq(Role.ROLE_ID, roleId));
			if (ArrayUtils.isNotEmpty(menuIds)) {
				List<RoleMenu> list = new ArrayList<RoleMenu>(menuIds.length);
				for (int i = 0; i < menuIds.length; i++) {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(menuIds[i]);
					list.add(roleMenu);
				}
				num = roleMenuMapper.insertBatch(list);
			}
		}
		return num;
	}

	@Override
	public List<String> menuIdList(String roleId) {
		return roleMenuMapper.menuIdList(roleId);
	}

}
