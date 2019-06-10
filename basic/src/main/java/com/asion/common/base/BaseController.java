package com.asion.common.base;

import java.io.Serializable;

import com.asion.common.util.ParamBroker;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <h1>SpringMVC基础控制器接口</h1>
 * 
 * @author chenboyang
 * 
 * @param <T>
 *            基本业务类型
 */
@Deprecated
public interface BaseController<T> {

	/**
	 * 新增记录
	 * 
	 * @param record
	 *            记录
	 * @return 操作记录数
	 */
	Object add(T record);

	/**
	 * 删除记录
	 * 
	 * @param id
	 *            编号
	 * @return 操作记录数
	 */
	Object del(Serializable id);

	/**
	 * 修改记录
	 * 
	 * @param record
	 *            记录
	 * @return 操作记录数
	 */
	Object mod(T record);

	/**
	 * 查询记录
	 * 
	 * @param param
	 *            条件
	 * @return 记录
	 */
	Object list(ParamBroker<String, Object> param);

	/**
	 * 分页查询记录
	 * 
	 * @param page
	 *            分页信息
	 * @return 分页记录
	 */
	Object page(Page<T> page);

}
