package com.asion.business.module.dict.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.asion.business.module.dict.model.Dict;
import com.asion.common.base.BaseMapper;

/**
 * <p>
 * 字典表 : 用于存放各业务的类型字典 Mapper 接口
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * 获取当前最大排序值
	 * 
	 * @param parentCode
	 *            上级代码
	 * @return 排序值
	 */
	@Select("SELECT MAX(SORT) FROM LOC_DICT WHERE PARENT_CODE = #{parentCode}")
	Integer currSort(@Param("parentCode") String parentCode);

}
