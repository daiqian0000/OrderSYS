package com.asion.common.gis.arcgis.controller;

import com.asion.business.web.annotation.RequestModel;
import com.asion.common.gis.arcgis.model.Attribute;
import com.asion.common.gis.arcgis.model.Feature;
import com.asion.common.gis.geometry.Geometry;
import com.asion.common.util.ParamBroker;

/**
 * 要素基础控制器
 * 
 * @author chenboyang
 *
 * @param <G>
 *            几何类型
 * @param <A>
 *            业务类型
 */
public interface FeatureController<G extends Geometry, A extends Attribute> {

	/**
	 * 查询要素集
	 * 
	 * @param param
	 *            查询参数
	 * @return 要素集
	 */
	Object features(@RequestModel ParamBroker<Object, Object> param);

	/**
	 * 保存要素
	 * 
	 * @param feature
	 *            要素
	 * @return 操作记录数
	 */
	Object saveFeature(@RequestModel Feature<G, A> feature);

	/**
	 * 更新要素
	 * 
	 * @param feature
	 *            要素
	 * @return 操作记录数
	 */
	Object updateFeature(@RequestModel Feature<G, A> feature);

	/**
	 * 删除要素
	 * 
	 * @param param
	 *            查询参数
	 * @return 要素集
	 */
	Object deleteFeature(@RequestModel ParamBroker<Object, Object> param);

	/**
	 * 查询图层元数据
	 * 
	 * @param param
	 *            查询参数
	 * @return 图层元数据
	 */
	Object layer(@RequestModel ParamBroker<Object, Object> param);

}
