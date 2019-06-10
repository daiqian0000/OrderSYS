package com.asion.business.module.log.service;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.asion.business.handler.service.SystemService;
import com.asion.business.module.log.model.Log;
import com.asion.business.module.permission.mapper.MenuMapper;
import com.asion.business.module.user.model.User;
import com.asion.business.web.service.ApplyService;
import com.asion.business.web.util.WebExceptionUtil;
import com.asion.common.log.LogInfo;

/**
 * WEB日志服务接口实现类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@Service
public class WebLogServiceImpl extends LogServiceImpl implements WebLogService {

	/**
	 * 系统应用服务接口
	 */
	@Autowired
	private ApplyService applyService;

	/**
	 * 菜单数据访问接口
	 */
	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 系统信息服务接口
	 */
	@Autowired
	private SystemService systemService;

	public void saveWebLog(HttpServletRequest request, Object handler, Exception ex) {
		Log log = new Log();
		setHandlerInfo(log, handler);
		setReqInfo(log, request);
		setExInfo(log, ex);
		saveLog(log);
	}

	public void saveWebLog(String operateInfo) {
		HttpServletRequest request = null;
		if (RequestContextHolder.getRequestAttributes() != null) {
			request = applyService.request();
		}
		saveWebLog(operateInfo, request);
	}

	public void saveWebLog(String operateInfo, HttpServletRequest request) {
		Log log = new Log();
		log.setOperateInfo(operateInfo);
		if (request != null) {
			setReqInfo(log, request);
			setExInfo(log, WebExceptionUtil.getThrowable(request));
		}
		saveLog(log);
	}

	/**
	 * 设置日志处理信息
	 * 
	 * @param log
	 *            日志信息
	 * @param handler
	 *            业务处理器
	 */
	private void setHandlerInfo(Log log, Object handler) {
		if (log != null && handler != null && handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			LogInfo li = method.getAnnotation(LogInfo.class);
			RequiresPermissions rp = method.getAnnotation(RequiresPermissions.class);
			if (li != null) {
				log.setOperateInfo(li.info());
			} else if (rp != null) {
				String[] permissions = rp != null ? rp.value() : null;
				if (ArrayUtils.isNotEmpty(permissions)) {
					String menuPath = menuMapper.menuPath(permissions);
					log.setOperateInfo(menuPath);
				}
			}
		}
	}

	/**
	 * 设置日志请求信息
	 * 
	 * @param log
	 *            日志信息
	 * @param request
	 *            请求信息
	 */
	private void setReqInfo(Log log, HttpServletRequest request) {
		if (log != null && request != null) {
			if (StringUtils.isBlank(log.getRequestUrl())) {
				log.setRequestUrl(request.getRequestURI());
			}
			if (StringUtils.isBlank(log.getParam()) && !(request instanceof StandardMultipartHttpServletRequest) && MapUtils.isNotEmpty(request.getParameterMap())) {
				StringBuilder params = new StringBuilder();
				for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
					if (params.length() > 1000) {
						params = new StringBuilder(params.substring(0, 999));
						break;
					}
					params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
					String paramValue = (param.getValue() != null && param.getValue().length > 0
							? StringUtils.join(param.getValue(), ',') : "");
					params.append(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue);
				}
				log.setParam(params.toString());
			}
			if (StringUtils.isBlank(log.getIp())) {
				log.setIp(request.getRemoteAddr());
			}
			if (StringUtils.isBlank(log.getUserId()) && RequestContextHolder.getRequestAttributes() != null) {
				User user = systemService.currUser();
				if (user != null) {
					log.setUserId(user.getUserId());
				}
			}
		}
	}

}
