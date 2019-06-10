package com.asion.business.module.user.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.asion.business.module.user.mapper.UserRoleMapper;
import com.asion.business.module.user.model.UserRole;
import com.asion.common.base.AbstractService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class UserRoleServiceImpl extends AbstractService<UserRoleMapper, UserRole> implements UserRoleService {

	@Override
	protected EntityWrapper<UserRole> queryWrapper(Map<String, Object> map) {
		EntityWrapper<UserRole> wrapper = super.queryWrapper(map);
		wrapper.eq(map.get("userId") != null, UserRole.USER_ID, map.get("userId"));
		return wrapper;
	}

}
