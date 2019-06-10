package com.asion.common.gis.util;

import org.apache.commons.lang3.StringUtils;

import com.asion.common.gis.geometry.Extent;
import com.asion.common.gis.geometry.Geometry;
import com.asion.common.gis.geometry.MultiPoint;
import com.asion.common.gis.geometry.Point;
import com.asion.common.gis.geometry.Polygon;
import com.asion.common.gis.geometry.Polyline;
import com.asion.common.util.JsonUtil;

/**
 * 要素帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class GeometryUtil {
	
	/**
	 * JSON字符串转要素对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param type
	 *            要素类型
	 * @return 要素对象
	 */
	public static Geometry jsonToBean(String json, String type) {
		if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(type)) {
			switch (type) {
				case Geometry.POINT:
					return JsonUtil.toObject(json, Point.class);
				case Geometry.MULTI_POINT:
					return JsonUtil.toObject(json, MultiPoint.class);
				case Geometry.POLYLINE:
					return JsonUtil.toObject(json, Polyline.class);
				case Geometry.POLYGON:
					return JsonUtil.toObject(json, Polygon.class);
				case Geometry.EXTENT:
					return JsonUtil.toObject(json, Extent.class);
			}
		}
		return null;
	}

	/**
	 * 判断坐标是否大概在中国范围
	 * 
	 * @param x
	 *            经度
	 * @param y
	 *            纬度
	 * @return 是否大概在中国范围
	 */
	public static boolean isInChina(double x, double y) {
		return x > 72.004 && x < 137.8347 && y > 0.8293 && y < 55.8271;
	}

}
