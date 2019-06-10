package com.asion.business.module.dict.service;

import java.util.List;
import java.util.Map;

import com.asion.business.module.dict.model.Dict;
import com.asion.business.tree.service.BusinessTreeService;
import com.asion.common.base.BaseService;

/**
 * <p>
 * 字典表 : 用于存放各业务的类型字典 服务类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
public interface DictService extends BaseService<Dict>, BusinessTreeService<Dict> {

	/**
	 * 上移
	 * 
	 * @param dict
	 *            条件参数
	 * @return 操作是否成功
	 */
	boolean up(Dict dict);
	
	/**
	 * 下移
	 * 
	 * @param dict
	 *            条件参数
	 * @return 操作是否成功
	 */
	boolean down(Dict dict);
	
	/**
	 * 获取字典集合
	 * 
	 * @return 集合
	 */
	Map<String, Map<String, String>> dictMap();

	/**
	 * 根据父级代码获取字典
	 * 
	 * @param parentCode
	 *            父级代码
	 * @return 字典
	 */
	List<Map<String, String>> getDict(String parentCode);
	
	/**
	 * 根据字典代码获取字典名称
	 * 
	 * @param code
	 *            字典代码
	 * @return 字典名称
	 */
	String dictName(String code);

	/**
	 * 根据字典父级代码和字典代码获取字典名称
	 * 
	 * @param parentCode
	 *            父级代码
	 * @param code
	 *            字典代码
	 * @return 字典名称
	 */
	String dictName(String parentCode, String code);
	
	/**
	 * 根据父级代码获取字典
	 * 
	 * @param parentCode
	 *            父级代码
	 * @return 字典
	 */
	Object dictList(String parentCode);
	
}
