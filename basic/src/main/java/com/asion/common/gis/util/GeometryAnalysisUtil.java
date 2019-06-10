//package com.asion.common.gis.util;
//
//import org.apache.log4j.Logger;
//
//import com.vividsolutions.jts.io.ParseException;
//import com.vividsolutions.jts.io.WKTReader;
//import com.vividsolutions.jts.precision.CommonBitsOp;
//
///**
// * 几何分析帮助类
// *
// * @author chenboyang
// *
// */
//public abstract class GeometryAnalysisUtil {
//
//	/**
//	 * 日志记录
//	 */
//	private static Logger logger = Logger.getLogger(GeometryAnalysisUtil.class);
//
//	/**
//	 * 通用几何处理器
//	 */
//	private static CommonBitsOp commonBitsOp = null;
//
//	/**
//	 * WKT字符数据解读器
//	 */
//	private static WKTReader wktReader = null;
//
//	/**
//	 * 获取几何处理器
//	 *
//	 * @return 几何处理器
//	 */
//	public static CommonBitsOp getCommonBitsOp() {
//		if (commonBitsOp == null) {
//			commonBitsOp = new CommonBitsOp();
//		}
//		return commonBitsOp;
//	}
//
//	/**
//	 * 获取WKT解读器
//	 *
//	 * @return WKT解读器
//	 */
//	public static WKTReader getWKTReader() {
//		if (wktReader == null) {
//			wktReader = new WKTReader();
//		}
//		return wktReader;
//	}
//
//	/**
//	 * 几何相交分析并返回相交区域几何
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 相交区域几何
//	 */
//	public static String intersection(String geom0, String geom1) {
//		try {
//			return getCommonBitsOp().intersection(getWKTReader().read(geom0), getWKTReader().read(geom1)).toText();
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 几何合并分析并返回合并后的几何
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 合并后的几何
//	 */
//	public static String union(String geom0, String geom1) {
//		try {
//			return getCommonBitsOp().union(getWKTReader().read(geom0), getWKTReader().read(geom1)).toText();
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 几何差异分析并返回要素1的差异区域几何
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 要素1的差异区域几何
//	 */
//	public static String difference(String geom0, String geom1) {
//		try {
//			return getCommonBitsOp().difference(getWKTReader().read(geom0), getWKTReader().read(geom1)).toText();
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 几何对称差异分析并返回要素1和要素2的差异区域几何
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 对称差异区域几何
//	 */
//	public static String symDifference(String geom0, String geom1) {
//		try {
//			return getCommonBitsOp().symDifference(getWKTReader().read(geom0), getWKTReader().read(geom1)).toText();
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 几何缓冲区分析并返回分析后的几何,几何数据为米制
//	 *
//	 * @param geom0
//	 *            要素1(米制)
//	 * @param distance
//	 *            距离(单位:米)
//	 * @return 缓冲区
//	 */
//	public static String buffer(String geom0, double distance) {
//		try {
//			return getCommonBitsOp().buffer(getWKTReader().read(geom0), distance).toText();
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 几何缓冲区分析并返回分析后的几何,几何数据为度制
//	 *
//	 * @param geom0
//	 *            要素1(度制)
//	 * @param distance
//	 *            距离(单位:米)
//	 * @return 缓冲区
//	 */
//	public static String bufferOfDegree(String geom0, double distance) {
//		return buffer(geom0, distance / (2 * Math.PI * 6371004) * 360);
//	}
//
//	/**
//	 * 要素1是否覆盖要素2
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 是否覆盖
//	 */
//	public static boolean covers(String geom0, String geom1) {
//		try {
//			return getWKTReader().read(geom0).covers(getWKTReader().read(geom1));
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return false;
//	}
//
//	/**
//	 * 要素1与要素2是否有相交
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 是否有相交
//	 */
//	public static boolean intersects(String geom0, String geom1) {
//		try {
//			return getWKTReader().read(geom0).intersects(getWKTReader().read(geom1));
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return false;
//	}
//
//	/**
//	 * 要素1是否包含要素2
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 是否包含
//	 */
//	public static boolean contains(String geom0, String geom1) {
//		try {
//			return getWKTReader().read(geom0).contains(getWKTReader().read(geom1));
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return false;
//	}
//
//	/**
//	 * 计算两个要素之间的距离
//	 *
//	 * @param geom0
//	 *            要素1
//	 * @param geom1
//	 *            要素2
//	 * @return 距离
//	 */
//	public static double distance(String geom0, String geom1) {
//		try {
//			return getWKTReader().read(geom0).distance(getWKTReader().read(geom1));
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return 0;
//	}
//
//}
