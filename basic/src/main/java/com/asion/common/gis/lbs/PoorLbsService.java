package com.asion.common.gis.lbs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 垃圾LBS服务接口
 * 
 * @author chenboyang
 *
 */
public class PoorLbsService implements LbsService {

	/**
	 * 接口API地址
	 */
	private static final String API = "http://api.cellocation.com:81/cell/";

	/**
	 * 参数模板
	 */
	private static final String PARAM_TEMP = "?mcc={mcc}&mnc={mnc}&lac={lac}&ci={ci}&coord={coord}&output=json";

	/**
	 * HTTP请求地址
	 */
	private static final String URL = API + PARAM_TEMP;

	/**
	 * REST接口访问模板
	 */
	private RestTemplate restTemplate;

	public PoorLbsService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public LbsInfo locate(LbsInfo lbsInfo, CoordType coordType) {
		if (lbsInfo == null) {
			return null;
		}
		ResponseEntity<LocateResult> response = restTemplate.getForEntity(URL, LocateResult.class, paramMap(lbsInfo, coordType));
		LocateResult result = response.getBody();
		if (result.getErrcode() == 0) {
			lbsInfo.setX(result.getLon());
			lbsInfo.setY(result.getLat());
			lbsInfo.setAddress(result.getAddress());
		}
		return lbsInfo;
	}

	@Override
	public LbsInfo locate(List<LbsInfo> lbsInfoList, CoordType coordType) {
		if (CollectionUtils.isEmpty(lbsInfoList)) {
			return null;
		}
		if (lbsInfoList.size() == 1) {
			return locate(lbsInfoList.get(0), coordType);
		}
		lbsInfoList.forEach((lbsInfo) -> locate(lbsInfo, coordType));
		double[] coord = LbsUtil.lbsLocate(lbsInfoList);
		LbsInfo last = lbsInfoList.get(lbsInfoList.size() - 1);
		LbsInfo lbsInfo = new LbsInfo();
		lbsInfo.setX(coord[0]);
		lbsInfo.setY(coord[1]);
		lbsInfo.setAddress(last.getAddress());
		return lbsInfo;
	}

	/**
	 * 封装参数MAP
	 * 
	 * @param lbsInfo
	 *            基站信息
	 * @param coordType
	 *            坐标类型
	 * @return 参数MAP
	 */
	private Map<String, Object> paramMap(LbsInfo lbsInfo, CoordType coordType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mcc", lbsInfo.getMcc());
		map.put("mnc", lbsInfo.getMnc());
		map.put("lac", lbsInfo.getLac());
		map.put("ci", lbsInfo.getCi());
		String coord = null;
		switch (coordType) {
		case GCJ02:
			coord = CoordType.GCJ02.toString().toLowerCase();
			break;
		case BD09:
			coord = CoordType.BD09.toString().toLowerCase();
			break;
		default:
			coord = CoordType.WGS84.toString().toLowerCase();
			break;
		}
		map.put("coord", coord);
		return map;
	}

	/**
	 * 定位查询结果类
	 * 
	 * @author chenboyang
	 *
	 */
	static class LocateResult {

		/**
		 * 操作结果代码
		 */
		private int errcode;

		/**
		 * 纬度
		 */
		private double lat;

		/**
		 * 经度
		 */
		private double lon;

		/**
		 * 覆盖
		 */
		private int radius;

		/**
		 * 
		 */
		private String address;

		public int getErrcode() {
			return errcode;
		}

		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLon() {
			return lon;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		public int getRadius() {
			return radius;
		}

		public void setRadius(int radius) {
			this.radius = radius;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

	}

}
