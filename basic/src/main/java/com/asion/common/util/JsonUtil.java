package com.asion.common.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class JsonUtil {

	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = Logger.getLogger(JsonUtil.class);

	/**
	 * JSON操作对象
	 */
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 对象转json字符串
	 * 
	 * @param obj
	 *            对象
	 * @return json字符串
	 */
	public static String toJson(Object obj) {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * json字符串转对象
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

}
