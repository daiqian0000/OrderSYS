package com.asion.business.sys.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * 默认系统存储实现类
 * 
 * @author chenboyang
 *
 */
@Component
public class DefaultSystemStorage extends AbstractSystemStorage {

	/**
	 * 默认系统存储容器
	 */
	private Map<Object, Object> storage = new ConcurrentHashMap<Object, Object>();

	@Override
	protected Map<Object, Object> getStorage() {
		return storage;
	}

}
