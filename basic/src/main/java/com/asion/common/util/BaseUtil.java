package com.asion.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 基本帮助类
 * 
 * @author chenboyang
 * 
 */
@SuppressWarnings("unchecked")
public abstract class BaseUtil {

	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = Logger.getLogger(BaseUtil.class);

	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	@Deprecated
	public static String createUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成32位UUID
	 * 
	 * @return UUID
	 */
	@Deprecated
	public static String createUUID32() {
		return createUUID().replaceAll("-", "");
	}

	/**
	 * 生成32位大写UUID
	 * 
	 * @return UUID
	 */
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
	public static String createUUID32Upper(String str) {
		return createUUID32(str).toUpperCase();
	}

	/**
	 * MD5加密返回16进制字符串
	 * 
	 * @param str
	 *            字符串
	 * @return MD5代码
	 */
	public static String encodeMD5(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return DigestUtils.md5Hex(str);
	}

	/**
	 * MD5加密返回16进制大写字符串
	 * 
	 * @param str
	 *            字符串
	 * @return MD5代码
	 */
	public static String encodeMD5Upper(String str) {
		return encodeMD5(str).toUpperCase();
	}

	/**
	 * 创建MAP容器
	 * 
	 * @return MAP容器
	 */
	public static <K, V> Map<K, V> createMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 创建MAP容器及设置初始键值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return MAP容器
	 */
	public static <K, V> Map<K, V> createMap(K key, V value) {
		Map<K, V> map = createMap();
		map.put(key, value);
		return map;
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @return 当前时间字符串
	 */
	@Deprecated
	public static String currentTimeMillis() {
		return (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
	}

	/**
	 * 将JAVA对象转为MAP对象
	 * 
	 * @param obj
	 *            JAVA对象
	 * @return MAP对象
	 */
	public static <K, V> Map<K, V> beanToMap(Object obj) {
		Map<K, V> map = null;
		if (obj != null) {
			map = new HashMap<K, V>();
			List<Field> fields = ReflectUtil.getFields(obj.getClass());
			if (CollectionUtils.isNotEmpty(fields)) {
				for (Field field : fields) {
					String key = field.getName();
					Object value = ReflectUtil.getFieldValue(obj, key);
					map.put((K) key, (V) value);
				}
			}
		}
		return map;
	}

	/**
	 * 将MAP对象转为JAVA对象
	 * 
	 * @param map
	 *            MAP对象
	 * @param clazz
	 *            JAVA类型
	 * @return JAVA对象
	 */
	public static <T> T mapToBean(Map<Object, Object> map, Class<T> clazz) {
		T t = null;
		try {
			if (MapUtils.isNotEmpty(map)) {
				t = clazz.newInstance();
				Set<Object> keySet = map.keySet();
				for (Object key : keySet) {
					String methodName = "set" + key.toString().substring(0, 1).toUpperCase()
							+ key.toString().substring(1);
					if (ReflectUtil.hasMethod(clazz, methodName)) {
						ReflectUtil.invokeSetMethod(t, methodName, map.get(key));
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return t;
	}

	/**
	 * 获取properties文件对象
	 * 
	 * @param filePath
	 *            文件路径
	 * @return Properties对象
	 */
	public static Properties getProperties(String filePath) {
		Properties properties = new Properties();
		if (StringUtils.isNotBlank(filePath)) {
			InputStream input = null;
			try {
				input = BaseUtil.class.getClassLoader().getResourceAsStream(filePath);
				if (input != null) {
					properties.load(input);
				} else {
					LOGGER.warn(filePath + "文件输入流为空！");
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return properties;
	}

	/**
	 * 对象类型转换
	 * 
	 * @param value
	 *            需要转换的对象
	 * @param clazz
	 *            转换的目标类型
	 * @return 转换后的对象
	 */
	public static <T> T convert(Object value, Class<T> clazz) {
		return (T) ConvertUtils.convert(value, clazz);
	}

	/**
	 * 设置列表元素
	 * 
	 * @param list
	 *            列表
	 * @param index
	 *            元素下标
	 * @param element
	 *            元素
	 * @return 列表
	 */
	public static <T> List<T> setList(List<T> list, int index, T element) {
		if (index >= list.size()) {
			for (int i = list.size(); i <= index; i++) {
				list.add(null);
			}
		}
		list.set(index, element);
		return list;
	}

	/**
	 * 设置数组元素
	 * 
	 * @param array
	 *            数组
	 * @param index
	 *            元素下标
	 * @param element
	 *            元素
	 * @return 数组
	 */
	public static <T> T[] setArray(T[] array, int index, T element) {
		if (index >= array.length) {
			for (int i = array.length; i <= index; i++) {
				array = ArrayUtils.add(array, null);
			}
		}
		array[index] = element;
		return array;
	}

	/**
	 * 数组去重
	 * 
	 * @param array
	 *            数组
	 * @return 去重后的数组
	 */
	public static <T> T[] arrayDerepeat(T... array) {
		Set<T> set = new TreeSet<T>(Arrays.asList(array));
		return set.toArray((T[]) Array.newInstance(array.getClass().getComponentType(), set.size()));
	}

	/**
	 * 将字符串转基础类型
	 * 
	 * @param value
	 *            字符串
	 * @param clazz
	 *            基础类型
	 * @return 基础类型值
	 */
	public static <T> T castByType(String value, Class<T> clazz) {
		Object result = null;
		if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
			result = StringUtil.isNumeric(value) ? Byte.valueOf(value) : 0;
		} else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
			result = StringUtil.isNumeric(value) ? Short.valueOf(value) : 0;
		} else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			result = StringUtil.isNumeric(value) ? Integer.valueOf(value) : 0;
		} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			result = StringUtil.isNumeric(value) ? Long.valueOf(value) : 0;
		} else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			result = StringUtil.isNumeric(value) ? Float.valueOf(value) : 0;
		} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			result = StringUtil.isNumeric(value) ? Double.valueOf(value) : 0;
		} else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
			result = "true".equals(value) || "1".equals(value);
		} else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
			result = StringUtils.isNotEmpty(value) ? value.charAt(0) : null;
		}
		return (T) result;
	}

}
