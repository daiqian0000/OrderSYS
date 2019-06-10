package com.asion.business.module.attach.service;

import com.asion.business.module.attach.model.Bucket;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 附件存储模块表 : 用于存放系统各业务的附件信息 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface BucketService extends BaseService<Bucket> {

	/**
	 * 存储板块后缀
	 */
	String BUCKET_SUFFIX = ".files";

}
