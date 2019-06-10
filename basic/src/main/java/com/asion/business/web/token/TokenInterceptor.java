package com.asion.business.web.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asion.business.web.constants.StatusCode;
import com.asion.business.web.model.Result;
import com.asion.common.util.JsonUtil;

/**
 * APP端Token访问拦截器
 * 
 * @author chenboyang
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Token缓存服务接口
	 */
	private TokenCache<?> tokenCache;
	
	/**
	 * 构建Token拦截器
	 * @param tokenCache Token缓存
	 */
	public TokenInterceptor(TokenCache<?> tokenCache) {
		this.tokenCache = tokenCache;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = getTokenFromRequest(request);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		if (StringUtils.isEmpty(token)) {
			Result<Object> result = Result.result(StatusCode.NO_TOKEN, "Token不能为空，请登录！", null);
			response.getWriter().write(JsonUtil.toJson(result));
			response.getWriter().close();
			return false;
		}
		if (!tokenCache.checkToken(token)) {
			Result<Object> result = Result.result(StatusCode.TOKEN_TIMEOUT, "Token已过期，请重新登录！", null);
			response.getWriter().write(JsonUtil.toJson(result));
			response.getWriter().close();
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * 从请求信息中获取token值
	 * 
	 * @param request
	 *            请求信息
	 * @return token值
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(TokenCache.TOKEN);
		if (StringUtils.isEmpty(token)) {
			token = request.getParameter(TokenCache.TOKEN);
		}
		return token;
	}

}
