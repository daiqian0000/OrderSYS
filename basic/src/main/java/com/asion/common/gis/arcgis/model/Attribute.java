package com.asion.common.gis.arcgis.model;

/**
 * 空间要素业务属性类
 * 
 * @author chenboyang
 * 
 */
public abstract class Attribute {

	/**
	 * 对象编号
	 */
	protected Integer objectid;

	public Integer getObjectid() {
		return objectid;
	}

	public void setObjectid(Integer objectid) {
		this.objectid = objectid;
	}

}
