package com.asion.business.handler.security.shiro.session;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import com.asion.common.util.IDUtil;

/**
 * Cookie会话标识生成器
 * 
 * @author chenboyang
 *
 */
public class CookieSessionIdGenerator implements SessionIdGenerator {

	@Override
	public Serializable generateId(Session session) {
		String userId = RestSessionManager.USER_ID.get();
		if (StringUtils.isNotBlank(userId)) {
			return IDUtil.createUUID32Upper(userId + session.getHost());
		}
		return IDUtil.createUUID32Upper();
	}

}
