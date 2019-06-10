package com.asion.business.handler.security.shiro.realm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.asion.business.handler.constants.SystemConstants;
import com.asion.business.handler.security.dataPermission.DataPermissionAspect;
import com.asion.business.handler.security.shiro.session.RestSessionManager;
import com.asion.business.handler.service.BusinessService;
import com.asion.business.module.user.model.User;
import com.asion.business.module.user.service.UserService;

/**
 * 权限校验器
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public class AuthRealm extends AuthorizingRealm {

	/**
	 * 用户服务接口
	 */
	@Autowired
	private UserService userService;

	/**
	 * 业务服务接口
	 */
	@Autowired
	private BusinessService businessService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = null;
		if (principals != null) {
			authorizationInfo = new SimpleAuthorizationInfo();
			Set<String> roles = (Set<String>) SecurityUtils.getSubject().getSession()
					.getAttribute(SystemConstants.ROLES);
			Set<String> permissions = (Set<String>) SecurityUtils.getSubject().getSession()
					.getAttribute(SystemConstants.PERMISSIONS);
			authorizationInfo.addRoles(roles);
			authorizationInfo.addStringPermissions(permissions);
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = ((UsernamePasswordToken) token).getUsername();
		User user = userService.getUser(username);
		if (user == null) {
			throw new AuthenticationException("用户不存在！");
		}
		if (user.getEnable() == null || !user.getEnable().booleanValue()) {
			throw new DisabledAccountException("帐号已被禁用！");
		}
		RestSessionManager.USER_ID.set(user.getUserId());
		Set<String> roles = userService.getRoles(username).stream().filter((t) -> StringUtils.isNoneBlank(t))
				.collect(Collectors.toSet());
		Set<String> permissions = userService
				.getPermissions(SystemConstants.ADMIN.equals(user.getUserId()) ? null : username).stream()
				.filter((t) -> StringUtils.isNoneBlank(t)).collect(Collectors.toSet());
		SecurityUtils.getSubject().getSession().setAttribute(SystemConstants.USER, user);
		SecurityUtils.getSubject().getSession().setAttribute(SystemConstants.ROLES, roles);
		SecurityUtils.getSubject().getSession().setAttribute(SystemConstants.PERMISSIONS, permissions);
		Map<String, Object> dataPermission = new HashMap<String, Object>();
		dataPermission.put(DataPermissionAspect.ORG_RECURSIVE, businessService.orgRecursive(user.getOrgId()));
		dataPermission.put(DataPermissionAspect.AREA_RECURSIVE, businessService.areaRecursive(user.getAreaId()));
		SecurityUtils.getSubject().getSession().setAttribute(SystemConstants.DATA_PERMISSIONS, dataPermission);
		return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
	}

	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return SecurityUtils.getSubject().getSession().getId();
	}

}
