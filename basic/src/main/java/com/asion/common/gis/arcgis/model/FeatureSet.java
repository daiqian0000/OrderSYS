package com.asion.common.gis.arcgis.model;

import java.util.ArrayList;
import java.util.List;

import com.asion.common.gis.geometry.Geometry;

/**
 * 要素集
 * 
 * @author chenboyang
 */
public class FeatureSet {

	/**
	 * 几何类型
	 */
	private String geometryType;

	/**
	 * 要素集合
	 */
	private List<Feature<Geometry, Attribute>> features;

	/**
	 * 构造函数
	 */
	public FeatureSet() {

	}

	/**
	 * 构造函数
	 * 
	 * @param geometryType
	 *            几何类型
	 */
	public FeatureSet(String geometryType) {
		this(geometryType, new ArrayList<Feature<Geometry, Attribute>>());
	}

	/**
	 * 构造函数
	 * 
	 * @param geometryType
	 *            几何类型
	 * @param features
	 *            要素集合
	 */
	public FeatureSet(String geometryType, List<Feature<Geometry, Attribute>> features) {
		this.geometryType = geometryType;
		this.features = features;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public List<Feature<Geometry, Attribute>> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature<Geometry, Attribute>> features) {
		this.features = features;
	}

}
