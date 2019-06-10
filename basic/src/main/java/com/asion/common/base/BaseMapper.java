package com.asion.common.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

/**
 * MyBatis基础持久化接口
 * 
 * @author chenboyang
 * 
 */
@SuppressWarnings("unchecked")
public interface BaseMapper<T> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {

	/**
	 * 获取日志记录器
	 * 
	 * @return 记录器
	 */
	default Logger logger() {
		return Logger.getLogger(getClass());
	}

	/**
	 * 获取当前模型类型
	 * 
	 * @return 类型
	 */
	default Class<T> currentModelClass() {
		Type[] types = getClass().getGenericInterfaces();
		if (ArrayUtils.isNotEmpty(types)) {
			for (int i = 0; i < types.length; i++) {
				Class<?> clazz = (Class<?>) types[i];
				if (BaseMapper.class.isAssignableFrom(clazz)) {
					return currentModelClass(clazz.getGenericInterfaces());
				}
			}
		}
		return null;
	}

	/**
	 * 根据Mapper类型集获取当前模型类型
	 * 
	 * @param types
	 *            Mapper类型集
	 * @return 类型
	 */
	default Class<T> currentModelClass(Type[] types) {
		if (ArrayUtils.isNotEmpty(types)) {
			for (int i = 0; i < types.length; i++) {
				Type type = types[i];
				if (type instanceof ParameterizedType) {
					ParameterizedType pt = ((ParameterizedType) type);
					Type[] params = pt.getActualTypeArguments();
					if (ArrayUtils.isNotEmpty(params)) {
						for (int j = 0; j < params.length; j++) {
							Class<?> paramClass = (Class<?>) params[j];
							if (Model.class.isAssignableFrom(paramClass)) {
								return (Class<T>) paramClass;
							}
						}
					}
					type = pt.getRawType();
				}
				if (type instanceof Class) {
					Class<T> clazz = currentModelClass(((Class<?>) type).getGenericInterfaces());
					if (clazz != null && Model.class.isAssignableFrom(clazz)) {
						return clazz;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取批量处理SQL会话
	 * 
	 * @return SQL会话
	 */
	default SqlSession sqlSessionBatch() {
		return SqlHelper.sqlSessionBatch(currentModelClass());
	}

	/**
	 * 根据SQL类型获取SQL语句
	 * 
	 * @param sqlMethod
	 *            SQL类型
	 * @return SQL语句
	 */
	default String sqlStatement(SqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}

	/**
	 * 批量新增实例列表
	 * 
	 * @param list
	 *            实例列表
	 * @return 操作记录数
	 */
	default int insertBatch(List<T> list) {
		return insertBatch(list, 100);
	}

	/**
	 * 批量新增实例列表
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @return 操作记录数
	 */
	default int insertBatch(List<T> list, int batchSize) {
		int num = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			try {
				SqlSession batchSqlSession = sqlSessionBatch();
				int size = list.size();
				String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
				for (int i = 0; i < size; i++) {
					batchSqlSession.insert(sqlStatement, list.get(i));
					if (i >= 1 && i % batchSize == 0) {
						num += batchSqlSession.flushStatements().size();
					}
				}
				num += batchSqlSession.flushStatements().size();
			} catch (Throwable e) {
				logger().error("执行批量新增操作出错！", e);
			}
		}
		return num;
	}

	/**
	 * 新增或更新实例
	 * 
	 * @param entity
	 *            实例
	 * @return 操作记录数
	 */
	default int insertOrUpdate(T entity) {
		int num = 0;
		if (entity != null) {
			Class<?> cls = entity.getClass();
			TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
			if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
				Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
				if (!StringUtils.checkValNull(idVal)) {
					num = updateById(entity);
				}
				if (num <= 0) {
					num = insert(entity);
				}
			}
		}
		return num;
	}

	/**
	 * 新增或者更新实例，全字段操作处理
	 * 
	 * @param entity
	 *            实例
	 * @return 操作记录数
	 */
	default int insertOrUpdateAllColumn(T entity) {
		int num = 0;
		if (entity != null) {
			Class<?> cls = entity.getClass();
			TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
			if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
				Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
				if (!StringUtils.checkValNull(idVal)) {
					num = updateAllColumnById(entity);
				}
				if (num <= 0) {
					num = insertAllColumn(entity);
				}
			}
		}
		return num;
	}

	/**
	 * 批量新增或更新实例列表
	 * 
	 * @param list
	 *            实例列表
	 * @return 操作记录数
	 */
	default int insertOrUpdateBatch(List<T> list) {
		return insertOrUpdateBatch(list, 100);
	}

	/**
	 * 批量新增或更新实例列表
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @return 操作记录数
	 */
	default int insertOrUpdateBatch(List<T> list, int batchSize) {
		return insertOrUpdateBatch(list, batchSize, true);
	}

	/**
	 * 批量新增或更新实例列表，全字段操作处理
	 * 
	 * @param list
	 *            实例列表
	 * @return 操作记录数
	 */
	default int insertOrUpdateAllColumnBatch(List<T> list) {
		return insertOrUpdateBatch(list, 100, false);
	}

	/**
	 * 批量新增或更新实例列表，全字段操作处理
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @return 操作记录数
	 */
	default int insertOrUpdateAllColumnBatch(List<T> list, int batchSize) {
		return insertOrUpdateBatch(list, batchSize, false);
	}

	/**
	 * 批量新增或更新实例列表
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @param selective
	 *            是否滤掉空字段
	 * @return 操作记录数
	 */
	default int insertOrUpdateBatch(List<T> list, int batchSize, boolean selective) {
		int num = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			try {
				SqlSession batchSqlSession = sqlSessionBatch();
				int size = list.size();
				for (int i = 0; i < size; i++) {
					if (selective) {
						num += insertOrUpdate(list.get(i));
					} else {
						num += insertOrUpdateAllColumn(list.get(i));
					}
					if (i >= 1 && i % batchSize == 0) {
						batchSqlSession.flushStatements();
					}
				}
				batchSqlSession.flushStatements();
			} catch (Throwable e) {
				logger().error("执行批量新增修改操作出错！", e);
			}
		}
		return num;
	}

	/**
	 * 批量更新实例列表，根据实例主键操作
	 * 
	 * @param list
	 *            实例列表
	 * @return 操作记录数
	 */
	default int updateBatchById(List<T> list) {
		return updateBatchById(list, 100);
	}

	/**
	 * 批量更新实例列表，根据实例主键操作
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @return 操作记录数
	 */
	default int updateBatchById(List<T> list, int batchSize) {
		return updateBatchById(list, batchSize, true);
	}

	/**
	 * 批量更新实例列表，全字段操作处理，根据实例主键操作
	 * 
	 * @param list
	 *            实例列表
	 * @return 操作记录数
	 */
	default int updateAllColumnBatchById(List<T> list) {
		return updateAllColumnBatchById(list, 100);
	}

	/**
	 * 批量更新实例列表，全字段操作处理，根据实例主键操作
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @return 操作记录数
	 */
	default int updateAllColumnBatchById(List<T> list, int batchSize) {
		return updateBatchById(list, batchSize, false);
	}

	/**
	 * 批量更新实例列表，根据实例主键操作
	 * 
	 * @param list
	 *            实例列表
	 * @param batchSize
	 *            批次数量
	 * @param selective
	 *            是否滤掉空字段
	 * @return 操作记录数
	 */
	default int updateBatchById(List<T> list, int batchSize, boolean selective) {
		int num = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			try {
				SqlSession batchSqlSession = sqlSessionBatch();
				int size = list.size();
				SqlMethod sqlMethod = selective ? SqlMethod.UPDATE_BY_ID : SqlMethod.UPDATE_ALL_COLUMN_BY_ID;
				String sqlStatement = sqlStatement(sqlMethod);
				for (int i = 0; i < size; i++) {
					MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
					param.put("et", list.get(i));
					batchSqlSession.update(sqlStatement, param);
					if (i >= 1 && i % batchSize == 0) {
						num += batchSqlSession.flushStatements().size();
					}
				}
				num += batchSqlSession.flushStatements().size();
			} catch (Throwable e) {
				logger().error("执行批量更新操作出错！", e);
			}
		}
		return num;
	}

