package com.asion.business.module.permission.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.handler.constants.SystemConstants;
import com.asion.business.module.dict.service.DictService;
import com.asion.business.module.permission.mapper.MenuMapper;
import com.asion.business.module.permission.model.Menu;
import com.asion.business.module.role.mapper.RoleMenuMapper;
import com.asion.business.module.role.model.RoleMenu;
import com.asion.business.tree.model.Tree;
import com.asion.business.tree.service.TreeService;
import com.asion.common.base.AbstractService;
import com.asion.common.exception.BusinessException;
import com.asion.common.util.JudgeUtil;
import com.asion.common.util.StringUtil;
import com.asion.dynamic.base.service.MyBatisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class MenuServiceImpl extends AbstractService<MenuMapper, Menu> implements MenuService {

	/**
	 * 角色菜单关系Mapper接口
	 */
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/**
	 * 字典服务接口
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 数据访问通用DAO
	 */
	@Autowired(required = false)
	@Qualifier("myBatisServiceImpl")
	private MyBatisService myBatisService;

	@Override
	public Function<Menu, Tree> function() {
		return new Function<Menu, Tree>() {
			public Tree apply(Menu menu) {
				Tree tree = new Tree();
				tree.setId(menu.getMenuId());
				tree.setName(menu.getName());
				tree.setParentId(menu.getParentId());
				return tree;
			}
		};
	}

	@Override
	public List<Tree> tree() {
		List<Menu> list = selectList(new EntityWrapper<Menu>()
				.orderBy(MessageFormat.format("CAST({0} AS SIGNED),{1}", Menu.PARENT_ID, Menu.SORT)));
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(function()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<Menu> tree(String sysId, Collection<String> roleIds) {
		EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
		wrapper.eq(sysId != null, Menu.SYS_ID, sysId);
		if (CollectionUtils.isNotEmpty(roleIds)) {
			List<Object> menuIds = roleMenuMapper.selectObjs(new EntityWrapper<RoleMenu>()
					.setSqlSelect(RoleMenu.MENU_ID).in(RoleMenu.ROLE_ID, roleIds));
			if (CollectionUtils.isNotEmpty(menuIds)) {
				wrapper.in(Menu.MENU_ID, menuIds).orderBy(MessageFormat.format("CAST({0} AS SIGNED),{1}", Menu.PARENT_ID, Menu.SORT));
			} else {
				wrapper.eq(Menu.MENU_ID, "-");
			}
		}
		List<Menu> list = selectList(wrapper);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Menu> cacheMap = new HashMap<String, Menu>();
			List<Menu> result = new ArrayList<Menu>();
			list = new LinkedList<Menu>(list);
			int size = 0;
			while (list.size() != size) {
				size = list.size();
				Iterator<Menu> iterator = list.iterator();
				while (iterator.hasNext()) {
					Menu menu = iterator.next();
					cacheMap.put(menu.getMenuId(), menu);
					if (StringUtils.isBlank(menu.getParentId()) || JudgeUtil.isOneEqual(menu.getParentId(),
							TreeService.TOP_LEVEL, TreeService.TOP_CODE)) {
						result.add(menu);
						iterator.remove();
						list.remove(menu);
					} else {
						if (cacheMap.containsKey(menu.getParentId())) {
							Menu parent = cacheMap.get(menu.getParentId());
							if (parent.getChildren() == null) {
								parent.setChildren(new ArrayList<Menu>());
							}
							parent.getChildren().add(menu);
							iterator.remove();
							list.remove(menu);
						}
					}
				}
			}
			return result;
		}
		return null;
	}

	@Override
	protected EntityWrapper<Menu> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Menu> wrapper = super.queryWrapper(map);
		StringBuilder sb = new StringBuilder();
		sb.append(Menu.ID).append(",");
		sb.append(Menu.MENU_ID).append(",");
		sb.append(Menu.NAME).append(",");
		sb.append(Menu.TYPE).append(",");
		sb.append(Menu.PARENT_ID).append(",");
		sb.append(Menu.SYS_ID).append(",");
		sb.append(Menu.ICON).append(",");
		sb.append(Menu.HREF).append(",");
		sb.append(Menu.TARGET).append(",");
		sb.append(Menu.PERMISSION).append(",");
		sb.append(Menu.SORT).append(",");
		sb.append(Menu.DISPLAY).append(",");
		sb.append("(IF ((");
		sb.append("SELECT COUNT(1) ");
		sb.append("FROM LOC_MENU T ");
		sb.append("WHERE T.PARENT_ID = LOC_MENU.MENU_ID");
		sb.append(") >= 1, TRUE, FALSE)) AS HAS_CHILDREN");
		wrapper.setSqlSelect(sb.toString());
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("menuName"))) {
				wrapper.like(Menu.NAME, map.get("menuName").toString());
			}
			wrapper.eq(map.get("parentId") != null, Menu.PARENT_ID, map.get("parentId"));
			wrapper.eq(map.get("sysId") != null, Menu.SYS_ID, map.get("sysId"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Menu.MENU_ID, map.get("keyword").toString())
					.or().like(Menu.NAME, map.get("keyword").toString());
			}
		}
		wrapper.orderBy(MessageFormat.format("{0},{1}", Menu.PARENT_ID, Menu.SORT));
		return wrapper;
	}

	@Override
	protected List<Menu> valueQueryResult(List<Menu> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(new Consumer<Menu>() {
				public void accept(Menu menu) {
					menu.setTypeName(dictService.dictName(SystemConstants.DICT_MENU_TYPE, menu.getType()));
					menu.setSysName(dictService.dictName(SystemConstants.DICT_SYS_FLAG, menu.getSysId()));
				}
			});
		}
		return list;
	}
	
	@Transactional
	public boolean add(Menu record) {
		boolean result = false;
		if (record != null) {
			if (myBatisService.isExists("LOC_MENU", Menu.MENU_ID, record.getMenuId())) {
				throw new BusinessException("组织机构编号已存在！");
			}
			if (StringUtils.isBlank(record.getMenuId())) {
				record.setMenuId(String.valueOf(myBatisService.currSeq("LOC_MENU", Menu.MENU_ID)));
			}
			if (StringUtils.isBlank(record.getParentId())) {
				record.setParentId(TreeService.TOP_LEVEL);
			}
			if (record.getSort() == null || record.getSort().intValue() <= 0) {
				Integer sort = baseMapper.currSort(record.getParentId());
				if (sort == null || sort.intValue() <= 0) {
					sort = 1;
				} else {
					sort++;
				}
				record.setSort(sort);
			}
			result = insert(record);
		}
		return result;
	}
	
	@Transactional
	public boolean del(Serializable id) {
		boolean result = false;
		roleMenuMapper.delete(new EntityWrapper<RoleMenu>().eq(Menu.MENU_ID, id));
		result = deleteById(id);
		return result;
	}
	
	@Transactional
	public boolean up(Menu menu) {
		int num = 0;
		Integer sort = menu.getSort();
		Menu previous = selectOne(new EntityWrapper<Menu>()
				.eq(Menu.PARENT_ID, menu.getParentId())
				.lt(Menu.SORT, sort).orderBy(MessageFormat.format("{0} DESC LIMIT 1", Menu.SORT)));
		if (previous != null) {
			menu.setSort(previous.getSort());
			previous.setSort(sort);
			num = baseMapper.updateBatchById(Lists.newArrayList(menu, previous));
		}
		return num == 2;
	}

	@Transactional
	public boolean down(Menu menu) {
		int num = 0;
		Integer sort = menu.getSort();
		Menu next = selectOne(new EntityWrapper<Menu>()
				.eq(Menu.PARENT_ID, menu.getParentId())
				.gt(Menu.SORT, sort).orderBy(MessageFormat.format("{0} LIMIT 1", Menu.SORT)));
		if (next != null) {
			menu.setSort(next.getSort());
			next.setSort(sort);
			num = baseMapper.updateBatchById(Lists.newArrayList(menu, next));
		}
		return num == 2;
	}

}
