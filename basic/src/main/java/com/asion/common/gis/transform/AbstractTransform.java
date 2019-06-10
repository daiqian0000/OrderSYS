package com.asion.common.gis.transform;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 坐标转换接口抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractTransform implements Transform {

	/**
	 * 坐标转换器容器
	 */
	private static Map<TransformRelation, Transform> transformMap = null;

	/**
	 * 坐标转换服务接口
	 */
	private static TransformService transformService = null;

	/**
	 * 获取坐标转换器容器
	 * 
	 * @return 坐标转换器容器
	 */
	private static Map<TransformRelation, Transform> transformMap() {
		if (transformMap == null) {
			transformMap = new ConcurrentHashMap<TransformRelation, Transform>();
		}
		return transformMap;
	}

	/**
	 * 根据坐标转换关系枚举获取坐标转换器
	 * 
	 * @param transformRelation
	 *            坐标转换关系枚举
	 * @return 坐标转换器
	 */
	protected static Transform getTransform(TransformRelation transformRelation) {
		if (!transformMap().containsKey(transformRelation)) {
			Transform transform = null;
			switch (transformRelation) {
			case WGS84_TO_MERCATOR:
				transform = new Wgs84ToMercatorTransform();
				break;
			case MERCATOR_TO_WGS84:
				transform = new MercatorToWgs84Transform();
				break;
			case WGS84_TO_GCJ02:
				transform = new Wgs84ToGcj02Transform();
				break;
			case GCJ02_TO_WGS84:
				transform = new Gcj02ToWgs84Transform();
				break;
			case GCJ02_TO_BD09:
				transform = new Gcj02ToBd09Transform();
				break;
			case BD09_TO_GCJ02:
				transform = new Bd09ToGcj02Transform();
				break;
			}
			transformMap().put(transformRelation, transform);
		}
		return transformMap().get(transformRelation);
	}

	/**
	 * 获取坐标转换服务接口
	 * 
	 * @return 坐标转换服务接口
	 */
	public static TransformService transformService() {
		if (transformService == null) {
			transformService = new TransformServiceImpl();
		}
		return transformService;
	}

}