	/**
	 * 根据参数查询实例
	 * 
	 * @param wrapper
	 *            参数封装器
	 * @return 实例
	 */
	default T selectOne(Wrapper<T> wrapper) {
		return SqlHelper.getObject(selectList(wrapper));
	}

	/**
	 * 根据参数查询MAP
	 * 
	 * @param wrapper
	 *            参数封装器
	 * @return MAP
	 */
	default Map<String, Object> selectMap(Wrapper<T> wrapper) {
		return SqlHelper.getObject(selectMaps(wrapper));
	}

	/**
	 * 根据参数查询Obj
	 * 
	 * @param wrapper
	 *            参数封装器
	 * @return Obj
	 */
	default Object selectObj(Wrapper<T> wrapper) {
		return SqlHelper.getObject(selectObjs(wrapper));
	}

	/**
	 * 根据分页条件查询分页实例信息
	 * 
	 * @param page
	 *            分页条件
	 * @return 分页实例信息
	 */
	default Page<T> selectPage(Page<T> page) {
		return selectPageModel(page, Condition.EMPTY);
	}

	/**
	 * 根据分页条件和参数条件查询分页MAP信息
	 * 
	 * @param page
	 *            分页条件
	 * @param wrapper
	 *            参数封装器
	 * @return 分页MAP信息
	 */
	default Page<Map<String, Object>> selectMapsPageModel(Page<Map<String, Object>> page, Wrapper<T> wrapper) {
		SqlHelper.fillWrapper(page, wrapper);
		page.setRecords(selectMapsPage(page, wrapper));
		return page;
	}

	/**
	 * 根据分页条件和参数条件查询分页实例信息
	 * 
	 * @param page
	 *            分页条件
	 * @param wrapper
	 *            参数封装器
	 * @return 分页实例信息
	 */
	default Page<T> selectPageModel(Page<T> page, Wrapper<T> wrapper) {
		SqlHelper.fillWrapper(page, wrapper);
		page.setRecords(selectPage(page, wrapper));
		return page;
	}

}
