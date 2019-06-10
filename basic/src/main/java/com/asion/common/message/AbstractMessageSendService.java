package com.asion.common.message;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.asion.business.sys.service.SysService;
import com.asion.business.sys.storage.StorageType;
import com.asion.common.message.annotation.MessageConfig;
import com.asion.common.model.time.TimeContainerFactory;
import com.asion.common.model.time.TimeMap;
import com.asion.common.redis.storage.value.DefaultRedisValueStorage;
import com.asion.common.redis.storage.value.RedisValueStorage;
import com.asion.common.util.SpringContextUtil;

/**
 * 手机短信发送服务抽象类
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unchecked")
public abstract class AbstractMessageSendService implements MessageSendService {

	/**
	 * 系统应用服务接口
	 */
	@Autowired
	private SysService sysService;
	
	/**
	 * 短信验证码发送校验容器
	 */
	private static final String MOBILE_MESSAGE_VERIFY = "mobile_message_verify";

	/**
	 * 获取消息发送配置信息
	 * 
	 * @return 消息发送配置信息
	 */
	protected MessageConfig messageConfig() {
		return getClass().getAnnotation(MessageConfig.class);
	}

	/**
	 * 生成验证码
	 * 
	 * @param count
	 *            验证码位数
	 * @return 验证码
	 */
	protected String verifyCode(int count) {
		return RandomStringUtils.randomNumeric(count);
	}

	/**
	 * 保存手机号验证码至校验容器
	 * 
	 * @param mobile
	 *            手机号
	 * @param timeoutAndInterval
	 *            过期和间隔时间
	 */
	protected void saveMobileVerify(String mobile, long timeoutAndInterval) {
		put(mobile, new VerifyCodeInfo(timeoutAndInterval), timeoutAndInterval);
	}

	/**
	 * 保存手机号验证码至校验容器
	 * 
	 * @param mobile
	 *            手机号
	 * @param verifyCode
	 *            验证码
	 * @param timeout
	 *            过期时间
	 */
	protected void saveMobileVerify(String mobile, String verifyCode, long timeout) {
		put(mobile, new VerifyCodeInfo(verifyCode), timeout);
	}

	/**
	 * 保存手机号验证码至校验容器
	 * 
	 * @param mobile
	 *            手机号
	 * @param verifyCode
	 *            验证码
	 * @param timeout
	 *            过期时间
	 * @param sendInterval
	 *            发送间隔
	 */
	protected void saveMobileVerify(String mobile, String verifyCode, long timeout, long sendInterval) {
		put(mobile, new VerifyCodeInfo(verifyCode, sendInterval), timeout);
	}

	/**
	 * 获取手机验证码发送校验系统存储
	 * 
	 * @return 系统存储
	 */
	protected TimeMap<String, VerifyCodeInfo> systemStorage() {
		if (!sysService.systemStorage().has(MOBILE_MESSAGE_VERIFY)) {
			TimeMap<String, VerifyCodeInfo> map = TimeContainerFactory.createTimeHashMap(0, 1, 1, TimeUnit.SECONDS);
			sysService.systemStorage().set(MOBILE_MESSAGE_VERIFY, map);
		}
		return sysService.systemStorage().get(MOBILE_MESSAGE_VERIFY);
	}

	/**
	 * 获取手机验证码发送校验Redis存储
	 * 
	 * @return Redis存储
	 */
	protected RedisValueStorage<String, VerifyCodeInfo> redisStorage() {
		return SpringContextUtil.getBean(DefaultRedisValueStorage.class);
	}

	/**
	 * 放置一个时间键值到存储容器中
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param timeout
	 *            过期时间
	 */
	protected void put(String key, VerifyCodeInfo value, long timeout) {
		MessageConfig messageConfig = messageConfig();
		if (messageConfig != null) {
			StorageType storageType = messageConfig.verifyStorageType();
			if (storageType == StorageType.SYSTEM) {
				systemStorage().put(key, value, timeout, TimeUnit.SECONDS);
			} else if (storageType == StorageType.REDIS) {
				redisStorage().set(key, value, timeout, TimeUnit.SECONDS);
			}
		}
	}

	/**
	 * 根据键获取值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	protected VerifyCodeInfo get(String key) {
		MessageConfig messageConfig = messageConfig();
		if (messageConfig != null) {
			StorageType storageType = messageConfig.verifyStorageType();
			if (storageType == StorageType.SYSTEM) {
				return systemStorage().get(key);
			} else if (storageType == StorageType.REDIS) {
				return redisStorage().get(key);
			}
		}
		return null;
	}

	public boolean verifySendInterval(String mobile) {
		VerifyCodeInfo info = get(mobile);
		if (info != null && info.getSendInterval() > 0) {
			return (System.currentTimeMillis() - info.getTime()) / 1000 > info.getSendInterval();
		}
		return true;
	}

	public boolean verifyCodeTimeout(String mobile, String verifyCode) {
		VerifyCodeInfo info = get(mobile);
		if (info != null) {
			return info.getVerifyCode().equalsIgnoreCase(verifyCode);
		}
		return false;
	}

	/**
	 * 验证码信息
	 * 
	 * @author chenboyang
	 *
	 */
	class VerifyCodeInfo {

		/**
		 * 发送时间
		 */
		private long time;

		/**
		 * 发送验证码
		 */
		private String verifyCode;

		/**
		 * 验证码短信发送间隔时间
		 */
		private long sendInterval;

		/**
		 * 构造方法
		 * 
		 * @param verifyCode
		 *            验证码
		 */
		public VerifyCodeInfo(String verifyCode) {
			this(verifyCode, -1);
		}

		/**
		 * 构造方法
		 * 
		 * @param sendInterval
		 *            发送间隔
		 */
		public VerifyCodeInfo(long sendInterval) {
			this(null, sendInterval);
		}

		/**
		 * 构造方法
		 * 
		 * @param verifyCode
		 *            验证码
		 * @param sendInterval
		 *            发送间隔
		 */
		public VerifyCodeInfo(String verifyCode, long sendInterval) {
			this.time = System.currentTimeMillis();
			this.verifyCode = verifyCode;
			this.sendInterval = sendInterval;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getVerifyCode() {
			return verifyCode;
		}

		public void setVerifyCode(String verifyCode) {
			this.verifyCode = verifyCode;
		}

		public long getSendInterval() {
			return sendInterval;
		}

		public void setSendInterval(long sendInterval) {
			this.sendInterval = sendInterval;
		}

	}

}
