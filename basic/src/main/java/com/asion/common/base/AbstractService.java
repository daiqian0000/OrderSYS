package com.asion.common.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * 基础服务接口抽象类
 * 
 * @author chenboyang
 *
 * @param <M>
 *            业务Mapper类型
 * @param <T>
 *            业务对象类型
 */
public abstract class AbstractService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	@Autowired
	protected M baseMapper;

	/**
	 * list和page查询方法通用参数封装方法
	 * 
	 * @param map
	 *            参数
	 * @return 参数封装
	 */
	protected EntityWrapper<T> queryWrapper(Map<String, Object> map) {
		return new EntityWrapper<T>();
	}

	/**
	 * list和page查询方法通用结果处理方法
	 * 
	 * @param list
	 *            结果
	 * @return 处理后的结果
	 */
	protected List<T> valueQueryResult(List<T> list) {
		return list;
	}

	@Override
	public boolean add(T record) {
		return insert(record);
	}

	@Override
	public boolean del(Serializable id) {
		return deleteById(id);
	}

	@Override
	public boolean mod(T record) {
		return updateById(record);
	}

	public List<T> list(Map<String, Object> map) {
		List<T> list = baseMapper.selectList(queryWrapper(map));
		return valueQueryResult(list);
	}

	public Page<T> page(Page<T> page) {
		if (page == null) {
			page = new Page<T>();
		}
		EntityWrapper<T> wrapper = queryWrapper(page.getCondition());
		if (MapUtils.isNotEmpty(page.getCondition())) {
			page.getCondition().clear();
		}
		page = baseMapper.selectPageModel(page, wrapper);
		page.setRecords(valueQueryResult(page.getRecords()));
		return page;
	}

}
