package com.asion.common.gis.service.gaode.geocode;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

import com.asion.common.gis.service.gaode.AbstractGaodeService;
import com.asion.common.gis.service.gaode.GaodeConfig;
import com.asion.common.gis.service.gaode.GaodeParameter;
import com.asion.common.gis.service.gaode.ParamWrapper;

/**
 * 高德地理编码服务接口
 * 
 * @author chenboyang
 *
 */
public class GeocodeService extends AbstractGaodeService {

	/**
	 * 全局单例
	 */
	private static GeocodeService geocodeService = null;

	/**
	 * 地理编码服务地址根路径
	 */
	private static final String GEOCODE_URL = GaodeConfig.SERVICE_URL + "/geocode";

	/**
	 * 逆向地理编码服务地址
	 */
	private static final String REGEO_URL = GEOCODE_URL + "/regeo";

	/**
	 * 封装逆向地理编码请求参数封装器
	 */
	private ParamWrapper<RegeoParameter> regeoParamWrapper = null;

	/**
	 * 获取地理编码服务实例
	 * 
	 * @return 服务实例
	 */
	public static GeocodeService getInstance() {
		if (geocodeService == null) {
			geocodeService = new GeocodeService();
		}
		return geocodeService;
	}

	/**
	 * 逆向地理编码请求服务
	 * 
	 * @param regeoParameter
	 *            高德地理编码服务请求参数
	 * @return 逆向地理编码响应结果
	 */
	public RegeoResult regeo(RegeoParameter regeoParameter) {
		return exchange(REGEO_URL, RegeoResult.class, regeoParameter, regeoParamWrapper());
	}

	/**
	 * 获取封装逆向地理编码请求参数封装器
	 * 
	 * @return 封装逆向地理编码请求参数封装器
	 */
	private ParamWrapper<RegeoParameter> regeoParamWrapper() {
		if (regeoParamWrapper == null) {
			regeoParamWrapper = (param) -> {
				MultiValueMap<String, String> paramMap = baseParams(param);
				if (CollectionUtils.isNotEmpty(param.getLocation())) {
					String location = StringUtils.join(param.getLocation().stream()
							.map((point) -> StringUtils.join(point.getCoords(), ',')).toArray(), '|');
					paramMap.add("location", location);
				}
				if (param.getRadius() > 0) {
					paramMap.add("radius", String.valueOf(param.getRadius()));
				}
				if (param.isBatch()) {
					paramMap.add("batch", String.valueOf(param.isBatch()));
				}
				if (GaodeParameter.EXTENSIONS_ALL.equals(param.getExtensions())) {
					if (CollectionUtils.isNotEmpty(param.getPoitype())) {
						String poitype = StringUtils.join(param.getPoitype(), '|');
						paramMap.add("poitype", poitype);
					}
					if (param.getRoadlevel() > 0) {
						paramMap.add("roadlevel", String.valueOf(param.getRoadlevel()));
					}
					if (param.getHomeorcorp() > 0) {
						paramMap.add("homeorcorp", String.valueOf(param.getHomeorcorp()));
					}
				}
				return paramMap;
			};
		}
		return regeoParamWrapper;
	}

}
