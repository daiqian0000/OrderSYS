package com.asion.common.gis.service.gaode.geocode;

import java.util.List;

import com.asion.common.gis.geometry.Point;
import com.asion.common.gis.service.gaode.GaodeParameter;

/**
 * 高德逆向地理编码服务请求参数类
 * 
 * @author chenboyang
 *
 */
public class RegeoParameter extends GaodeParameter {

	/**
	 * 经纬度坐标
	 */
	private List<Point> location;

	/**
	 * 返回附近POI类型
	 */
	private List<String> poitype;

	/**
	 * 搜索半径(radius取值范围在0~3000，默认是1000。单位：米)
	 */
	private int radius = 3000;

	/**
	 * 批量查询控制
	 */
	private boolean batch;

	/**
	 * 道路等级
	 */
	private int roadlevel;

	/**
	 * 是否优化POI返回顺序
	 */
	private int homeorcorp;

	public List<Point> getLocation() {
		return location;
	}

	public void setLocation(List<Point> location) {
		this.location = location;
	}

	public List<String> getPoitype() {
		return poitype;
	}

	public void setPoitype(List<String> poitype) {
		this.poitype = poitype;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public boolean isBatch() {
		return batch;
	}

	public void setBatch(boolean batch) {
		this.batch = batch;
	}

	public int getRoadlevel() {
		return roadlevel;
	}

	public void setRoadlevel(int roadlevel) {
		this.roadlevel = roadlevel;
	}

	public int getHomeorcorp() {
		return homeorcorp;
	}

	public void setHomeorcorp(int homeorcorp) {
		this.homeorcorp = homeorcorp;
	}
	
}
