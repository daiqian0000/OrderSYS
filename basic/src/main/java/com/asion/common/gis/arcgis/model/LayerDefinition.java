package com.asion.common.gis.arcgis.model;

import java.util.List;

/**
 * 图层定义类
 * 
 * @author chenboyang
 *
 */
public class LayerDefinition {

	/**
	 * 主键字段
	 */
	public static final String PK_FIELD = "objectid";

	/**
	 * 几何类型
	 */
	private String geometryType;

	/**
	 * 对象编号字段
	 */
	private String objectIdField = PK_FIELD;

	/**
	 * 字段集
	 */
	private List<Field> fields;

	/**
	 * 构造函数
	 */
	public LayerDefinition() {

	}

	/**
	 * 构造函数
	 * 
	 * @param geometryType
	 *            几何类型
	 * @param fields
	 *            字段集
	 */
	public LayerDefinition(String geometryType, List<Field> fields) {
		this(geometryType, PK_FIELD, fields);
	}

	/**
	 * 构造函数
	 * 
	 * @param geometryType
	 *            几何类型
	 * @param objectIdField
	 *            对象编号字段
	 * @param fields
	 *            字段集
	 */
	public LayerDefinition(String geometryType, String objectIdField, List<Field> fields) {
		this.geometryType = geometryType;
		this.objectIdField = objectIdField;
		this.fields = fields;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public String getObjectIdField() {
		return objectIdField;
	}

	public void setObjectIdField(String objectIdField) {
		this.objectIdField = objectIdField;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
