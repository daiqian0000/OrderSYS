package com.asion.business.module.attach.service;

import java.util.List;

import com.asion.business.module.attach.model.Attach;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 附件表 : 用于存放系统各业务的附件信息 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface AttachService extends BaseService<Attach> {

	/**
	 * 新增附件列表
	 * 
	 * @param attachList
	 *            附件列表
	 * @param inputs
	 *            文件输入流集
	 * @return 操作结果
	 */
	boolean addList(List<Attach> attachList);

	/**
	 * 更新附件列表
	 * 
	 * @param attachList
	 *            附件列表
	 * @param inputs
	 *            文件输入流集
	 * @return 操作结果
	 */
	boolean modList(List<Attach> attachList);

}
