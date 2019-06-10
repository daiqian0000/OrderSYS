package com.asion.business.module.param.service;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.asion.business.module.param.mapper.ParamMapper;
import com.asion.business.module.param.model.Param;
import com.asion.common.base.AbstractService;
import com.asion.common.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 参数表 : 用于配置业务参数 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class ParamServiceImpl extends AbstractService<ParamMapper, Param> implements ParamService {

	@Override
	protected EntityWrapper<Param> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Param> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Param.PARAM_KEY, map.get("keyword").toString())
					.or().like(Param.PARAM_LABEL, map.get("keyword").toString());
			}
		}
		return wrapper;
	}
	
}
