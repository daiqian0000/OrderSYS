package com.asion.business.handler.security.dataPermission;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.asion.business.handler.service.SystemService;
import com.asion.common.util.BaseUtil;
import com.asion.common.util.PageBroker;
import com.asion.common.util.ParamBroker;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 数据权限切面拦截器
 * 
 * @author chenboyang
 *
 */
@Aspect
@SuppressWarnings({ "unchecked", "deprecation" })
public class DataPermissionAspect {

	/**
	 * 机构权限
	 */
	public static final String ORG_RECURSIVE = "orgRecursive";

	/**
	 * 地区权限
	 */
	public static final String AREA_RECURSIVE = "areaRecursive";

	/**
	 * 系统服务接口
	 */
	@Autowired
	private SystemService systemService;

	/**
	 * 调用接口前验证
	 * 
	 * @param point
	 *            信息连接点
	 * @param dataPermission
	 *            数据权限信息
	 */
	@Before("@annotation(dataPermission)")
	public void before(JoinPoint point, DataPermission dataPermission) {
		if (ArrayUtils.isNotEmpty(point.getArgs()) && ArrayUtils.isNotEmpty(dataPermission.value())) {
			Map<String, Object> map = getMap(point.getArgs());
			if (map != null && systemService.currDataPermissions() != null) {
				DataPermissionVerify[] verifys = BaseUtil.arrayDerepeat(dataPermission.value());
				for (DataPermissionVerify verify : verifys) {
					String key = null;
					if (verify.equals(DataPermissionVerify.ORG)) {
						key = ORG_RECURSIVE;
					}
					if (verify.equals(DataPermissionVerify.AREA)) {
						key = AREA_RECURSIVE;
					}
					if (StringUtils.isNotBlank(key)) {
						map.put(key, systemService.currDataPermissions().get(key));
					}
				}
			}
		}
	}

	/**
	 * 获取参数容器
	 * 
	 * @param args
	 *            参数集
	 * @return 参数容器
	 */
	private Map<String, Object> getMap(Object[] args) {
		for (Object arg : args) {
			if (arg instanceof ParamBroker) {
				return ((ParamBroker<String, Object>) arg).getParam();
			} else if (arg instanceof PageBroker) {
				return ((PageBroker<?>) arg).getPage();
			} else if (arg instanceof Page) {
				return ((Page<?>) arg).getCondition();
			}
		}
		return null;
	}

}
