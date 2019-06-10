package com.asion.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.CollectionFactory;

/**
 * JAVA反射帮助类
 * 
 * @author chenboyang
 * 
 */
@SuppressWarnings("unchecked")
public abstract class ReflectUtil {

	/**
	 * 日志记录
	 */
	private static Logger logger = Logger.getLogger(ReflectUtil.class);
	
	/**
	 * 获取类的字段集
	 * 
	 * @param clazz
	 *            类
	 * @return 字段集
	 */
	public static List<Field> getFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return fields;
	}

	/**
	 * 获取类的字段
	 * 
	 * @param clazz
	 *            类
	 * @param fieldName
	 *            字段名
	 * @return 字段
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e) {
			}
		}
		return null;
	}

	/**
	 * 获取对象的字段值
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @return 字段值
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		try {
			Field field = getField(obj.getClass(), fieldName);
			if (field != null) {
				field.setAccessible(true);
				return field.get(obj);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
		}
		return null;
	}

	/**
	 * 设置对象的字段值
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 */
	public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
		try {
			Field field = getField(obj.getClass(), fieldName);
			if (field != null) {
				field.setAccessible(true);
				field.set(obj, fieldValue);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
		}
	}

	/**
	 * 获取类的方法
	 * 
	 * @param clazz
	 *            类
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            参数类型集
	 * @return 方法
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				return clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException | SecurityException e) {
			}
		}
		return null;
	}

	/**
	 * 获取类的方法
	 * 
	 * @param clazz
	 *            类
	 * @param methodName
	 *            方法名
	 * @return 方法
	 */
	public static Method getMethod(Class<?> clazz, String methodName) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				return getMethod(clazz.getDeclaredMethods(), methodName);
			} catch (SecurityException e) {
			}
		}
		return null;
	}

	/**
	 * 调用对象的方法
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数集
	 * @return 方法调用的返回结果
	 */
	public static Object invokeMethod(Object obj, String methodName, Object... args) {
		try {
			Method method = getMethod(obj.getClass(), methodName, ParameterClass(args));
			if (method != null) {
				method.setAccessible(true);
				return method.invoke(obj, args);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
		return null;
	}

	/**
	 * 参数类型封装
	 * 
	 * @param args
	 *            参数
	 * @return 参数类集
	 */
	public static Class<?>[] ParameterClass(Object... args) {
		if (args != null) {
			Class<?>[] parameterClass = new Class<?>[args.length];
			for (int i = 0; i < parameterClass.length; i++) {
				parameterClass[i] = args[i].getClass();
			}
			return parameterClass;
		}
		return null;
	}

	/**
	 * 判断是否为包装类
	 * 
	 * @param clazz
	 *            类
	 * @return 否为包装类
	 */
	public static boolean isWrapClass(Class<?> clazz) {
		try {
			Object type = clazz.getField("TYPE").get(null);
			if (type instanceof Class) {
				return ((Class<?>) type).isPrimitive();
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
		}
		return false;
	}

	/**
	 * 根据方法名获取方法
	 * 
	 * @param methods
	 *            方法集
	 * @param name
	 *            方法名
	 * @return 方法
	 */
	public static Method getMethod(Method[] methods, String name) {
		if (methods.length > 0 && StringUtils.isNotBlank(name)) {
			for (Method method : methods) {
				if (method.getName().equals(name)) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 类型中是否包含该方法
	 * 
	 * @param clazz
	 *            类型
	 * @param methodName
	 *            方法名
	 * @return 是否包含
	 */
	public static boolean hasMethod(Class<?> clazz, String methodName) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				for (Method method : clazz.getMethods()) {
					if (method.getName().equals(methodName)) {
						return true;
					}
				}
			} catch (SecurityException e) {
			}
		}
		return false;
	}

	/**
	 * 类型中是否包含该方法
	 * 
	 * @param clazz
	 *            类型
	 * @param method
	 *            方法
	 * @return 是否包含
	 */
	public static boolean hasMethod(Class<?> clazz, Method method) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				for (Method method1 : clazz.getMethods()) {
					if (method1.equals(method)) {
						return true;
					}
				}
			} catch (SecurityException e) {
			}
		}
		return false;
	}

	/**
	 * 类型中是否包含该字段
	 * 
	 * @param clazz
	 *            类型
	 * @param fieldName
	 *            字段名
	 * @return 是否包含
	 */
	public static boolean hasField(Class<?> clazz, String fieldName) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				for (Field field : clazz.getDeclaredFields()) {
					if (field.getName().equals(fieldName)) {
						return true;
					}
				}
			} catch (SecurityException e) {
			}
		}
		return false;
	}

	/**
	 * 类型中是否包含该字段
	 * 
	 * @param clazz
	 *            类型
	 * @param field
	 *            字段
	 * @return 是否包含
	 */
	public static boolean hasField(Class<?> clazz, Field field) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				for (Field field1 : clazz.getDeclaredFields()) {
					if (field1.equals(field)) {
						return true;
					}
				}
			} catch (SecurityException e) {
			}
		}
		return false;
	}

	/**
	 * 调度对象的的设置方法
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数
	 * @return 设置结果
	 */
	public static Object invokeSetMethod(Object obj, String methodName, Object args) {
		try {
			Method method = getMethod(obj.getClass(), methodName);
			if (method != null) {
				method.setAccessible(true);
				args = cast(method.getParameterTypes()[0], args);
				return method.invoke(obj, args);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}
		return null;
	}

	/**
	 * 值类型转换
	 * 
	 * @param type
	 *            类型
	 * @param value
	 *            值
	 * @return 转换结果
	 */
	public static Object cast(Class<?> type, Object value) {
		if (type != null && value != null) {
			if (type.equals(byte.class)) {
				value = Byte.valueOf(value.toString());
			} else if (type.equals(Byte.class)) {
				value = Byte.valueOf(value.toString());
			} else if (type.equals(short.class)) {
				value = Short.valueOf(value.toString());
			} else if (type.equals(Short.class)) {
				value = Short.valueOf(value.toString());
			} else if (type.equals(int.class)) {
				value = Integer.valueOf(value.toString());
			} else if (type.equals(Integer.class)) {
				value = Integer.valueOf(value.toString());
			} else if (type.equals(long.class)) {
				value = Long.valueOf(value.toString());
			} else if (type.equals(Long.class)) {
				value = Long.valueOf(value.toString());
			} else if (type.equals(float.class)) {
				value = Float.valueOf(value.toString());
			} else if (type.equals(Float.class)) {
				value = Float.valueOf(value.toString());
			} else if (type.equals(double.class)) {
				value = Double.valueOf(value.toString());
			} else if (type.equals(Double.class)) {
				value = Double.valueOf(value.toString());
			} else if (type.equals(char.class)) {
				value = value.toString().charAt(0);
			} else if (type.equals(Character.class)) {
				value = value.toString().charAt(0);
			} else if (type.equals(boolean.class)) {
				value = Boolean.valueOf(value.toString());
			} else if (type.equals(Boolean.class)) {
				value = Boolean.valueOf(value.toString());
			} else {
				value = type.cast(value);
			}
		}
		return value;
	}

	/**
	 * 根据类型创建对象
	 * 
	 * @param type
	 *            对象类型
	 * @return 对象
	 */
	public static Object createObject(Class<?> type) {
		try {
			if (type.isArray()) {
				return createArray(type);
			} else if (Collection.class.isAssignableFrom(type)) {
				return CollectionFactory.createCollection(type, 16);
			} else if (Map.class.isAssignableFrom(type)) {
				return CollectionFactory.createMap(type, 16);
			}
			return type.newInstance();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 递归创建多维数组
	 * 
	 * @param type
	 *            数组类型
	 * @return 数组实例
	 */
	public static Object createArray(Class<?> type) {
		Class<?> componentType = type.getComponentType();
		if (componentType.isArray()) {
			Object array = Array.newInstance(componentType, 1);
			Array.set(array, 0, createArray(componentType));
			return array;
		}
		return Array.newInstance(componentType, 0);
	}

	/**
	 * 获取原始类型
	 * 
	 * @param type
	 *            对象类型
	 * @return 类型
	 */
	public static Class<?> getClass(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return getClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			Class<?> componentClass = getClass(componentType);
			if (componentClass != null) {
				return Array.newInstance(componentClass, 0).getClass();
			}
		}
		return Object.class;
	}

	/**
	 * 获取数组元素
	 * 
	 * @param array
	 *            数组
	 * @param key
	 *            下标
	 * @return 元素
	 */
	public static Object getArrayElement(Object[] array, Object key) {
		int index = Integer.valueOf(key.toString());
		if (array.length > index) {
			return array[index];
		}
		return null;
	}

	/**
	 * 获取属性类型
	 * 
	 * @param type
	 *            对象类型
	 * @param key
	 *            参数名
	 * @return 类型
	 */
	public static Type getPropertyType(Type type, Object key) {
		Class<?> clazz = getClass(type);
		if (clazz.isArray()) {
			return clazz.getComponentType();
		} else if (Map.class.isAssignableFrom(clazz)) {
			if (type instanceof ParameterizedType) {
				return ((ParameterizedType) type).getActualTypeArguments()[1];
			}
		} else if (Collection.class.isAssignableFrom(clazz)) {
			if (type instanceof ParameterizedType) {
				return ((ParameterizedType) type).getActualTypeArguments()[0];
			}
		}
		if (hasField(clazz, (String) key)) {
			return BeanUtils.getPropertyDescriptor(clazz, (String) key).getReadMethod().getGenericReturnType();
		}
		return Object.class;
	}

	/**
	 * 获取属性值
	 * 
	 * @param object
	 *            对象
	 * @param key
	 *            参数名
	 * @return 值对象
	 */
	public static Object getPropertyValue(Object object, Object key) {
		try {
			if (object.getClass().isArray()) {
				return getArrayElement((Object[]) object, key);
			} else if (object instanceof Map) {
				return ((Map<?, ?>) object).get(key);
			} else if (object instanceof Collection) {
				return getArrayElement(((Collection<?>) object).toArray(), key);
			} else if (hasField(object.getClass(), (String) key)) {
				return BeanUtils.getPropertyDescriptor(object.getClass(), (String) key).getReadMethod().invoke(object);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 设置对象属性值
	 * 
	 * @param object
	 *            对象
	 * @param key
	 *            参数名
	 * @param value
	 *            参数值
	 * @return 对象
	 */
	public static Object setPropertyValue(Object object, Object key, Object value) {
		try {
			if (object.getClass().isArray()) {
				Object[] array = (Object[]) object;
				int index = Integer.valueOf(key.toString());
				if (array.length > index) {
					array[index] = value;
				} else {
					array = BaseUtil.setArray(array, index, value);
				}
				object = array;
			} else if (object instanceof Map) {
				((Map<Object, Object>) object).put(key, value);
			} else if (object instanceof List) {
				BaseUtil.setList((List<Object>) object, Integer.valueOf(key.toString()), value);
			} else if (hasField(object.getClass(), (String) key)) {
				Method method = BeanUtils.getPropertyDescriptor(object.getClass(), (String) key).getWriteMethod();
				if (method != null) {
					method.invoke(object, cast(method.getParameterTypes()[0], value));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return object;
	}

}
