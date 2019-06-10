package com.asion.business.module.org.service;

import java.util.List;

import com.asion.business.module.org.model.Org;
import com.asion.business.tree.model.Tree;
import com.asion.business.tree.service.BusinessTreeService;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 机构表 : 系统操作员的机构管理 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface OrgService extends BaseService<Org>, BusinessTreeService<Org> {

	/**
	 * 根据上级机构编码加载机构树状列表
	 * 
	 * @param parentId
	 *            上级机构编码
	 * @return 树状列表
	 */
	List<Tree> tree(String parentId);

	/**
	 * 上移
	 * 
	 * @param org
	 *            条件参数
	 * @return 操作是否成功
	 */
	boolean up(Org org);
	
	/**
	 * 下移
	 * 
	 * @param org
	 *            条件参数
	 * @return 操作是否成功
	 */
	boolean down(Org org);
	
}
