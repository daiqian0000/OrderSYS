package com.asion.business.module.app.service;

import java.io.File;
import java.io.InputStream;

import com.asion.business.module.app.model.AppVersion;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 应用版本表 服务类
 * </p>
 *
 * @author chenboyang-123
 * @since 2018-03-21
 */
public interface AppVersionService extends BaseService<AppVersion> {

	/**
	 * 新增APP版本信息
	 * 
	 * @param appVersion
	 *            APP版本信息
	 * @param file
	 *            文件
	 * @return 操作结果
	 */
	boolean add(AppVersion appVersion, File file);

	/**
	 * 新增APP版本信息
	 * 
	 * @param appVersion
	 *            APP版本信息
	 * @param input
	 *            文件输入流
	 * @return 操作结果
	 */
	boolean add(AppVersion appVersion, InputStream input);

}
