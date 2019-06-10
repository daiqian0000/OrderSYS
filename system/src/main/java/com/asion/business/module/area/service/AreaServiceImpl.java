package com.asion.business.module.area.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asion.business.module.area.mapper.AreaMapper;
import com.asion.business.module.area.model.Area;
import com.asion.business.sys.service.SysService;
import com.asion.business.tree.model.Tree;
import com.asion.business.tree.service.TreeService;
import com.asion.common.base.AbstractService;
import com.asion.common.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class AreaServiceImpl extends AbstractService<AreaMapper, Area> implements AreaService {

	/**
	 * 树状数据服务接口
	 */
	@Autowired
	private TreeService treeService;

	/**
	 * 系统服务接口
	 */
	@Autowired
	private SysService sysService;

	@Override
	protected EntityWrapper<Area> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Area> wrapper = super.queryWrapper(map);
		StringBuilder sb = new StringBuilder();
		sb.append(Area.ID).append(",");
		sb.append(Area.NAME).append(",");
		sb.append(Area.PARENT_ID).append(",");
		sb.append(Area.LEVEL).append(",");
		sb.append(Area.X).append(",");
		sb.append(Area.Y).append(",");
		sb.append(MessageFormat.format("ASTEXT({0}) AS {0}", Area.SHAPE)).append(",");
		sb.append("(IF ((");
		sb.append("SELECT COUNT(1) ");
		sb.append("FROM LOC_AREA T ");
		sb.append("WHERE T.PARENT_ID = LOC_AREA.ID ");
		sb.append(") >= 1, TRUE, FALSE)) AS HAS_CHILDREN");
		wrapper.setSqlSelect(sb.toString());
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("areaName"))) {
				wrapper.like(Area.NAME, map.get("areaName").toString());
			}
			wrapper.eq(map.get("parentId") != null, Area.PARENT_ID, map.get("parentId"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Area.ID, map.get("keyword").toString())
					.or().like(Area.NAME, map.get("keyword").toString());
			}
		}
		wrapper.orderBy(Area.ID);
		return wrapper;
	}

	@Override
	public List<Tree> tree() {
		if (!sysService.systemStorage().has(AREA_TREE)) {
			List<Area> list = selectList(new EntityWrapper<Area>().orderBy(MessageFormat.format("{0},{1}", Area.PARENT_ID, Area.ID)));
			if (CollectionUtils.isNotEmpty(list)) {
				List<Tree> treeList = list.stream().map(function()).collect(Collectors.toList());
				sysService.systemStorage().set(AREA_TREE, treeService.tree(treeList, Tree.createTop("中华人民共和国", true)));
			}
		}
		return sysService.systemStorage().get(AREA_TREE);
	}

	@Override
	public List<Tree> tree(int areaId) {
		if (areaId == 0) {
			return tree();
		} else if (areaId > 100000) {
			Area parent = selectById(areaId);
			if (parent != null && parent.getParentId() != null) {
				String recursive = baseMapper.downRecursive(areaId);
				List<Area> list = selectList(new EntityWrapper<Area>()
						.where(String.format("FIND_IN_SET(%s, {0})", Area.ID), recursive)
						.orderBy(MessageFormat.format("{0},{1}", Area.PARENT_ID, Area.ID)));
				if (CollectionUtils.isNotEmpty(list)) {
					List<Tree> treeList = list.stream().map(function()).collect(Collectors.toList());
					return treeService.tree(treeList, parent.getParentId().toString());
				}
			}
		}
		return null;
	}

	@Override
	public Function<Area, Tree> function() {
		return new Function<Area, Tree>() {
			public Tree apply(Area area) {
				Tree tree = new Tree();
				if (area.getId() != null) {
					tree.setId(area.getId().toString());
				}
				tree.setName(area.getName());
				if (area.getParentId() != null) {
					tree.setParentId(area.getParentId().toString());
				}
				tree.setLevel(area.getLevel());
				return tree;
			}
		};
	}

}
