package com.asion.business.module.area.service;

import java.util.List;

import com.asion.business.module.area.model.Area;
import com.asion.business.tree.model.Tree;
import com.asion.business.tree.service.BusinessTreeService;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface AreaService extends BaseService<Area>, BusinessTreeService<Area> {

	/**
	 * 地区树容器键
	 */
	String AREA_TREE = "area_tree";

	/**
	 * 根据上级地区编码加载地区树状列表
	 * 
	 * @param areaId
	 *            上级地区编码
	 * @return 树状列表
	 */
	List<Tree> tree(int areaId);

}
