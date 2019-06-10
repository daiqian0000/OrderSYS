package com.asion.common.gis.arcgis.model;

/**
 * 要素集合类
 * 
 * @author chenboyang
 */
public class FeatureCollection {

	/**
	 * ESRI几何点
	 */
	public static final String ESRI_GEOMETRY_POINT = "esriGeometryPoint";
	
	/**
	 * ESRI几何点簇
	 */
	public static final String ESRI_GEOMETRY_MULTI_POINT = "esriGeometryMultiPoint";
	
	/**
	 * ESRI几何线
	 */
	public static final String ESRI_GEOMETRY_POLYLINE = "esriGeometryPolyline";
	
	/**
	 * ESRI几何面
	 */
	public static final String ESRI_GEOMETRY_POLYGON = "esriGeometryPolygon";
	
	/**
	 * ESRI几何范围
	 */
	public static final String ESRI_GEOMETRY_EXTENT = "esriGeometryExtent";
	
	/**
	 * 图层定义
	 */
	private LayerDefinition layerDefinition;
	
	/**
	 * 要素集
	 */
	private FeatureSet featureSet;
	
	/**
	 * 构造函数
	 */
	public FeatureCollection() {
		
	}
	
	/**
	 * 构造函数
	 * 
	 * @param layerDefinition
	 *            图层定义
	 * @param featureSet
	 *            要素集
	 */
	public FeatureCollection(LayerDefinition layerDefinition, FeatureSet featureSet) {
		this.layerDefinition = layerDefinition;
		this.featureSet = featureSet;
	}

	public LayerDefinition getLayerDefinition() {
		return layerDefinition;
	}

	public void setLayerDefinition(LayerDefinition layerDefinition) {
		this.layerDefinition = layerDefinition;
	}

	public FeatureSet getFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(FeatureSet featureSet) {
		this.featureSet = featureSet;
	}

}
