package com.asion.common.model.time;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.collections.MapUtils;

import com.asion.common.util.DateUtil;
import com.asion.common.util.ExecutorUtil;

/**
 * 时间哈希容器实现类
 * 
 * @author chenboyang
 *
 * @param <K>
 *            键类型
 * @param <V>
 *            值类型
 */
public class TimeHashMap<K, V> extends TimeContainer implements TimeMap<K, V>, Cloneable, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6085791531955515931L;

	/**
	 * 时间元素存储容器
	 */
	private Map<K, TimeElement<V>> container;

	/**
	 * 哈希时间容器构建方法
	 */
	public TimeHashMap() {
		this(0L);
	}

	/**
	 * 哈希时间容器构建方法
	 * 
	 * @param timeout
	 *            时间元素默认过期时间（单位：毫秒）
	 */
	public TimeHashMap(long timeout) {
		this(timeout, TimeUnit.MILLISECONDS);
	}

	/**
	 * 哈希时间容器构建方法
	 * 
	 * @param timeout
	 *            时间元素默认过期时间
	 * @param timeUnit
	 *            监听器执行时间单位和时间元素默认时间单位
	 */
	public TimeHashMap(long timeout, TimeUnit timeUnit) {
		this(100L, timeout, timeUnit);
	}

	/**
	 * 哈希时间容器构建方法
	 * 
	 * @param interval
	 *            监听器执行间隔时间
	 * @param timeout
	 *            时间元素默认过期时间
	 * @param timeUnit
	 *            监听器执行时间单位和时间元素默认时间单位
	 */
	public TimeHashMap(long interval, long timeout, TimeUnit timeUnit) {
		this(0L, interval, timeout, timeUnit);
	}

	/**
	 * 哈希时间容器构建方法
	 * 
	 * @param delay
	 *            监听器延迟执行时间
	 * @param interval
	 *            监听器执行间隔时间
	 * @param timeout
	 *            时间元素默认过期时间
	 * @param timeUnit
	 *            监听器执行时间单位和时间元素默认时间单位
	 */
	public TimeHashMap(long delay, long interval, long timeout, TimeUnit timeUnit) {
		this.delay = delay;
		this.interval = interval;
		this.timeout = timeout;
		this.timeUnit = timeUnit;
		this.container = new ConcurrentHashMap<K, TimeElement<V>>();
		this.startListener();
	}

	public Map<K, TimeElement<V>> container() {
		return container;
	}

	protected void startListener() {
		Map<K, TimeElement<V>> map = container();
		ExecutorUtil.addListener(new Thread() {
			public void run() {
				if (MapUtils.isNotEmpty(map)) {
					Set<Entry<K, TimeElement<V>>> entrys = container().entrySet();
					entrys.forEach(new Consumer<Entry<K, TimeElement<V>>>() {
						public void accept(Entry<K, TimeElement<V>> entry) {
							TimeElement<V> timeElement = entry.getValue();
							long currTime = DateUtil.currentTime(timeElement.getTimeUnit());
							long elementTimeout = timeElement.getTimeout();
							long realtime = currTime - timeElement.getStartTime();
							if (elementTimeout > 0 && realtime > elementTimeout) {
								container().remove(entry.getKey());
							}
						}
					});
				}
			}
		}, delay(), interval(), timeUnit());
	}

}
