package com.asion.business.handler.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asion.business.handler.security.shiro.session.RestSessionManager;
import com.asion.business.handler.service.SystemService;
import com.asion.business.module.log.service.WebLogService;
import com.asion.business.web.controller.ApplyController;
import com.asion.common.log.LogInfo;
import com.asion.common.util.BaseUtil;

/**
 * 系统信息请求控制器
 * 
 * @author chenboyang
 *
 */
public class SystemController extends ApplyController {

	/**
	 * 系统信息服务接口
	 */
	@Autowired
	private SystemService systemService;

	/**
	 * Web日志服务接口
	 */
	@Autowired
	private WebLogService webLogService;

	/**
	 * 访问用户是否在线
	 * 
	 * @return 是否在线
	 */
	@RequestMapping("/isAuthen")
	public Object isAuthen() {
		return BaseUtil.createMap("isAuthen", SecurityUtils.getSubject().isAuthenticated());
	}
	
	/**
	 * 获取当前用户所有信息（基本信息，角色，权限等）
	 * 
	 * @return 信息
	 */
	@LogInfo(info = "获取用户信息")
	@RequestMapping("/currInfo")
	public Object currInfo() {
		return systemService.currInfo();
	}

	/**
	 * 用户登录系统
	 * 
	 * @param request
	 *            请求信息
	 * @param response
	 *            响应信息
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录成功或失败
	 */
	@RequestMapping("/login")
	public Object login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(new UsernamePasswordToken(username, password));
			boolean isSuccess = subject.isAuthenticated();
			if (isSuccess) {
				Cookie cookie = new Cookie(RestSessionManager.AUTHORIZATION, subject.getSession().getId().toString());
				cookie.setMaxAge(SimpleCookie.ONE_YEAR);
				cookie.setPath("/");
				response.addCookie(cookie);
				webLogService.saveWebLog("用户登录", request);
				return true;
			}
		} catch (IncorrectCredentialsException e) {
			throw new IncorrectCredentialsException("登录密码错误！");
		} catch (ExcessiveAttemptsException e) {
			throw new ExcessiveAttemptsException("登录失败次数过多！");
		} catch (LockedAccountException e) {
			throw new LockedAccountException("帐号已被锁定！");
		} catch (DisabledAccountException e) {
			throw new DisabledAccountException("帐号已被禁用！");
		} catch (ExpiredCredentialsException e) {
			throw new ExpiredCredentialsException("帐号已过期！");
		} catch (UnknownAccountException e) {
			throw new UnknownAccountException("帐号不存在！");
		} catch (UnauthorizedException e) {
			throw new UnauthorizedException("您没有得到相应的授权！");
		}
		return false;
	}
	
	/**
	 * 用户退出系统
	 * 
	 * @param request
	 *            请求信息
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request) {
		webLogService.saveWebLog("用户退出", request);
		SecurityUtils.getSubject().logout();
	}

}
