package com.asion.business.web.token;

import org.apache.commons.lang3.StringUtils;

import com.asion.business.sys.service.SysService;
import com.asion.business.sys.service.SysServiceImpl;
import com.asion.common.exception.BusinessException;
import com.asion.common.util.IDUtil;
import com.asion.common.util.SpringContextUtil;

/**
 * Token缓存接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务对象类型
 */
public interface TokenCache<T> {

	/**
	 * Token容器键
	 */
	String TOKEN = "token";

	/**
	 * 默认Token过期时间
	 */
	long TIMEOUT = 30L;

	/**
	 * 获取系统服务
	 * 
	 * @return 系统服务
	 */
	default SysService sysService() {
		return SpringContextUtil.getBean(SysServiceImpl.class);
	}

	/**
	 * 保存Token信息返回Token标识
	 * 
	 * @param id
	 *            Token信息编号
	 * @return Token标识
	 */
	String saveToken(String id);

	/**
	 * 保存Token信息返回Token标识
	 * 
	 * @param id
	 *            Token信息编号
	 * @param info
	 *            Token信息
	 * @return Token标识
	 */
	String saveToken(String id, T info);

	/**
	 * 根据Token标识检查Token是否存在
	 * 
	 * @param token
	 *            Token标识
	 * @return 是否存在
	 */
	boolean checkToken(String token);

	/**
	 * 根据Token标识删除Token信息
	 * 
	 * @param token
	 *            Token标识
	 */
	void deleteToken(String token);

	/**
	 * 根据Token标识获取Token信息
	 * 
	 * @param token
	 *            Token标识
	 * @return Token信息
	 */
	T getToken(String token);

	/**
	 * 根据编号创建Token标识
	 * 
	 * @param id
	 *            编号
	 * @return Token标识
	 */
	default String createToken(String id) {
		if (StringUtils.isBlank(id)) {
			throw new BusinessException("业务编号为空，无法创建token！");
		}
		return IDUtil.createUUID32(id + TOKEN);
	}

	/**
	 * 获取超时时间（单位：分钟）
	 * 
	 * @return 超时时间
	 */
	default long timeout() {
		return sysService().sysProperties().getTimeout();
	}

}
