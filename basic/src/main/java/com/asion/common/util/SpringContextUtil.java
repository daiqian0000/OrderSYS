package com.asion.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <h3>Spring辅助类</h3>
 * <p>
 * 主要用于操作SpringBean容器内的各种Bean
 * </p>
 * 
 * @author chenboyang
 * @see com.asion.common.util.SpringContextUtil
 */
@Component
@SuppressWarnings("unchecked")
public class SpringContextUtil implements ApplicationContextAware {
	
	/**
	 * 日志记录
	 */
	private static Logger logger = Logger.getLogger(SpringContextUtil.class);
	
	/**
	 * Spring上下文对象
	 */
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 根据Bean的类型获取SpringBean容器内的Bean对象
	 * 
	 * @param clazz
	 *            Bean的类型
	 * @return Bean对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		try {
			if (applicationContext != null) {
				return applicationContext.getBean(clazz);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 根据Bean的名称获取SpringBean容器内的Bean对象
	 * 
	 * @param name
	 *            名称
	 * @return Bean对象
	 */
	public static <T> T getBean(String name) {
		try {
			if (applicationContext != null) {
				return (T) applicationContext.getBean(name);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据Bean的名称和类型获取SpringBean容器内的Bean对象
	 * 
	 * @param name
	 *            名称
	 * @param clazz
	 *            Bean的类型
	 * @return Bean对象
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		try {
			if (applicationContext != null) {
				return applicationContext.getBean(name, clazz);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 初始化Spring上下文
	 * 
	 * @param paths
	 *            Spring配置文件路径
	 * @return Spring上下文
	 */
	@Deprecated
	public static ClassPathXmlApplicationContext initContext(String... paths) {
		if (ArrayUtils.isEmpty(paths)) {
			paths = new String[] { "classpath:spring/spring-beans.xml" };
		}
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(paths);
		applicationContext = context;
		return context;
	}
	
	/**
	 * 是否为初次加载上下文
	 * 
	 * @param event
	 *            加载上下文事件
	 * @return 是否为初次
	 */
	public static boolean isFirst(ApplicationContextEvent event) {
		return event.getApplicationContext().getParent() == null;
	}

}

