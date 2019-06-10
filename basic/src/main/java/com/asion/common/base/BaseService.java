package com.asion.common.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 基础服务接口
 * 
 * @author chenboyang
 *
 * @param <T>
 *            业务类型
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 新增记录
	 * 
	 * @param record
	 *            记录
	 * @return 操作记录数
	 */
	boolean add(T record);

	/**
	 * 根据主键删除记录
	 * 
	 * @param id
	 *            主键
	 * @return 操作记录数
	 */
	boolean del(Serializable id);

	/**
	 * 修改记录
	 * 
	 * @param record
	 *            记录
	 * @return 操作记录数
	 */
	boolean mod(T record);

	/**
	 * 查询记录
	 * 
	 * @param map
	 *            条件
	 * @return 记录
	 */
	List<T> list(Map<String, Object> map);

	/**
	 * 分页查询记录
	 * 
	 * @param page
	 *            分页条件
	 * @return 分页记录
	 */
	Page<T> page(Page<T> page);

}
