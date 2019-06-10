package com.asion.common.gis.service.gaode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.asion.common.exception.BusinessException;
import com.asion.common.util.JsonUtil;
import com.asion.common.util.SpringContextUtil;

/**
 * 高德WEB服务抽象类
 * 
 * @author chenboyang
 *
 */
public abstract class AbstractGaodeService {

	/**
	 * REST接口服务请求模板
	 */
	private RestTemplate restTemplate = null;

	/**
	 * HTTP请求头
	 */
	private static HttpHeaders httpHeaders = null;

	/**
	 * 获取REST接口服务请求模板
	 * 
	 * @return REST接口服务请求模板
	 */
	protected RestTemplate restTemplate() {
		if (restTemplate == null) {
			restTemplate = SpringContextUtil.getBean(RestTemplate.class);
		}
		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}
		return restTemplate;
	}

	/**
	 * 封装请求基础参数
	 * 
	 * @param gaodeParameter
	 *            请求参数对象
	 * @return 参数封装MAP
	 */
	protected MultiValueMap<String, String> baseParams(GaodeParameter gaodeParameter) {
		if (gaodeParameter == null) {
			throw new BusinessException("参数不为空！");
		}
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("key", gaodeParameter.getKey());
		if (StringUtils.isNotBlank(gaodeParameter.getExtensions())) {
			params.add("extensions", gaodeParameter.getExtensions());
		}
		if (StringUtils.isNotBlank(gaodeParameter.getSig())) {
			params.add("sig", gaodeParameter.getSig());
		}
		if (StringUtils.isNotBlank(gaodeParameter.getOutput())) {
			params.add("output", gaodeParameter.getOutput());
		}
		if (StringUtils.isNotBlank(gaodeParameter.getCallback())) {
			params.add("callback", gaodeParameter.getCallback());
		}
		return params;
	}

	/**
	 * 获取HTTP请求头
	 * 
	 * @return HTTP请求头
	 */
	private HttpHeaders httpHeaders() {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		}
		return httpHeaders;
	}

	/**
	 * 执行服务请求
	 * 
	 * @param url
	 *            服务地址
	 * @param clazz
	 *            返回值类型
	 * @param param
	 *            请求参数
	 * @param wrapper
	 *            参数封装器
	 * @return 返回对象
	 */
	protected <T extends GaodeResult, P extends GaodeParameter> T exchange(String url, Class<T> clazz, P param, ParamWrapper<P> wrapper) {
		if (StringUtils.isBlank(url)) {
			throw new BusinessException("服务地址不能为空！");
		}
		if (clazz == null) {
			throw new BusinessException("返回值类型不能为空！");
		}
		if (param == null) {
			throw new BusinessException("请求参数不能为空！");
		}
		if (wrapper == null) {
			throw new BusinessException("参数封装器不能为空！");
		}
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(wrapper.wrapper(param), httpHeaders());
		ResponseEntity<String> responseEntity = restTemplate().exchange(url, HttpMethod.POST, requestEntity, String.class);
		String body = responseEntity.getBody();
		body = filterStr(body);
		return JsonUtil.toObject(body, clazz);
	}

	/**
	 * 过滤异常字符
	 * 
	 * @param s
	 *            字符串
	 * @return 过滤后的字符串
	 */
	private String filterStr(String s) {
		String nullValue = "null";
		return s.replace("[[[[[[[[]]]]]]]]", nullValue)
				.replace("[[[[[[[]]]]]]]", nullValue)
				.replace("[[[[[[]]]]]]", nullValue)
				.replace("[[[[[]]]]]", nullValue)
				.replace("[[[[]]]]", nullValue)
				.replace("[[[]]]", nullValue)
				.replace("[[]]", nullValue)
				.replace("[]", nullValue);
	}

}
