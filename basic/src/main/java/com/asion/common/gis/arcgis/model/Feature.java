package com.asion.common.gis.arcgis.model;

import com.asion.common.gis.geometry.Geometry;

/**
 * 要素
 * 
 * @author chenboyang
 *
 * @param <G>
 *            几何数据
 * @param <A>
 *            业务数据
 */
public class Feature<G extends Geometry, A extends Attribute> {

	/**
	 * 空间数据
	 */
	private G geometry;

	/**
	 * 业务数据
	 */
	private A attributes;

	/**
	 * 构造函数
	 */
	public Feature() {

	}

	/**
	 * 构造函数
	 * 
	 * @param geometry
	 *            空间数据
	 * @param attributes
	 *            业务数据
	 */
	public Feature(G geometry, A attributes) {
		this.geometry = geometry;
		this.attributes = attributes;
	}

	public G getGeometry() {
		return geometry;
	}

	public void setGeometry(G geometry) {
		this.geometry = geometry;
	}

	public A getAttributes() {
		return attributes;
	}

	public void setAttributes(A attributes) {
		this.attributes = attributes;
	}

}
