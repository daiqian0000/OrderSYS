package com.asion.common.gis.arcgis.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.asion.common.base.BaseService;
import com.asion.common.gis.arcgis.annotation.FeatureType;
import com.asion.common.gis.arcgis.model.Attribute;
import com.asion.common.gis.arcgis.model.Feature;
import com.asion.common.gis.arcgis.model.FeatureCollection;
import com.asion.common.gis.arcgis.model.FeatureSet;
import com.asion.common.gis.arcgis.model.Field;
import com.asion.common.gis.arcgis.model.LayerDefinition;
import com.asion.common.gis.geometry.Extent;
import com.asion.common.gis.geometry.Geometry;
import com.asion.common.gis.geometry.MultiPoint;
import com.asion.common.gis.geometry.Point;
import com.asion.common.gis.geometry.Polygon;
import com.asion.common.gis.geometry.Polyline;
import com.asion.common.gis.util.WKTUtil;
import com.asion.common.util.ReflectUtil;

/**
 * 要素服务接口
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public interface FeatureService<G extends Geometry, A extends Attribute> extends BaseService<A> {

	/**
	 * 空间几何字段
	 */
	String SHAPE = "shape";

	/**
	 * 加载要素集标识
	 */
	String LOAD_FEATURES = "loadFeatures";

	/**
	 * 获取日志记录器
	 * 
	 * @return 日志记录器
	 */
	default Logger log() {
		return Logger.getLogger(getClass());
	}

	/**
	 * 获取要素类型
	 * 
	 * @return 要素类型
	 */
	default Class<? extends Geometry> geometryClass() {
		return this.getClass().getAnnotation(FeatureType.class).geometry();
	}

	/**
	 * 获取属性类型
	 * 
	 * @return 属性类型
	 */
	default Class<? extends Attribute> attributeClass() {
		return this.getClass().getAnnotation(FeatureType.class).attribute();
	}

	/**
	 * 将要素数据转换为属性数据
	 * 
	 * @param feature
	 *            要素数据
	 * @return 属性数据
	 */
	default A valueAttribute(Feature<G, A> feature) {
		A attribute = null;
		try {
			attribute = feature.getAttributes() != null ? feature.getAttributes() : (A) attributeClass().newInstance();
			if (feature.getGeometry() != null && ReflectUtil.hasField(attribute.getClass(), SHAPE)) {
				BeanUtils.setProperty(attribute, SHAPE, WKTUtil.geometryToWkt(feature.getGeometry()));
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			log().error(ExceptionUtils.getStackTrace(e));
		}
		return attribute;
	}

	/**
	 * 查询要素集合
	 * 
	 * @param map
	 *            查询条件
	 * @return 要素集合
	 */
	default List<Feature<G, A>> features(Map<String, Object> map) {
		List<Feature<G, A>> result = null;
		try {
			List<A> list = list(map);
			if (!CollectionUtils.isEmpty(list)) {
				result = new ArrayList<Feature<G, A>>();
				for (A attribute : list) {
					Feature<G, A> feature = new Feature<G, A>();
					String shape = BeanUtils.getProperty(attribute, SHAPE);
					if (StringUtils.isNotBlank(shape)) {
						feature.setGeometry((G) WKTUtil.wktToGeometry(shape));
						attribute.setObjectid(list.indexOf(attribute));
						BeanUtils.setProperty(attribute, SHAPE, null);
					}
					feature.setAttributes(attribute);
					result.add(feature);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log().error(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	/**
	 * 保存要素
	 * 
	 * @param feature
	 *            要素
	 * @return 操作记录数
	 */
	default boolean saveFeature(Feature<G, A> feature) {
		return add(valueAttribute(feature));
	}

	/**
	 * 更新要素
	 * 
	 * @param feature
	 *            要素
	 * @return 操作记录数
	 */
	default boolean updateFeature(Feature<G, A> feature) {
		return mod(valueAttribute(feature));
	}

	/**
	 * 封装图层信息
	 * 
	 * @param map
	 *            查询条件
	 * @return 图层定义对象
	 */
	default FeatureCollection layer(Map<String, Object> map) {
		String geometryType = null;
		if (geometryClass().equals(Point.class)) {
			geometryType = FeatureCollection.ESRI_GEOMETRY_POINT;
		} else if (geometryClass().equals(MultiPoint.class)) {
			geometryType = FeatureCollection.ESRI_GEOMETRY_MULTI_POINT;
		} else if (geometryClass().equals(Polyline.class)) {
			geometryType = FeatureCollection.ESRI_GEOMETRY_POLYLINE;
		} else if (geometryClass().equals(Polygon.class)) {
			geometryType = FeatureCollection.ESRI_GEOMETRY_POLYGON;
		} else if (geometryClass().equals(Extent.class)) {
			geometryType = FeatureCollection.ESRI_GEOMETRY_EXTENT;
		}
		LayerDefinition layerDefinition = new LayerDefinition(geometryType, Field.getFields(attributeClass()));
		FeatureSet featureSet = new FeatureSet(geometryType);
		if (map.get(LOAD_FEATURES) != null && Boolean.valueOf(map.get(LOAD_FEATURES).toString())) {
			featureSet.setFeatures((List) features(map));
		}
		return new FeatureCollection(layerDefinition, featureSet);
	}

}
