package com.asion.business.module.org.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.module.org.mapper.OrgMapper;
import com.asion.business.module.org.model.Org;
import com.asion.business.module.user.mapper.UserMapper;
import com.asion.business.module.user.model.User;
import com.asion.business.tree.model.Tree;
import com.asion.business.tree.service.TreeService;
import com.asion.common.base.AbstractService;
import com.asion.common.exception.BusinessException;
import com.asion.common.util.IDUtil;
import com.asion.common.util.StringUtil;
import com.asion.dynamic.base.service.MyBatisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 机构表 : 系统操作员的机构管理 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class OrgServiceImpl extends AbstractService<OrgMapper, Org> implements OrgService {

	/**
	 * 树状数据服务接口
	 */
	@Autowired
	private TreeService treeService;
	
	/**
	 * 数据访问通用DAO
	 */
	@Autowired(required = false)
	@Qualifier("myBatisServiceImpl")
	private MyBatisService myBatisService;

	/**
	 * 用户数据访问接口
	 */
	@Autowired
	private UserMapper userMapper;

	@Override
	protected EntityWrapper<Org> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Org> wrapper = super.queryWrapper(map);
		StringBuilder sb = new StringBuilder();
		sb.append(Org.ID).append(",");
		sb.append(Org.ORG_ID).append(",");
		sb.append(Org.ORG_NAME).append(",");
		sb.append(Org.ORG_TYPE).append(",");
		sb.append(Org.PARENT_ID).append(",");
		sb.append(Org.SORT).append(",");
		sb.append(Org.AREA_ID).append(",");
		sb.append(Org.PERSON).append(",");
		sb.append(Org.TELEPHONE).append(",");
		sb.append(Org.ADDRESS).append(",");
		sb.append(Org.EMAIL).append(",");
		sb.append(Org.FAX).append(",");
		sb.append(Org.DESCRIPTION).append(",");
		sb.append("(IF ((");
		sb.append("SELECT COUNT(1) ");
		sb.append("FROM LOC_ORG T ");
		sb.append("WHERE T.PARENT_ID = LOC_ORG.ORG_ID");
		sb.append(") >= 1, TRUE, FALSE)) AS HAS_CHILDREN");
		wrapper.setSqlSelect(sb.toString());
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("orgName"))) {
				wrapper.like(Org.ORG_NAME, map.get("orgName").toString());
			}
			wrapper.eq(map.get("orgId") != null, Org.ORG_ID, map.get("orgId"));
			wrapper.eq(map.get("parentId") != null, Org.PARENT_ID, map.get("parentId"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Org.ORG_ID, map.get("keyword").toString())
					.or().like(Org.ORG_NAME, map.get("keyword").toString());
			}
		}
		wrapper.orderBy(MessageFormat.format("{0},{1}", Org.PARENT_ID, Org.SORT));
		return wrapper;
	}
	
	@Override
	public Function<Org, Tree> function() {
		return new Function<Org, Tree>() {
			public Tree apply(Org org) {
				Tree tree = new Tree();
				tree.setId(org.getOrgId());
				tree.setName(org.getOrgName());
				tree.setParentId(org.getParentId());
				return tree;
			}
		};
	}

	@Override
	public List<Tree> tree() {
		List<Org> list = selectList(new EntityWrapper<Org>().orderBy(MessageFormat.format("{0},{1}", Org.PARENT_ID, Org.SORT)));
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(function()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<Tree> tree(String parentId) {
		String recursive = baseMapper.downRecursive(parentId);
		List<Org> list = selectList(new EntityWrapper<Org>().in(Org.ORG_ID, recursive.split(",")).orderBy(MessageFormat.format("{0},{1}", Org.PARENT_ID, Org.SORT)));
		if (CollectionUtils.isNotEmpty(list)) {
			List<Tree> treeList = list.stream().map(function()).collect(Collectors.toList());
			Org parent = selectById(parentId);
			if (parent != null && parent.getParentId() != null) {
				return treeService.tree(treeList, parent.getParentId());
			} else {
				return treeService.tree(treeList);
			}
		}
		return null;
	}

	@Transactional
	public boolean add(Org record) {
		boolean result = false;
		if (record != null) {
			if (myBatisService.isExists("LOC_ORG", Org.ORG_ID, record.getOrgId())) {
				throw new BusinessException("组织机构编号已存在！");
			}
			if (StringUtils.isBlank(record.getOrgId())) {
				record.setOrgId(IDUtil.createTimeId());
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
		Org org = selectById(id);
		if (org != null) {
			int count = userMapper.selectCount(new EntityWrapper<User>().eq(Org.ORG_ID, org.getOrgId()));
			if (count > 0) {
				throw new BusinessException("机构有用户存在，无法删除！");
			}
		}
		result = deleteById(id);
		return result;
	}
	
	@Transactional
	public boolean up(Org org) {
		int num = 0;
		Integer sort = org.getSort();
		Org previous = selectOne(new EntityWrapper<Org>()
				.eq(Org.PARENT_ID, org.getParentId())
				.lt(Org.SORT, sort).orderBy(MessageFormat.format("{0} DESC LIMIT 1", Org.SORT)));
		if (previous != null) {
			org.setSort(previous.getSort());
			previous.setSort(sort);
			num = baseMapper.updateBatchById(Lists.newArrayList(org, previous));
		}
		return num == 2;
	}

	@Transactional
	public boolean down(Org org) {
		int num = 0;
		Integer sort = org.getSort();
		Org next = selectOne(new EntityWrapper<Org>()
				.eq(Org.PARENT_ID, org.getParentId())
				.gt(Org.SORT, sort).orderBy(MessageFormat.format("{0} LIMIT 1", Org.SORT)));
		if (next != null) {
			org.setSort(next.getSort());
			next.setSort(sort);
			num = baseMapper.updateBatchById(Lists.newArrayList(org, next));
		}
		return num == 2;
	}

}
