package com.asion.dynamic.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Mybatis数据访问通用DAO
 * 
 * @author chenboyang
 */
public interface Mapper {

	// ==================== 通用动态SQL语句执行方法 ====================
	
	/**
	 * 查询
	 * 
	 * @param obj
	 *            语句
	 * @return 结果
	 */
	List<Map<Object, Object>> select(Object obj);

	/**
	 * 插入
	 * 
	 * @param obj
	 *            语句
	 * @return 记录数
	 */
	int insert(Object obj);

	/**
	 * 更新
	 * 
	 * @param obj
	 *            语句
	 * @return 记录数
	 */
	int update(Object obj);

	/**
	 * 删除
	 * 
	 * @param obj
	 *            语句
	 * @return 记录数
	 */
	int delete(Object obj);
	
	/**
	 * 获取当前序列值
	 * 
	 * @param tableName
	 *            表名
	 * @param fieldName
	 *            字段名
	 * @return 序列值
	 */
	int currSeq(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

	/**
	 * 字段值是否已经存在
	 * 
	 * @param tableName
	 *            表名
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            字段值
	 * @return 是否存在
	 */
	boolean isExists(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("value") Object value);
	
}
