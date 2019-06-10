package com.asion.common.gis.lbs;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * LBS定位帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class LbsUtil {

	/**
	 * 信号强度差值
	 */
	private static final int INIL = 110;

	/**
	 * LBS定位
	 * 
	 * @param list
	 *            LBS信息列表
	 * @return 坐标
	 */
	public static double[] lbsLocate(List<LbsInfo> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			double x = 0D;
			double y = 0D;
			long totalRssi = 0L;
			for (LbsInfo lbsInfo : list) {
				int rssi = lbsInfo.getRssi() + INIL;
				x += lbsInfo.getX() * rssi;
				y += lbsInfo.getY() * rssi;
				totalRssi += rssi;
			}
			if (totalRssi > 0) {
				return new double[] { x / totalRssi, y / totalRssi };
			}
		}
		return null;
	}

}
