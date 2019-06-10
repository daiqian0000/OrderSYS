package com.asion.common.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 程序调度执行器帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class ExecutorUtil {

	/**
	 * 获取执行器
	 * 
	 * @return 执行器
	 */
	public static ScheduledExecutorService scheduledExecutor() {
		return Executors.newSingleThreadScheduledExecutor();
	}

	/**
	 * 执行监听
	 * 
	 * @param command
	 *            监听内容
	 * @param initialDelay
	 *            执行延迟时间
	 * @param period
	 *            执行间隔时间
	 * @param unit
	 *            时间单位
	 */
	public static void addListener(Runnable command, long initialDelay, long period, TimeUnit unit) {
		scheduledExecutor().scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	/**
	 * 增加延时执行程序
	 * 
	 * @param command
	 *            程序内容
	 * @param delay
	 *            延时时间
	 * @param unit
	 *            时间单位
	 */
	public static void addSchedule(Runnable command, long delay, TimeUnit unit) {
		scheduledExecutor().schedule(command, delay, unit);
	}

	/**
	 * 立即执行程序
	 * 
	 * @param command
	 *            程序
	 */
	public static void execute(Runnable command) {
		if (command != null) {
			command.run();
		}
	}

}
