package com.asion.business.module.log.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asion.business.handler.constants.SystemConstants;
import com.asion.business.module.dict.service.DictService;
import com.asion.business.module.log.mapper.LogMapper;
import com.asion.business.module.log.model.Log;
import com.asion.business.sys.service.SysService;
import com.asion.common.base.AbstractService;
import com.asion.common.util.ExceptionUtil;
import com.asion.common.util.ExecutorUtil;
import com.asion.common.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class LogServiceImpl extends AbstractService<LogMapper, Log> implements LogService {

	/**
	 * 系统服务接口
	 */
	@Autowired
	protected SysService sysService;
	/**
	 * 字典服务接口
	 */
	@Autowired
	protected DictService dictService;

	@Override
	protected EntityWrapper<Log> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Log> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			wrapper.eq(StringUtil.isNotBlank(map.get("sysId")), Log.SYS_ID, map.get("sysId"));
			wrapper.ge(StringUtil.isNotBlank(map.get("startTime")), Log.RECORD_TIME, map.get("startTime"));
			wrapper.le(StringUtil.isNotBlank(map.get("endTime")), Log.RECORD_TIME, map.get("endTime"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Log.OPERATE_INFO, map.get("keyword").toString())
					.or().like(Log.USER_ID, map.get("keyword").toString());
			}
		}
		wrapper.orderBy(Log.RECORD_TIME, false);
		return wrapper;
	}

	@Override
	protected List<Log> valueQueryResult(List<Log> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(new Consumer<Log>() {
				public void accept(Log log) {
					log.setLogTypeName(dictService.dictName(SystemConstants.DICT_LOG_TYPE, log.getLogType()));
					log.setSysName(dictService.dictName(SystemConstants.DICT_SYS_FLAG, log.getSysId()));
				}
			});
		}
		return list;
	}
	
	/**
	 * 系统业务操作出现异常保存日志执行方法
	 * 
	 * @param operateInfo
	 *            操作信息
	 * @param throwable
	 *            异常
	 */
	public void saveLog(String operateInfo, Throwable throwable) {
		Log log = new Log();
		log.setOperateInfo(operateInfo);
		setExInfo(log, throwable);
		saveLog(log);
	}

	/**
	 * 业务操作保存日志执行方法
	 * 
	 * @param operateInfo
	 *            操作信息
	 */
	public void saveLog(String operateInfo) {
		Log log = new Log();
		log.setOperateInfo(operateInfo);
		saveLog(log);
	}

	/**
	 * 保存日志
	 * 
	 * @param log
	 *            日志信息
	 */
	public void saveLog(Log log) {
		if (log != null) {
			if (StringUtils.isBlank(log.getLogType())) {
				if (StringUtils.isNotBlank(log.getException())) {
					log.setLogType(LogService.LOG_TYPE_EXCEPTION);
				} else {
					log.setLogType(LogService.LOG_TYPE_OPREATION);
				}
			}
			if (StringUtils.isBlank(log.getSysId())) {
				log.setSysId(sysService.sysProperties().getName());
			}
			if (log.getRecordTime() == null) {
				log.setRecordTime(new Date());
			}
			ExecutorUtil.execute(() -> add(log));
		}
	}

	/**
	 * 设置日志异常信息
	 * 
	 * @param log
	 *            日志信息
	 * @param throwable
	 *            异常
	 */
	protected void setExInfo(Log log, Throwable throwable) {
		if (log != null && throwable != null) {
			String exInfo = ExceptionUtil.getStackTraceAsString(throwable);
			if (exInfo.length() > 1000) {
				exInfo = exInfo.substring(0, 999);
			}
			log.setException(exInfo);
		}
	}

}
