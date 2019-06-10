package com.asion.business.module.dict.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.handler.constants.SystemConstants;
import com.asion.business.module.dict.mapper.DictMapper;
import com.asion.business.module.dict.model.Dict;
import com.asion.business.sys.service.SysService;
import com.asion.business.tree.model.Tree;
import com.asion.business.tree.service.TreeService;
import com.asion.common.base.AbstractService;
import com.asion.common.util.IDUtil;
import com.asion.common.util.JudgeUtil;
import com.asion.common.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 字典表 : 用于存放各业务的类型字典 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class DictServiceImpl extends AbstractService<DictMapper, Dict> implements DictService {

	/**
	 * 系统服务接口
	 */
	@Autowired
	private SysService sysService;

	@Override
	protected EntityWrapper<Dict> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Dict> wrapper = super.queryWrapper(map);
		StringBuilder sb = new StringBuilder();
		sb.append(Dict.ID).append(",");
		sb.append(Dict.CODE).append(",");
		sb.append(Dict.NAME).append(",");
		sb.append(Dict.PARENT_CODE).append(",");
		sb.append(Dict.SORT).append(",");
		sb.append(Dict.DESCRIPTION).append(",");
		sb.append("(IF ((");
		sb.append("SELECT COUNT(1) ");
		sb.append("FROM LOC_DICT T ");
		sb.append("WHERE T.PARENT_CODE = LOC_DICT.CODE");
		sb.append(") >= 1, TRUE, FALSE)) AS HAS_CHILDREN");
		wrapper.setSqlSelect(sb.toString());
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("dictName"))) {
				wrapper.like(Dict.NAME, map.get("dictName").toString());
			}
			wrapper.eq(map.get("parentCode") != null, Dict.PARENT_CODE, map.get("parentCode"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Dict.CODE, map.get("keyword").toString())
					.or().like(Dict.NAME, map.get("keyword").toString());
			}
		}
		wrapper.orderBy(MessageFormat.format("{0},{1}", Dict.PARENT_CODE, Dict.SORT));
		return wrapper;
	}

	@Transactional
	public boolean add(Dict record) {
		if (record != null) {
			if (StringUtils.isBlank(record.getCode())) {
				record.setCode(IDUtil.createTimeId());
			}
			if (StringUtils.isBlank(record.getParentCode())) {
				record.setParentCode(TreeService.TOP_CODE);
			}
			if (record.getSort() == null || record.getSort().intValue() <= 0) {
				Integer sort = baseMapper.currSort(record.getParentCode());
				if (sort == null || sort.intValue() <= 0) {
					sort = 1;
				} else {
					sort++;
				}
				record.setSort(sort);
			}
			if (insert(record)) {
				if (MapUtils.isNotEmpty(dictMap())) {
					if (StringUtils.isBlank(record.getParentCode()) || JudgeUtil.isOneEqual(record.getParentCode(),
							TreeService.TOP_LEVEL, TreeService.TOP_CODE)) {
						dictMap().put(record.getCode(), new ConcurrentHashMap<String, String>());
					} else {
						dictMap().get(record.getParentCode()).put(record.getCode(), record.getName());
					}
				}
				return true;
			}
		}
		return false;
	}
	
	@Transactional
	public boolean mod(Dict record) {
		if (record != null) {
			if (updateById(record)) {
				if (MapUtils.isNotEmpty(dictMap())) {
					if (StringUtils.isBlank(record.getParentCode()) || JudgeUtil.isOneEqual(record.getParentCode(),
							TreeService.TOP_LEVEL, TreeService.TOP_CODE)) {
						sysService.systemStorage().replace(SystemConstants.DICT, dictMap());
					} else {
						dictMap().get(record.getParentCode()).clear();
						List<Dict> list = selectList(new EntityWrapper<Dict>().eq(Dict.PARENT_CODE, record.getParentCode()).orderBy(MessageFormat.format("{0},{1}", Dict.PARENT_CODE, Dict.SORT)));
						list.forEach((t) -> dictMap().get(record.getParentCode()).put(t.getCode(), t.getName()));
					}
				}
				return true;
			}
		}
		return false;
	}
	
	@Transactional
	public boolean del(Serializable id) {
		Dict dict = selectById(id);
		if (dict != null) {
			if (deleteById(id)) {
				if (dict.getCode() != null && dict.getParentCode() != null) {
					if (MapUtils.isNotEmpty(dictMap())) {
						if (StringUtils.isBlank(dict.getParentCode()) || dict.getParentCode().equals(TreeService.TOP_CODE)) {
							dictMap().remove(dict.getCode());
						} else {
							dictMap().get(dict.getParentCode()).remove(dict.getCode());
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Transactional
	public boolean up(Dict dict) {
		int num = 0;
		Integer sort = dict.getSort();
		Dict previous = selectOne(new EntityWrapper<Dict>()
				.eq(Dict.PARENT_CODE, dict.getParentCode())
				.lt(Dict.SORT, sort).orderBy(MessageFormat.format("{0} DESC LIMIT 1", Dict.SORT)));
		if (previous != null) {
			dict.setSort(previous.getSort());
			previous.setSort(sort);
			num = baseMapper.updateBatchById(Lists.newArrayList(dict, previous));
		}
		return num == 2;
	}

	@Transactional
	public boolean down(Dict dict) {
		int num = 0;
		Integer sort = dict.getSort();
		Dict next = selectOne(new EntityWrapper<Dict>()
				.eq(Dict.PARENT_CODE, dict.getParentCode())
				.gt(Dict.SORT, sort).orderBy(MessageFormat.format("{0} LIMIT 1", Dict.SORT)));
		if (next != null) {
			dict.setSort(next.getSort());
			next.setSort(sort);
			num = baseMapper.updateBatchById(Lists.newArrayList(dict, next));
		}
		return num == 2;
	}

	@Override
	public Map<String, Map<String, String>> dictMap() {
		Map<String, Map<String, String>> dictMap = null;
		if (sysService.systemStorage().has(SystemConstants.DICT)) {
			dictMap = sysService.systemStorage().get(SystemConstants.DICT);
		} else {
			List<Tree> list = new LinkedList<Tree>(tree());
			if (CollectionUtils.isNotEmpty(list)) {
				dictMap = new ConcurrentHashMap<String, Map<String, String>>();
				int size = 0;
				while (list.size() != size) {
					size = list.size();
					Iterator<Tree> iterator = list.iterator();
					while (iterator.hasNext()) {
						Tree node = iterator.next();
						if (StringUtils.isBlank(node.getParentId()) || JudgeUtil.isOneEqual(node.getParentId(),
								TreeService.TOP_LEVEL, TreeService.TOP_CODE)) {
							dictMap.put(node.getId(), new ConcurrentHashMap<String, String>());
							iterator.remove();
							list.remove(node);
						} else {
							if (dictMap.containsKey(node.getParentId())) {
								Map<String, String> parent = dictMap.get(node.getParentId());
								parent.put(node.getId(), node.getName());
								iterator.remove();
								list.remove(node);
							}
						}
					}
				}
				sysService.systemStorage().set(SystemConstants.DICT, dictMap);
			}
		}
		return dictMap;
	}

	@Override
	public List<Map<String, String>> getDict(String parentCode) {
		List<Map<String, String>> list = null;
		if (StringUtils.isNotBlank(parentCode)) {
			if (MapUtils.isNotEmpty(dictMap()) && dictMap().containsKey(parentCode)) {
				Map<String, String> infoMap = dictMap().get(parentCode);
				if (MapUtils.isNotEmpty(infoMap)) {
					list = infoMap.entrySet().stream().map(new Function<Entry<String, String>, Map<String, String>>() {
						@Override
						public Map<String, String> apply(Entry<String, String> entry) {
							Map<String, String> item = new HashMap<String, String>();
							item.put("code", entry.getKey());
							item.put("name", entry.getValue());
							return item;
						}
					}).collect(Collectors.toList());
				}
			}
		}
		return list;
	}

	@Override
	public String dictName(String code) {
		if (StringUtils.isNotBlank(code)) {
			if (MapUtils.isNotEmpty(dictMap())) {
				for (Map<String, String> map : dictMap().values()) {
					if (MapUtils.isNotEmpty(map) && map.containsKey(code)) {
						return map.get(code);
					}
				}
			}
		}
		return null;
	}

	@Override
	public String dictName(String parentCode, String code) {
		if (JudgeUtil.isAllNotBlank(parentCode, code) && MapUtils.isNotEmpty(dictMap())) {
			Map<String, String> map = dictMap().get(parentCode);
			if (MapUtils.isNotEmpty(map)) {
				return map.get(code);
			}
		}
		return null;
	}

	@Override
	public Object dictList(String parentCode) {
		List<Map<String, String>> list = getDict(parentCode);
		if (CollectionUtils.isNotEmpty(list)) {
			return list;
		} else {
			return selectList(new EntityWrapper<Dict>().eq(Dict.PARENT_CODE, parentCode).orderBy(MessageFormat.format("{0},{1}", Dict.PARENT_CODE, Dict.SORT)));
		}
	}

	@Override
	public Function<Dict, Tree> function() {
		return new Function<Dict, Tree>() {
			public Tree apply(Dict dict) {
				Tree tree = new Tree();
				tree.setId(dict.getCode());
				tree.setName(dict.getName());
				tree.setParentId(dict.getParentCode());
				return tree;
			}
		};
	}
	
	@Override
	public List<Tree> tree() {
		List<Dict> list = selectList(new EntityWrapper<Dict>().orderBy(MessageFormat.format("{0},{1}", Dict.PARENT_CODE, Dict.SORT)));
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(function()).collect(Collectors.toList());
		}
		return null;
	}

}
