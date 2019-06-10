package com.asion.business.module.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.asion.business.module.app.mapper.AppVersionMapper;
import com.asion.business.module.app.model.AppVersion;
import com.asion.business.module.attach.model.Attach;
import com.asion.business.module.attach.service.AttachService;
import com.asion.common.base.AbstractService;
import com.asion.common.exception.BusinessException;
import com.asion.common.util.StringUtil;
import com.asion.dynamic.base.service.MyBatisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 应用版本表 服务实现类
 * </p>
 *
 * @author chenboyang-123
 * @since 2018-03-21
 */
@Service
public class AppVersionServiceImpl extends AbstractService<AppVersionMapper, AppVersion> implements AppVersionService {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 数据访问通用DAO
	 */
	@Autowired(required = false)
	@Qualifier("myBatisServiceImpl")
	private MyBatisService myBatisService;

	/**
	 * 附件信息服务接口
	 */
	@Autowired
	private AttachService attachService;

	@Override
	protected EntityWrapper<AppVersion> queryWrapper(Map<String, Object> map) {
		EntityWrapper<AppVersion> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(AppVersion.VERSION, map.get("keyword").toString())
					.or().like(AppVersion.CONTENT, map.get("keyword").toString());
			}
		}
		wrapper.orderBy(AppVersion.ID, false);
		return wrapper;
	}

	@Override
	public boolean add(AppVersion appVersion, File file) {
		try {
			if (file != null) {
				return add(appVersion, FileUtils.openInputStream(file));
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public boolean add(AppVersion appVersion, InputStream input) {
		boolean result = false;
		appVersion.setPublishTime(new Date());
		if (myBatisService.isExists("LOC_APP_VERSION", AppVersion.VERSION, appVersion.getVersion())) {
			throw new BusinessException("版本号已存在！");
		}
		result = insert(appVersion);	
		if (result && input != null) {
			if (appVersion.getAttach() == null) {
				appVersion.setAttach(new Attach());
			}
			appVersion.getAttach().setDomainId("LOC_APP_VERSION");
			appVersion.getAttach().setTargetId(appVersion.getId().toString());
			appVersion.getAttach().setFieldId("apk");
			appVersion.getAttach().setInput(input);
			attachService.add(appVersion.getAttach());
		}
		return result;
	}

	@Override
	public boolean del(Serializable id) {
		boolean result = false;
		AppVersion appVersion = selectById(id);
		result = deleteById(id);
		if (result && appVersion != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("domainId", "LOC_APP_VERSION");
			map.put("targetId", appVersion.getId());
			map.put("fieldId", "apk");
			attachService.deleteByMap(map);
		}
		return result;
	}
	
}
