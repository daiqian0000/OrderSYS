package com.asion.common.util;

import java.math.BigDecimal;

/**
 * 数值处理帮助类
 * 
 * @author chenboyang
 * 
 */
public abstract class NumberUtil {

	/**
	 * 小数位舍入方法
	 * 
	 * @param num
	 *            数值
	 * @param scale
	 *            保留小数位
	 * @return 计算后数值
	 */
	public static double numberScale(double num, int scale) {
		return numberScale(num, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 小数位舍入方法
	 * 
	 * @param num
	 *            数值
	 * @param scale
	 *            保留小数位
	 * @param roundingMode
	 *            舍入方式
	 * @return 计算后数值
	 */
	public static double numberScale(double num, int scale, int roundingMode) {
		return new BigDecimal(num).setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * 小数位舍入方法
	 * 
	 * @param num
	 *            数值
	 * @param scale
	 *            保留小数位
	 * @return 计算后数值
	 */
	public static float numberScale(float num, int scale) {
		return numberScale(num, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 小数位舍入方法
	 * 
	 * @param num
	 *            数值
	 * @param scale
	 *            保留小数位
	 * @param roundingMode
	 *            舍入方式
	 * @return 计算后数值
	 */
	public static float numberScale(float num, int scale, int roundingMode) {
		return new BigDecimal(num).setScale(scale, roundingMode).floatValue();
	}
	
}
