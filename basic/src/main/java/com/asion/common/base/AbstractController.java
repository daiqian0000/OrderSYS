package com.asion.common.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asion.business.web.annotation.RequestModel;
import com.asion.common.util.ParamBroker;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <h1>SpringMVC基础控制器接口抽象类</h1>
 * <p>
 * 此类的通用接口不支持权限拦截，如要进行权限拦截，请不要继承此类换成实现{@link BaseController}接口
 * </p>
 * 
 * @author chenboyang
 *
 * @param <T>
 *            基本业务类型
 */
@Deprecated
public abstract class AbstractController<S extends BaseService<T>, T> implements BaseController<T> {

	/**
	 * 获取基本服务接口
	 */
	@Autowired
	protected S baseService;
	
	/**
	 * 新增记录
	 * 
	 * @param model
	 *            记录
	 * @return 操作记录数
	 */
	@RequestMapping("/add")
	public Object add(@RequestModel T model) {
		return baseService.add(model);
	}

	/**
	 * 删除记录
	 * 
	 * @param id
	 *            编号
	 * @return 操作记录数
	 */
	@RequestMapping("/del")
	public Object del(@RequestModel Serializable id) {
		return baseService.del(id);
	}

	/**
	 * 修改记录
	 * 
	 * @param model
	 *            记录
	 * @return 操作记录数
	 */
	@RequestMapping("/mod")
	public Object mod(@RequestModel T model) {
		return baseService.mod(model);
	}

	/**
	 * 查询记录
	 * 
	 * @param param
	 *            条件
	 * @return 记录
	 */
	@RequestMapping("/list")
	public Object list(@RequestModel ParamBroker<String, Object> param) {
		return baseService.list(param.getParam());
	}

	/**
	 * 分页查询记录
	 * 
	 * @param page
	 *            分页信息
	 * @return 分页记录
	 */
	@RequestMapping("/page")
	public Object page(@RequestModel Page<T> page) {
		return baseService.page(page);
	}

}
