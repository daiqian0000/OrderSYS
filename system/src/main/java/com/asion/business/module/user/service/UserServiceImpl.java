package com.asion.business.module.user.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.handler.service.BusinessService;
import com.asion.business.handler.service.SystemService;
import com.asion.business.module.user.mapper.UserMapper;
import com.asion.business.module.user.mapper.UserRoleMapper;
import com.asion.business.module.user.model.User;
import com.asion.business.module.user.model.UserRole;
import com.asion.common.base.AbstractService;
import com.asion.common.exception.BusinessException;
import com.asion.common.util.BaseUtil;
import com.asion.common.util.JudgeUtil;
import com.asion.common.util.StringUtil;
import com.asion.dynamic.base.service.MyBatisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 用户表 : 系统管理用户表 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class UserServiceImpl extends AbstractService<UserMapper, User> implements UserService {
	
	/**
	 * 系统信息服务接口
	 */
	@Autowired(required = false)
	private SystemService systemService;

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
	 * 业务服务接口
	 */
	@Autowired
	private BusinessService businessService;
	
	@Override
	protected EntityWrapper<User> queryWrapper(Map<String, Object> map) {
		EntityWrapper<User> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			wrapper.and(map.get("userId") != null, User.USER_ID, map.get("userId"));
			if (StringUtil.isNotBlank(map.get("orgId"))) {
				wrapper.and("FIND_IN_SET(ORG_ID, {0})", businessService.orgRecursive(map.get("orgId").toString()));
			}
			wrapper.and(map.get("orgRecursive") != null, "FIND_IN_SET(ORG_ID, {0})", map.get("orgRecursive"));
			wrapper.and(map.get("areaRecursive") != null, "FIND_IN_SET(AREA_ID, {0})", map.get("areaRecursive"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(User.USER_ID, map.get("keyword").toString())
					.or().like(User.REAL_NAME, map.get("keyword").toString());
			}
		}
		return wrapper;
	}

	@Transactional
	public boolean add(User record) {
		boolean result = false;
		if (record != null) {
			if (myBatisService.isExists("LOC_USER", User.USER_ID, record.getUserId())) {
				throw new BusinessException("用户名已存在！");
			}
			record.setPassword(BaseUtil.encodeMD5(DEFAULT_PASSWORD));
			updateUserRole(record);
			result = insert(record);
		}
		return result;
	}
	
	@Override
	public User getUser(String userId) {
		return selectOne(new EntityWrapper<User>().eq(User.USER_ID, userId));
	}

	@Override
	public List<String> getRoles(String userId) {
		return baseMapper.getRoles(userId);
	}

	@Override
	public List<String> getPermissions(String userId) {
		return baseMapper.getPermissions(userId);
	}

	@Transactional
	public boolean resetPwd(User user) {
		boolean result = false;
		user.setPassword(BaseUtil.encodeMD5(DEFAULT_PASSWORD));
		result = updateById(user);
		return result;
	}

	@Transactional
	public boolean modPwd(String oldPwd, String newPwd) {
		boolean result = false;
		if (JudgeUtil.isAllNotBlank(oldPwd, newPwd)) {
			User user = systemService.currUser();
			if (user == null) {
				throw new BusinessException("未获取到用户登陆信息，修改密码操作中断！");
			} else {
				if (!user.getPassword().equals(BaseUtil.encodeMD5(oldPwd))) {
					throw new BusinessException("输入的旧密码错误！");
				} else if (oldPwd.equals(newPwd)) {
					throw new BusinessException("输入的新密码与旧密码不能一致！");
				} else {
					User modUser = new User();
					modUser.setId(user.getId());
					modUser.setPassword(BaseUtil.encodeMD5(newPwd));
					result = updateById(modUser);
				}
			}
		}
		return result;
	}

	/**
	 * 更新用户角色关系
	 * 
	 * @param user
	 *            用户信息
	 */
	private void updateUserRole(User user) {
		if (user != null && StringUtils.isNotBlank(user.getUserId())) {
			userRoleMapper.delete(new EntityWrapper<UserRole>().eq(User.USER_ID, user.getUserId()));
			if (StringUtils.isNotBlank(user.getRoleId())) {
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getUserId());
				userRole.setRoleId(user.getRoleId());
				userRoleMapper.insert(userRole);
			}
		}
	}

	@Override
	public List<UserRole> userRoleList(String userId) {
		return userRoleMapper.selectList(new EntityWrapper<UserRole>().eq(User.USER_ID, userId));
	}

	@Transactional
	public boolean del(Serializable id) {
		User user = baseMapper.selectById(id);
		if (user != null) {
			userRoleMapper.delete(new EntityWrapper<UserRole>().eq(User.USER_ID, user.getUserId()));
		}
		return deleteById(id);
	}

	@Transactional
	public boolean mod(User record) {
		boolean result = false;
		if (record != null) {
			updateUserRole(record);
			result = updateById(record);
		}
		return result;
	}
	
}
