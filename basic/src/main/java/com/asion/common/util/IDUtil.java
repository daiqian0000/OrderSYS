package com.asion.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * ID生成帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class IDUtil {

	/**
	 * ID生成最小计数
	 */
	private static final int MIN_COUNT = 1;

	/**
	 * ID生成最大计数
	 */
	private static final int MAX_COUNT = 1000000;

	/**
	 * ID生成记数器
	 */
	private static int idCreateCount = MIN_COUNT;

	/**
	 * 生成长整型唯一ID
	 * @return 长整型ID
	 */
	public static long createLongId() {
		if (idCreateCount >= MAX_COUNT) {
			idCreateCount = MIN_COUNT;
		}
		long id = System.currentTimeMillis();
		id *= MAX_COUNT;
		id += idCreateCount;
		idCreateCount++;
		return id;
	}

	/**
	 * 根据当前时间生成ID
	 * 
	 * @return 当前时间ID
	 */
	public static String createTimeId() {
		return (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
	}

	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	public static String createUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成32位UUID
	 * 
	 * @return UUID
	 */
	public static String createUUID32() {
		return createUUID().replaceAll("-", "");
	}

	/**
	 * 生成32位大写UUID
	 * 
	 * @return UUID
	 */
	public static String createUUID32Upper() {
		return createUUID32().toUpperCase();
	}

	/**
	 * 根据字符串生成UUID
	 * 
	 * @param str
	 *            字符串
	 * @return UUID
	 */
	public static String createUUID(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return UUID.nameUUIDFromBytes(str.getBytes()).toString();
	}

	/**
	 * 根据字符串生成32位UUID
	 * 
	 * @param str
	 *            字符串
	 * @return UUID
	 */
	public static String createUUID32(String str) {
		return createUUID(str).replaceAll("-", "");
	}

	/**
	 * 根据字符串生成32位大写UUID
	 * 
	 * @param str
	 *            字符串
	 * @return UUID
	 */
	public static String createUUID32Upper(String str) {
		return createUUID32(str).toUpperCase();
	}

}
