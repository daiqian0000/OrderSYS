package com.asion.business.web.resolver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import com.asion.business.web.annotation.RequestModel;
import com.asion.common.util.DateUtil;
import com.asion.common.util.PageBroker;
import com.asion.common.util.ParamBroker;
import com.asion.common.util.ReflectUtil;
import com.asion.common.util.StringUtil;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * SpringMVC请求参数模型解析器
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class RequestModelResolver implements HandlerMethodArgumentResolver {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 对象正则表达式
	 */
	private static final String OBJECT_REGEX = ".*?\\[.+?\\].*?";

	/**
	 * 分隔符
	 */
	private static final String SEPARATOR = "\\.";

	/**
	 * 属性标识
	 */
	private static final String PROPERTY_MARK = ".";

	/**
	 * 数组前缀
	 */
	private static final String ARRAY_PREFIX = "[";

	/**
	 * 数组后缀
	 */
	private static final String ARRAY_SUFFIX = "]";

	/**
	 * 数组标识
	 */
	private static final String ARRAY_MARK = ARRAY_PREFIX + ARRAY_SUFFIX;

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestModel.class);
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
		String name = parameter.getParameterAnnotation(RequestModel.class).value();
		Class<?> type = parameter.getParameterType();
		if (type.isPrimitive() || String.class.equals(type) || ReflectUtil.isWrapClass(type)) {
			return ConvertUtils.convert(request.getParameter(name), type);
		} else {
			Map<String, Object> params = WebUtils.getParametersStartingWith(request.getNativeRequest(HttpServletRequest.class), name);
			Object object = ReflectUtil.createObject(type);
			WebDataBinder binder = binderFactory.createBinder(request, object, name);
			if (ParamBroker.class.equals(type)) {
				ParamBroker<String, Object> paramBroker = binder.convertIfNecessary(object, ParamBroker.class);
				params.forEach((k, v) -> setMap(binder, getKeys(k), v, paramBroker.getParam()));
			} else if (PageBroker.class.equals(type)) {
				PageBroker<?> pageBroker = binder.convertIfNecessary(object, PageBroker.class);
				params.forEach((k, v) -> setMap(binder, getKeys(k), v, pageBroker.getPage()));
			} else if (Page.class.equals(type)) {
				Page<?> page = binder.convertIfNecessary(object, Page.class);
				if (page.getCondition() == null) {
					page.setCondition(new HashMap<String, Object>());
				}
				params.forEach((k, v) -> {
					if (ReflectUtil.hasField(Page.class, k)) {
						ReflectUtil.setPropertyValue(page, k, v);
					} else {
						setMap(binder, getKeys(k), v, page.getCondition());
					}
				});
			} else {
				for (String key : params.keySet()) {
					object = setParameter(binder, type, object, getKeys(key), params.get(key));
				}
			}
			return binder.convertIfNecessary(object, type);
		}
	}

	/**
	 * 获取参数纵向名称集
	 * 
	 * @param key
	 *            参数名
	 * @return 名称集
	 */
	private String[] getKeys(String key) {
		String[] keys = null;
		String keyStr = null;
		if (StringUtils.isNotBlank(key)) {
			keyStr = key.trim();
			if (StringUtil.isMatche(keyStr, OBJECT_REGEX) || keyStr.contains(PROPERTY_MARK)) {
				keyStr = keyStr.replace(ARRAY_SUFFIX, "")
						.replace(ARRAY_SUFFIX + ARRAY_MARK, "")
						.replace(ARRAY_SUFFIX + ARRAY_PREFIX, PROPERTY_MARK)
						.replace(ARRAY_PREFIX, PROPERTY_MARK);
				if (keyStr.indexOf(PROPERTY_MARK) == 0) {
					keyStr = keyStr.replaceFirst(PROPERTY_MARK, "");
				}
				keys = keyStr.split(SEPARATOR);
			} else {
				if (!keyStr.equals(ARRAY_MARK)) {
					keyStr = keyStr.replace(ARRAY_MARK, "");
				}
				keys = new String[] { keyStr };
			}
		}
		return keys;
	}

	/**
	 * 设置哈希容器参数
	 * 
	 * @param binder
	 *            数据绑定器
	 * @param keys
	 *            名称集
	 * @param value
	 *            参数值
	 * @param map
	 *            哈希容器
	 */
	private void setMap(WebDataBinder binder, String[] keys, Object value, Map<String, Object> map) {
		if (ArrayUtils.isNotEmpty(keys)) {
			if (keys.length == 1) {
				map.put(keys[0], value);
			} else {
				if (StringUtil.isNumeric(keys[1])) {
					if (!map.containsKey(keys[0])) {
						map.put(keys[0], new ArrayList<Object>());
					}
					setList(binder, ArrayUtils.subarray(keys, 1, keys.length), value, binder.convertIfNecessary(map.get(keys[0]), List.class));
				} else {
					if (!map.containsKey(keys[0])) {
						map.put(keys[0], new HashMap<String, Object>());
					}
					setMap(binder, ArrayUtils.subarray(keys, 1, keys.length), value, binder.convertIfNecessary(map.get(keys[0]), Map.class));
				}
			}
		}
	}

	/**
	 * 设置列表容器参数
	 * 
	 * @param binder
	 *            数据绑定器
	 * @param keys
	 *            名称集
	 * @param value
	 *            参数值
	 * @param list
	 *            列表容器
	 */
	private void setList(WebDataBinder binder, String[] keys, Object value, List<Object> list) {
		int index = Integer.valueOf(keys[0]);
		if (ArrayUtils.isNotEmpty(keys)) {
			if (keys.length == 1) {
				list.add(index, value);
			} else {
				if (StringUtil.isNumeric(keys[1])) {
					if (list.size() <= index) {
						list.add(index, new ArrayList<Object>());
					}
					setList(binder, ArrayUtils.subarray(keys, 1, keys.length), value, binder.convertIfNecessary(list.get(index), List.class));
				} else {
					if (list.size() <= index) {
						list.add(index, new HashMap<String, Object>());
					}
					setMap(binder, ArrayUtils.subarray(keys, 1, keys.length), value, binder.convertIfNecessary(list.get(index), Map.class));
				}
			}
		}
	}

	/**
	 * 普通参数设置
	 * 
	 * @param binder
	 *            数据绑定器
	 * @param type
	 *            对象类型
	 * @param object
	 *            对象
	 * @param keys
	 *            名称集
	 * @param value
	 *            参数值
	 * @return 对象
	 */
	private Object setParameter(WebDataBinder binder, Type type, Object object, String[] keys, Object value) {
		try {
			if (ArrayUtils.isNotEmpty(keys)) {
				if (keys.length == 1) {
					if (keys[0].equals(ARRAY_MARK)) {
						object = setArrayParameter(binder, type, keys[0], value);
					} else {
						object = setParameterValue(binder, type, object, keys[0], value);
					}
				} else {
					object = setDeepParameter(binder, type, object, keys, value);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return object;
	}

	/**
	 * 数组参数设置
	 * 
	 * @param binder
	 *            数据绑定器
	 * @param type
	 *            对象类型
	 * @param key
	 *            参数名
	 * @param value
	 *            参数值
	 * @return 对象
	 */
	private Object setArrayParameter(WebDataBinder binder, Type type, String key, Object value) {
		Class<?> clazz = ReflectUtil.getClass(ReflectUtil.getPropertyType(type, key));
		Object[] array = (Object[]) value;
		for (int i = 0; i < array.length; i++) {
			binder.convertIfNecessary(array[i], clazz);
		}
		return array;
	}

	/**
	 * 设置参数值到对象中
	 * 
	 * @param binder
	 *            数据绑定器
	 * @param type
	 *            对象类型
	 * @param object
	 *            对象
	 * @param key
	 *            参数名
	 * @param value
	 *            参数值
	 * @return 对象
	 */
	private Object setParameterValue(WebDataBinder binder, Type type, Object object, String key, Object value) {
		Class<?> clazz = ReflectUtil.getClass(ReflectUtil.getPropertyType(type, key));
		if (clazz == null) {
			clazz = Object.class;
		}
		if (clazz.equals(Date.class) && value != null) {
			if (value instanceof String && StringUtils.isNotBlank(value.toString())) {
				String dateStr = value.toString();
				if (StringUtil.isNumeric(dateStr)) {
					value = new Date(Long.valueOf(dateStr));
				} else {
					if (!DateUtil.isStandardFormat(dateStr)) {
						value = DateUtil.parse(dateStr, DateUtil.getFormat(dateStr));
					}
				}
			} else {
				value = null;
			}
		}
		if (clazz.isPrimitive() && StringUtils.isBlank((String) value)) {
			return object;
		}
		return ReflectUtil.setPropertyValue(object, key, binder.convertIfNecessary(value, clazz));
	}

	/**
	 * 深度设置对象参数
	 * 
	 * @param binder
	 *            数据绑定器
	 * @param type
	 *            对象类型
	 * @param object
	 *            对象
	 * @param keys
	 *            名称集
	 * @param value
	 *            参数值
	 * @return 对象
	 * @throws Exception
	 *             异常
	 */
	private Object setDeepParameter(WebDataBinder binder, Type type, Object object, String[] keys, Object value) throws Exception {
		String key = keys[0];
		Object property = value;
		if (keys.length > 1) {
			Type propertyType = ReflectUtil.getPropertyType(type, key);
			property = ReflectUtil.getPropertyValue(object, key);
			if (property == null) {
				property = ReflectUtil.createObject(ReflectUtil.getClass(propertyType));
				object = setParameterValue(binder, type, object, key, property);
			}
			property = setDeepParameter(binder, propertyType, property, ArrayUtils.subarray(keys, 1, keys.length), value);
			if (ReflectUtil.getClass(type).isArray()) {
				object = setParameterValue(binder, type, object, key, property);
			}
		} else {
			object = setParameterValue(binder, type, object, key, property);
		}
		return object;
	}
	
}
