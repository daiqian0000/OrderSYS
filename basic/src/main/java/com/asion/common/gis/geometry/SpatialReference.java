package com.asion.common.gis.geometry;

/**
 * 空间参考
 * 
 * @author chenboyang
 *
 */
public class SpatialReference {

	/**
	 * 坐标系编号
	 */
	private int wkid;

	/**
	 * WKT数据
	 */
	private String wkt;

	/**
	 * 构造函数
	 */
	public SpatialReference() {
		this(4326);
	}

	/**
	 * 构造函数
	 * 
	 * @param wkid
	 *            坐标系编号
	 */
	public SpatialReference(int wkid) {
		this.wkid = wkid;
	}

	public int getWkid() {
		return wkid;
	}

	public void setWkid(int wkid) {
		this.wkid = wkid;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

}
