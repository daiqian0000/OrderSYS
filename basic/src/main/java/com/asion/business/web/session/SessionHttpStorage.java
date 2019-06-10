package com.asion.business.web.session;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import com.asion.business.web.service.ApplyService;

/**
 * Session信息Http存储实现类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@Component
@SuppressWarnings("unchecked")
public class SessionHttpStorage implements SessionStorage {

	/**
	 * 系统应用服务接口
	 */
	@Autowired
	private ApplyService applyService;
	
	/**
	 * 获取当前Session信息
	 * 
	 * @return Session信息
	 */
	private HttpSession session() {
		return applyService.session();
	}

	@Override
	public boolean hasSession(String name) {
		Enumeration<String> names = session().getAttributeNames();
		while (names.hasMoreElements()) {
			String element = names.nextElement();
			if (element.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setSession(String name, Object value) {
		session().setAttribute(name, value);
	}

	@Override
	public <V> V getSession(String name) {
		return (V) session().getAttribute(name);
	}

	@Override
	public void removeSession(String name) {
		session().removeAttribute(name);
	}

}
