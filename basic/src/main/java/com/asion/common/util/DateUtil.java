package com.asion.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.asion.common.exception.BusinessException;

/**
 * 日期处理帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class DateUtil {

	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = Logger.getLogger(DateUtil.class);

	/**
	 * 日期正则表达式
	 */
	public static final String DATE_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

	/**
	 * 时间正则表达式
	 */
	public static final String TIME_REGEX = "[0-9]{2}:[0-9]{2}:[0-9]{2}";

	/**
	 * 毫秒正则表达式
	 */
	public static final String MILLISECOND_REGEX = "[0-9]{1,3}";
	
	/**
	 * 日期时间正则表达式
	 */
	public static final String DATETIME_REGEX = DATE_REGEX + " " + TIME_REGEX;

	/**
	 * 时间戳正则表达式
	 */
	public static final String TIMESTAMP_REGEX = DATETIME_REGEX + "." + MILLISECOND_REGEX;

	/**
	 * 星期正则表达式
	 */
	public static final String WEEK_REGEX = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)";

	/**
	 * 月份正则表达式
	 */
	public static final String MONTH_REGEX = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";

	/**
	 * GMT正则表达式
	 */
	public static final String GMT_REGEX = "[0-9]{2} " + MONTH_REGEX + " [0-9]{4} " + TIME_REGEX + " GMT";

	/**
	 * CTS正则表达式
	 */
	public static final String CST_REGEX = WEEK_REGEX + " " + MONTH_REGEX + " [0-9]{2} " + TIME_REGEX + " CST [0-9]{4}";

	/**
	 * WEB-GMT正则表达式
	 */
	public static final String WEB_GMT_REGEX = WEEK_REGEX + " " + MONTH_REGEX + " [0-9]{2} [0-9]{4} " + TIME_REGEX + " GMT\\+[0-9]{4} (.+?)";
	
	/**
	 * 日期字符串格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 日期时间字符串格式
	 */
	public static final String DATETIME_FORMAT = DATE_FORMAT + " HH:mm:ss";

	/**
	 * 时间戳字符串格式
	 */
	public static final String TIMESTAMP_FORMAT = DATETIME_FORMAT+ ".SSS";

	/**
	 * 东八时区
	 */
	public static final String GMT_E_8 = "GMT+8";

	/**
	 * 格式化当前日期字符串
	 * 
	 * @return 日期字符串
	 */
	public static String formatCurDate() {
		return formatDate(new Date(), DATETIME_FORMAT);
	}
	
	/**
	 * 格式化日期默认字符串
	 * 
	 * @param date
	 *            日期
	 * @return 日期字符串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DATETIME_FORMAT);
	}
	
	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 日期字符串
	 */
	public static String formatDate(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}
	
	/**
	 * 根据日期字符串获取日期格式
	 * 
	 * @param value
	 *            日期字符串
	 * @return 日期格式
	 */
	public static String getFormat(String value) {
		String format = null;
		if (StringUtil.isMatche(value, DATE_REGEX)) {
			format = DATE_FORMAT;
		} else if (StringUtil.isMatche(value, DATETIME_REGEX)) {
			format = DATETIME_FORMAT;
		} else if (StringUtil.isMatche(value, TIMESTAMP_REGEX)) {
			format = TIMESTAMP_FORMAT;
		}
		return format;
	}
	
	/**
	 * 增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            增加日期类型
	 * @param amount
	 *            增加数量
	 * @return 增加后的日期
	 */
	public static Date plusDate(Date date, int dateType, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(dateType, amount);
		return calendar.getTime();
	}

	/**
	 * 减少日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            减少日期类型
	 * @param amount
	 *            减少数量
	 * @return 减少后的日期
	 */
	public static Date reduceDate(Date date, int dateType, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(dateType, calendar.get(dateType) - amount);
		return calendar.getTime();
	}

	/**
	 * 设置日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期单位类型
	 * @param amount
	 *            单位数量
	 * @return 设置后的日期
	 */
	public static Date setDate(Date date, int dateType, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(dateType, amount);
		return calendar.getTime();
	}

	/**
	 * 根据日期字符串判断是否为国际标准格式
	 * 
	 * @param value
	 *            日期字符串
	 * @return 是否为国际标准格式
	 */
	public static boolean isStandardFormat(String value) {
		if (StringUtils.isNotBlank(value)) {
			if (StringUtil.isMatche(value, GMT_REGEX)) {
				return true;
			} else if (StringUtil.isMatche(value, CST_REGEX)) {
				return true;
			} else if (StringUtil.isMatche(value, WEB_GMT_REGEX)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取当前时间数量
	 * 
	 * @param timeUnit
	 *            时间单位
	 * @return 时间数量
	 */
	public static long currentTime(TimeUnit timeUnit) {
		return timeNum(System.currentTimeMillis(), timeUnit);
	}
	
	/**
	 * 获取日期的时间数量
	 * 
	 * @param date
	 *            日期
	 * @param timeUnit
	 *            时间单位
	 * @return 时间数量
	 */
	public static long timeNum(Date date, TimeUnit timeUnit) {
		return timeNum(date.getTime(), timeUnit);
	}

	/**
	 * 获取时间戳的时间数量
	 * 
	 * @param timeMillis
	 *            时间戳
	 * @param timeUnit
	 *            时间单位
	 * @return 时间数量
	 */
	public static long timeNum(long timeMillis, TimeUnit timeUnit) {
		double time = 0D;
		if (timeUnit == TimeUnit.NANOSECONDS) {
			time = timeMillis * 1000D * 1000;
		} else if (timeUnit == TimeUnit.MICROSECONDS) {
			time = timeMillis * 1000D;
		} else if (timeUnit == TimeUnit.SECONDS) {
			time = timeMillis / 1000D;
		} else if (timeUnit == TimeUnit.MINUTES) {
			time = timeMillis / 1000D / 60;
		} else if (timeUnit == TimeUnit.HOURS) {
			time = timeMillis / 1000D / 60 / 60;
		} else if (timeUnit == TimeUnit.DAYS) {
			time = timeMillis / 1000D / 60 / 60 / 24;
		} else {
			time = timeMillis;
		}
		return (long) time;
	}
	
	/**
	 * 将时间字符串解析为默认日期对象（默认格式为：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param value
	 *            时间字符串
	 * @return 日期对象
	 */
	public static Date parse(String value) {
		return parse(value, DATETIME_FORMAT);
	}
	
	/**
	 * 将时间字符串解析为日期对象
	 * 
	 * @param value
	 *            时间字符串
	 * @param format
	 *            时间格式
	 * @return 日期对象
	 */
	public static Date parse(String value, String format) {
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(format)) {
			try {
				return (new SimpleDateFormat(format)).parse(value);
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 获取日期的起点时间
	 * 
	 * @param date
	 *            日期
	 * @return 起点日期
	 */
	public static Date dateFirst(Date date) {
		if (date == null) {
			throw new BusinessException("日期不能为空");
		}
		date = DateUtils.setHours(date, 0);
		date = DateUtils.setMinutes(date, 0);
		date = DateUtils.setSeconds(date, 0);
		return date;
	}

	/**
	 * 获取日期的终点时间
	 * 
	 * @param date
	 *            日期
	 * @return 终点日期
	 */
	public static Date dateLast(Date date) {
		if (date == null) {
			throw new BusinessException("日期不能为空");
		}
		date = DateUtils.setHours(date, 23);
		date = DateUtils.setMinutes(date, 59);
		date = DateUtils.setSeconds(date, 59);
		return date;
	}

}
