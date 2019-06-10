/*package com.asion.business.handler.security.shiro.realm;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.asion.business.handler.constants.SystemConstants;
import com.asion.business.module.log.service.WebLogService;
import com.asion.business.module.user.model.User;
import com.asion.business.module.user.service.UserService;

import io.buji.pac4j.realm.Pac4jRealm;

@SuppressWarnings("unchecked")
public class CasAuthenRealm extends Pac4jRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private WebLogService webLogService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
		AuthenticationInfo authen = super.doGetAuthenticationInfo(authenticationToken);
		String account = (String) authen.getPrincipals().getPrimaryPrincipal();
		Session session = SecurityUtils.getSubject().getSession();
		User user = userService.getUser(account);
		List<String> roles = userService.getRoles(account).stream().filter((t) -> StringUtils.isNoneBlank(t))
				.collect(Collectors.toList());
		List<String> permissions = userService
				.getPermissions(SystemConstants.ADMIN.equals(user.getUserId()) ? null : account).stream()
				.filter((t) -> StringUtils.isNoneBlank(t)).collect(Collectors.toList());
		session.setAttribute(SystemConstants.USER, user);
		session.setAttribute(SystemConstants.ROLES, roles);
		session.setAttribute(SystemConstants.PERMISSIONS, permissions);
		webLogService.saveWebLog("用户登陆");
		return authen;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = null;
		if (principals.getPrimaryPrincipal() != null
				&& StringUtils.isNotBlank(principals.getPrimaryPrincipal().toString())) {
			Session session = SecurityUtils.getSubject().getSession();
			List<String> roles = (List<String>) session.getAttribute(SystemConstants.ROLES);
			List<String> permissions = (List<String>) session.getAttribute(SystemConstants.PERMISSIONS);
			authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addRoles(roles);
			authorizationInfo.addStringPermissions(permissions);
		}
		return authorizationInfo;
	}

}
*/