package com.asion.business.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.asion.business.tree.model.Tree;
import com.asion.common.util.JudgeUtil;

/**
 * 树状数据服务接口实现类
 * 
 * @author chenboyang
 *
 */
@Service
public class TreeServiceImpl implements TreeService {

	@Override
	public List<Tree> tree(List<Tree> list, String... topCodes) {
		List<Tree> result = null;
		if (CollectionUtils.isNotEmpty(list)) {
			result = new ArrayList<Tree>();
			Map<String, Tree> map = new HashMap<String, Tree>();
			list = new LinkedList<Tree>(list);
			int size = 0;
			while (list.size() != size) {
				size = list.size();
				Iterator<Tree> iterator = list.iterator();
				while (iterator.hasNext()) {
					Tree tree = iterator.next();
					map.put(tree.getId(), tree);
					if (StringUtils.isBlank(tree.getParentId()) || JudgeUtil.isOneEqual(tree.getParentId(), topCodes)) {
						if (tree.getLevel() == null) {
							tree.setLevel(1);
						}
						result.add(tree);
						iterator.remove();
						list.remove(tree);
					} else {
						if (map.containsKey(tree.getParentId())) {
							Tree parent = map.get(tree.getParentId());
							if (parent.getChildren() == null) {
								parent.setChildren(new ArrayList<Tree>());
							}
							if (tree.getLevel() == null) {
								int level = (parent.getLevel() != null ? parent.getLevel().intValue() : 0) + 1;
								tree.setLevel(level);
							}
							parent.getChildren().add(tree);
							iterator.remove();
							list.remove(tree);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<Tree> tree(List<Tree> list) {
		return tree(list, TOP_LEVEL, TOP_CODE);
	}

	@Override
	public List<Tree> tree(List<Tree> list, Tree top) {
		List<Tree> result = new ArrayList<Tree>();
		top.setChildren(tree(list, top.getId()));
		result.add(top);
		return result;
	}

	@Override
	public List<Tree> fullTree(List<Tree> list, String... topCodes) {
		List<Tree> result = null;
		if (CollectionUtils.isNotEmpty(list)) {
			result = new ArrayList<Tree>();
			Map<String, Tree> map = new HashMap<String, Tree>();
			list = new LinkedList<Tree>(list);
			int size = 0;
			while (list.size() != size) {
				size = list.size();
				Iterator<Tree> iterator = list.iterator();
				while (iterator.hasNext()) {
					Tree tree = iterator.next();
					map.put(tree.getId(), tree);
					if (StringUtils.isBlank(tree.getParentId()) || JudgeUtil.isOneEqual(tree.getParentId(), topCodes)) {
						if (tree.getLevel() == null) {
							tree.setLevel(1);
						}
						result.add(tree);
						iterator.remove();
						list.remove(tree);
					} else {
						if (map.containsKey(tree.getParentId())) {
							Tree parent = map.get(tree.getParentId());
							if (parent.getChildren() == null) {
								parent.setChildren(new ArrayList<Tree>());
							}
							if (tree.getLevel() == null) {
								int level = (parent.getLevel() != null ? parent.getLevel().intValue() : 0) + 1;
								tree.setLevel(level);
							}
							tree.setParent(parent);
							parent.getChildren().add(tree);
							iterator.remove();
							list.remove(tree);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<Tree> fullTree(List<Tree> list) {
		return fullTree(list, TOP_LEVEL, TOP_CODE);
	}

	@Override
	public List<Tree> fullTree(List<Tree> list, Tree top) {
		List<Tree> result = new ArrayList<Tree>();
		top.setChildren(fullTree(list, top.getId()));
		result.add(top);
		return result;
	}

}
