package com.asion.business.module.attach.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.module.attach.mapper.AttachMapper;
import com.asion.business.module.attach.model.Attach;
import com.asion.common.base.AbstractService;
import com.asion.common.io.storage.FileStorage;
import com.asion.common.util.IDUtil;
import com.asion.common.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 附件表 : 用于存放系统各业务的附件信息 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class AttachServiceImpl extends AbstractService<AttachMapper, Attach> implements AttachService {

	/**
	 * 文件存储接口
	 */
	@Autowired(required = false)
	private FileStorage fileStorage;

	@Override
	protected EntityWrapper<Attach> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Attach> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			wrapper.eq(map.get("bucket") != null, Attach.BUCKET, map.get("bucket"));
			wrapper.and(StringUtil.isNotBlank(map.get("targetIds")),
					String.format("FIND_IN_SET(%s, {0})", Attach.TARGET_ID), map.get("targetIds"));
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Attach.NAME, map.get("keyword").toString())
					.or().like(Attach.BUCKET, map.get("keyword").toString());
			}
		}
		return wrapper;
	}

	@Override
	public boolean add(Attach record) {
		boolean result = false;
		if (record != null && record.getInput() != null) {
			if (StringUtils.isBlank(record.getUrl())) {
				StringBuilder sb = new StringBuilder(IDUtil.createUUID32());
				if (StringUtils.isNotBlank(record.getType())) {
					if (!record.getType().startsWith(".")) {
						sb.append(".");
					}
					sb.append(record.getType());
				}
				record.setUrl(sb.toString());
			}
			if (StringUtils.isNotBlank(record.getBucket())) {
				record.setPresignedUrl(fileStorage.save(record.getBucket(), record.getUrl(), record.getInput()));
			} else {
				record.setPresignedUrl(fileStorage.save(record.getUrl(), record.getInput()));
			}
			result = insert(record);
			if (!result) {
				if (StringUtils.isNotBlank(record.getBucket())) {
					fileStorage.remove(record.getBucket(), record.getUrl());
				} else {
					fileStorage.remove(record.getUrl());
				}
			}
		}
		return result;
	}

	@Override
	public boolean mod(Attach record) {
		boolean result = false;
		if (record != null) {
			if (record.getInput() != null) {
				if (StringUtils.isNotBlank(record.getUrl())) {
					if (StringUtils.isNotBlank(record.getBucket())) {
						fileStorage.remove(record.getBucket(), record.getUrl());
					} else {
						fileStorage.remove(record.getUrl());
					}
				} else {
					StringBuilder sb = new StringBuilder(IDUtil.createUUID32());
					if (StringUtils.isNotBlank(record.getType())) {
						if (!record.getType().startsWith(".")) {
							sb.append(".");
						}
						sb.append(record.getType());
					}
					record.setUrl(sb.toString());
				}
				if (StringUtils.isNotBlank(record.getBucket())) {
					record.setPresignedUrl(fileStorage.save(record.getBucket(), record.getUrl(), record.getInput()));
				} else {
					record.setPresignedUrl(fileStorage.save(record.getUrl(), record.getInput()));
				}
			}
			result = updateById(record);
		}
		return result;
	}

	@Override
	public boolean addList(List<Attach> attachList) {
		boolean result = false;
		if (CollectionUtils.isNotEmpty(attachList)) {
			attachList.stream().filter((attach) -> attach.getInput() != null).forEach((attach) -> {
				if (StringUtils.isBlank(attach.getUrl())) {
					StringBuilder sb = new StringBuilder(IDUtil.createUUID32());
					if (StringUtils.isNotBlank(attach.getType())) {
						if (!attach.getType().startsWith(".")) {
							sb.append(".");
						}
						sb.append(attach.getType());
					}
					attach.setUrl(sb.toString());
				}
				if (StringUtils.isNotBlank(attach.getBucket())) {
					attach.setPresignedUrl(fileStorage.save(attach.getBucket(), attach.getUrl(), attach.getInput()));
				} else {
					attach.setPresignedUrl(fileStorage.save(attach.getUrl(), attach.getInput()));
				}
			});
			result = insertBatch(attachList);
			if (!result) {
				attachList.forEach((attach) -> {
					if (StringUtils.isNotBlank(attach.getBucket())) {
						fileStorage.remove(attach.getBucket(), attach.getUrl());
					} else {
						fileStorage.remove(attach.getUrl());
					}
				});
			}
		}
		return result;
	}

	@Override
	public boolean modList(List<Attach> attachList) {
		boolean result = false;
		if (CollectionUtils.isNotEmpty(attachList)) {
			attachList.stream().filter((attach) -> attach.getInput() != null).forEach((attach) -> {
				if (StringUtils.isNotBlank(attach.getUrl())) {
					if (StringUtils.isNotBlank(attach.getBucket())) {
						fileStorage.remove(attach.getBucket(), attach.getUrl());
					} else {
						fileStorage.remove(attach.getUrl());
					}
				} else {
					StringBuilder sb = new StringBuilder(IDUtil.createUUID32());
					if (StringUtils.isNotBlank(attach.getType())) {
						if (!attach.getType().startsWith(".")) {
							sb.append(".");
						}
						sb.append(attach.getType());
					}
					attach.setUrl(sb.toString());
				}
				if (StringUtils.isNotBlank(attach.getBucket())) {
					attach.setPresignedUrl(fileStorage.save(attach.getBucket(), attach.getUrl(), attach.getInput()));
				} else {
					attach.setPresignedUrl(fileStorage.save(attach.getUrl(), attach.getInput()));
				}
			});
			result = updateBatchById(attachList);
		}
		return result;
	}

	@Override
	public boolean del(Serializable id) {
		boolean result = false;
		Attach attach = selectById(id);
		result = deleteById(id);
		if (result && attach != null) {
			if (StringUtils.isNotBlank(attach.getBucket())) {
				fileStorage.remove(attach.getBucket(), attach.getUrl());
			} else {
				fileStorage.remove(attach.getUrl());
			}
		}
		return result;
	}

	@Transactional
	public boolean deleteByMap(Map<String, Object> map) {
		boolean result = false;
		List<Attach> attachList = null;
		if (MapUtils.isNotEmpty(map)) {
			EntityWrapper<Attach> wrapper = new EntityWrapper<Attach>();
			wrapper.eq(map.get("id") != null, Attach.ID, map.get("id"));
			if (StringUtil.isNotBlank(map.get("ids"))) {
				String[] ids = map.get("ids").toString().split(",");
				wrapper.in(Attach.ID, ids);
			}
			wrapper.eq(map.get("domainId") != null, Attach.DOMAIN_ID, map.get("domainId"));
			wrapper.eq(map.get("targetId") != null, Attach.TARGET_ID, map.get("targetId"));
			wrapper.eq(map.get("fieldId") != null, Attach.FIELD_ID, map.get("fieldId"));
			if (StringUtil.isNotBlank(map.get("targetIds"))) {
				String[] targetIds = map.get("targetIds").toString().split(",");
				wrapper.in(Attach.TARGET_ID, targetIds);
			}
			wrapper.eq(map.get("bucket") != null, Attach.BUCKET, map.get("bucket"));
			if (MapUtils.isNotEmpty(wrapper.getParamNameValuePairs())) {
				attachList = selectList(wrapper);
				result = delete(wrapper);
			}
		}
		if (result && CollectionUtils.isNotEmpty(attachList)) {
			attachList.forEach(new Consumer<Attach>() {
				public void accept(Attach attach) {
					if (StringUtils.isNotBlank(attach.getBucket())) {
						fileStorage.remove(attach.getBucket(), attach.getUrl());
					} else {
						fileStorage.remove(attach.getUrl());
					}
				}
			});
		}
		return result;
	}

}
