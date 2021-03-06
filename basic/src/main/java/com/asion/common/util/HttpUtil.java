package com.asion.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * HTTP请求辅助类
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings("unused")
public abstract class HttpUtil {
	
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);

	/**
	 * HTTP连接池
	 */
	private static PoolingHttpClientConnectionManager pool;
	
	/**
	 * 连接池最大连接数
	 */
	private static final int MAX_TOTAL = 50;
	
	/**
	 * 连接池默认路由连接总数
	 */
	private static final int DEFAULT_MAX_PER_ROUTE = 5;
	
	/**
	 * 字符集编码
	 */
	private static final String CHARSET = "UTF-8";

	/**
	 * 初始化数据
	 */
	static {
		if (pool == null) {
			pool = new PoolingHttpClientConnectionManager();
			pool.setMaxTotal(MAX_TOTAL);
			pool.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return HttpClient
	 */
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.custom().setConnectionManager(pool).build();
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 *            地址
	 * @return 结果
	 */
	public static String get(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            参数
	 * @return 结果
	 */
	public static String get(String url, Map<Object, Object> params) {
		String result = null;
		try {
			URIBuilder ub = new URIBuilder();
			ub.setPath(url);
			List<NameValuePair> pairs = covertParamsToNVPS(params);
			if (CollectionUtils.isNotEmpty(pairs)) {
				ub.setParameters(pairs);
			}
			HttpGet httpGet = new HttpGet(ub.build());
			result = getResult(httpGet);
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 *            地址
	 * @param headers
	 *            请求头
	 * @param params
	 *            参数
	 * @return 结果
	 */
	public static String get(String url, Map<Object, Object> headers, Map<Object, Object> params) {
		String result = null;
		try {
			URIBuilder ub = new URIBuilder();
			ub.setPath(url);
			List<NameValuePair> pairs = covertParamsToNVPS(params);
			if (CollectionUtils.isNotEmpty(pairs)) {
				ub.setParameters(pairs);
			}
			HttpGet httpGet = new HttpGet(ub.build());
			if (MapUtils.isNotEmpty(headers)) {
				for (Object key : headers.keySet()) {
					httpGet.addHeader(String.valueOf(key), String.valueOf(headers.get(key)));
				}
			}
			result = getResult(httpGet);
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            地址
	 * @return 结果
	 */
	public static String post(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            参数
	 * @return 结果
	 */
	public static String post(String url, Map<Object, Object> params) {
		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> pairs = covertParamsToNVPS(params);
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			result = getResult(httpPost);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            地址
	 * @param headers
	 *            请求头
	 * @param params
	 *            参数
	 * @return 结果
	 */
	public static String post(String url, Map<Object, Object> headers, Map<Object, Object> params) {
		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if (MapUtils.isNotEmpty(headers)) {
				for (Object key : headers.keySet()) {
					httpPost.addHeader(String.valueOf(key), String.valueOf(headers.get(key)));
				}
			}
			List<NameValuePair> pairs = covertParamsToNVPS(params);
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			result = getResult(httpPost);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 将参数转换为NVP
	 * 
	 * @param params
	 *            参数
	 * @return NVP集合
	 */
	private static List<NameValuePair> covertParamsToNVPS(Map<Object, Object> params) {
		List<NameValuePair> pairs = null;
		if (MapUtils.isNotEmpty(params)) {
			pairs = new ArrayList<NameValuePair>();
			for (Object key : params.keySet()) {
				pairs.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(params.get(key))));
			}
		}
		return pairs;
	}

	/**
	 * 处理HTTP请求
	 * 
	 * @param request
	 *            请求对象
	 * @return 响应结果
	 */
	private static String getResult(HttpRequestBase request) {
		String result = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();
//			CloseableHttpClient httpClient = getHttpClient();
			response = httpClient.execute(request);
//			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
// 				long length = entity.getContentLength();// -1 表示长度未知
				result = EntityUtils.toString(entity);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}
		return result;
	}

}
