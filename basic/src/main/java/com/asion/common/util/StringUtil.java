package com.asion.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串处理帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class StringUtil extends StringUtils {

	/**
	 * 是否匹配正则表达式
	 * 
	 * @param str
	 *            字符串
	 * @param regex
	 *            正则表达式
	 * @return 是否符合
	 */
	public static boolean isMatche(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 获取符合正则表达式的分组
	 * 
	 * @param str
	 *            字符串
	 * @param regex
	 *            正则表达式
	 * @param group
	 *            分组下标
	 * @return 分组
	 */
	public static String getGroup(String str, String regex, int group) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(group).trim();
		}
		return "";
	}

	/**
	 * 字符串是否为数字
	 * 
	 * @param str
	 *            字符串
	 * @return 是否为数字
	 */
	public static boolean isNumeric(String str) {
		return isMatche(str, "-?\\d+?|-?\\d+\\.?\\d+?");
	}
	
	/**
	 * 判断对象是否不为空白
	 * 
	 * @param obj
	 *            对象
	 * @return 是否不为空白
	 */
	public static boolean isNotBlank(Object obj) {
		return obj != null && isNotBlank(obj.toString());
	}

	/**
	 * 判断对象是否为空白
	 * 
	 * @param obj
	 *            对象
	 * @return 是否为空白
	 */
	public static boolean isBlank(Object obj) {
		return obj == null || isBlank(obj.toString());
	}

}
