package com.asion.common.gis.util;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Operator;
import com.esri.core.geometry.Operator.Type;
import com.esri.core.geometry.OperatorBuffer;
import com.esri.core.geometry.OperatorFactoryLocal;
import com.esri.core.geometry.SpatialReference;

/**
 * ESRI空间几何分析帮助类(未完善)
 * 
 * @author chenboyang
 *
 */
public abstract class ESRIGeometryAnalysisUtil {

	/**
	 * 根据类型获取几何操作接口
	 * 
	 * @param type
	 *            操作类型
	 * @return 操作接口
	 */
	public static Operator getOperator(Type type) {
		return OperatorFactoryLocal.getInstance().getOperator(type);
	}

	/**
	 * 缓冲区分区
	 * 
	 * @param geometry
	 *            几何要素
	 * @param wkid
	 *            坐标系ID
	 * @param distance
	 *            缓冲距离
	 * @return 分析后的几何要素
	 */
	public static Geometry buffer(Geometry geometry, int wkid, double distance) {
		return ((OperatorBuffer) getOperator(Operator.Type.Buffer)).execute(geometry, SpatialReference.create(wkid),
				distance, null);
	}

}
