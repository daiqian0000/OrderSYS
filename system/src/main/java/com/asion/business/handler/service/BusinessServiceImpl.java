package com.asion.business.handler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asion.business.module.area.mapper.AreaMapper;
import com.asion.business.module.org.mapper.OrgMapper;
import com.asion.business.tree.service.TreeService;
import com.asion.common.util.JudgeUtil;
import com.asion.common.util.StringUtil;

/**
 * 业务服务接口实现类
 * 
 * @author chenboyang
 *
 */
@Service
public class BusinessServiceImpl implements BusinessService {

	/**
	 * 机构管理Mapper接口 
	 */
	@Autowired
	protected OrgMapper orgMapper;

	/**
	 * 地区Mapper接口
	 */
	@Autowired
	protected AreaMapper areaMapper;
	
	@Override
	public String orgRecursive(String orgId) {
		String orgRecursive = "";
		if (StringUtil.isNotBlank(orgId)) {
			if (JudgeUtil.isOneEqual(orgId, TreeService.TOP_CODE, TreeService.TOP_LEVEL)) {
				orgRecursive = null;
			} else {
				orgRecursive = orgMapper.downRecursive(orgId);
			}
		}
		return orgRecursive;
	}

	@Override
	public String areaRecursive(Integer areaId) {
		String areaRecursive = "";
		if (areaId != null) {
			if (JudgeUtil.isOneEqual(areaId.toString(), TreeService.TOP_CODE, TreeService.TOP_LEVEL)) {
				areaRecursive = null;
			} else {
				areaRecursive = areaMapper.downRecursive(areaId);
			}
		}
		return areaRecursive;
	}
	
}
