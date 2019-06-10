package com.asion.business.module.area.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.asion.business.module.area.model.Area;
import com.asion.common.base.BaseMapper;

/**
 * <p>
 * 地区表 Mapper 接口
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface AreaMapper extends BaseMapper<Area> {

	/**
	 * 向下递归查询地区代码
	 * 
	 * @param areaId
	 *            地区代码
	 * @return 地区代码字符串
	 */
	@Select("SELECT AREA_DOWN_RECURSIVE(#{areaId})")
	String downRecursive(@Param("areaId") int areaId);

	/**
	 * 向上递归查询地区代码
	 * 
	 * @param areaId
	 *            地区代码
	 * @return 地区代码字符串
	 */
	@Select("SELECT AREA_UP_RECURSIVE(#{areaId})")
	String upRecursive(@Param("areaId") int areaId);

}
