package com.asion.common.gis.service.gaode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.asion.common.exception.BusinessException;

/**
 * 高德WEB服务请求参数封装接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务参数类型
 */
@FunctionalInterface
public interface ParamWrapper<T extends GaodeParameter> {

	/**
	 * 封装请求参数
	 * 
	 * @param t
	 *            请求参数对象
	 * @return 参数封装MAP
	 */
	MultiValueMap<String, String> wrapper(T t);

	/**
	 * 封装请求基础参数
	 * 
	 * @param t
	 *            请求参数对象
	 * @return 参数封装MAP
	 */
	default MultiValueMap<String, String> baseParams(T t) {
		if (t == null) {
			throw new BusinessException("参数不为空！");
		}
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("key", t.getKey());
		if (StringUtils.isNotBlank(t.getExtensions())) {
			params.add("extensions", t.getExtensions());
		}
		if (StringUtils.isNotBlank(t.getSig())) {
			params.add("sig", t.getSig());
		}
		if (StringUtils.isNotBlank(t.getOutput())) {
			params.add("output", t.getOutput());
		}
		if (StringUtils.isNotBlank(t.getCallback())) {
			params.add("callback", t.getCallback());
		}
		return params;
	}

}
