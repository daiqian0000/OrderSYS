package com.asion.common.job;

import org.apache.log4j.Logger;

import com.asion.common.job.impl.RedisToMapperExecutorImpl;

/**
 * 任务执行器抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractJobExecutor {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Redis到数据访问层执行器
	 */
	private static RedisToMapperExecutor redisToMapperExecutor;

	/**
	 * Redis到数据访问层执行器
	 * 
	 * @return 执行器
	 */
	public static RedisToMapperExecutor redisToMapperExecutor() {
		if (redisToMapperExecutor == null) {
			redisToMapperExecutor = new RedisToMapperExecutorImpl();
		}
		return redisToMapperExecutor;
	}
	
	/**
	 * 单向任务操作日志记录
	 * 
	 * @param sourceType
	 *            源类型
	 * @param souceName
	 *            源名称
	 * @param operate
	 *            操作名称
	 * @param num
	 *            操作记录数
	 */
	protected void singleOperateLog(String sourceType, String souceName, String operate, int num) {
		StringBuilder sb = new StringBuilder();
		sb.append("从").append(sourceType).append("‘").append(souceName).append("’").append("已").append(operate)
				.append(num).append("条数据。");
		logger.info(sb.toString());
	}

	/**
	 * 双向任务操作日志记录
	 * 
	 * @param sourceType
	 *            源类型
	 * @param souceName
	 *            源名称
	 * @param operate
	 *            操作名称
	 * @param num
	 *            操作记录数
	 * @param destinationType
	 *            目标类型
	 * @param destinationName
	 *            目标名称
	 */
	protected void doubleOperateLog(String sourceType, String souceName, String operate, int num,
			String destinationType, String destinationName) {
		StringBuilder sb = new StringBuilder();
		sb.append("从").append(sourceType).append("‘").append(souceName).append("’").append("已").append(operate)
				.append(num).append("条数据到").append(destinationType).append("‘").append(destinationName).append("’。");
		logger.info(sb.toString());
	}

}
