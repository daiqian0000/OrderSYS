package com.asion.business.module.attach.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asion.business.module.attach.mapper.AttachMapper;
import com.asion.business.module.attach.mapper.BucketMapper;
import com.asion.business.module.attach.model.Attach;
import com.asion.business.module.attach.model.Bucket;
import com.asion.common.base.AbstractService;
import com.asion.common.exception.BusinessException;
import com.asion.common.io.bucket.BucketManager;
import com.asion.common.util.StringUtil;
import com.asion.dynamic.base.service.MyBatisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * <p>
 * 附件存储模块表 : 用于存放系统各业务的附件信息 服务实现类
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@Service
public class BucketServiceImpl extends AbstractService<BucketMapper, Bucket> implements BucketService {

	/**
	 * 附件信息Mapper接口 
	 */
	@Autowired
	private AttachMapper attachMapper;

	/**
	 * 存储板块管理接口
	 */
	@Autowired(required = false)
	private BucketManager bucketManager;

	/**
	 * 数据访问通用DAO
	 */
	@Autowired(required = false)
	@Qualifier("myBatisServiceImpl")
	private MyBatisService myBatisService;

	@Override
	protected EntityWrapper<Bucket> queryWrapper(Map<String, Object> map) {
		EntityWrapper<Bucket> wrapper = super.queryWrapper(map);
		if (MapUtils.isNotEmpty(map)) {
			if (StringUtil.isNotBlank(map.get("keyword"))) {
				wrapper.andNew().like(Bucket.NAME, map.get("keyword").toString())
					.or().like(Bucket.BUCKET, map.get("keyword").toString());
			}
		}
		return wrapper;
	}

	protected List<Bucket> valueQueryResult(List<Bucket> list) {
		if (CollectionUtils.isNotEmpty(list) && bucketManager != null) {
			list.forEach(new Consumer<Bucket>() {
				public void accept(Bucket bucket) {
					bucket.setCount(bucketManager.count(bucket.getBucket()));
				}
			});
		}
		return super.valueQueryResult(list);
	}

	@Transactional
	public boolean add(Bucket record) {
		boolean result = false;
		if (record != null) {
			if (myBatisService.isExists("LOC_BUCKET", Bucket.BUCKET, record.getBucket())) {
				throw new BusinessException("存储板块已存在！");
			}
			record.setCollection(record.getBucket() + BUCKET_SUFFIX);
			result = insert(record);
			if (result && bucketManager != null) {
				bucketManager.create(record.getBucket());
			}
		}
		return result;
	}

	@Transactional
	public boolean del(Serializable id) {
		Bucket bucket = selectById(id);
		if (bucket != null) {
			if (deleteById(id) && bucket.getBucket() != null) {
				int attachDelNum = attachMapper.delete(new EntityWrapper<Attach>().eq(Bucket.BUCKET, bucket.getBucket()));
				if (attachDelNum > 0 && bucketManager != null) {
					bucketManager.delete(bucket.getBucket());
					return true;
				}
			}
		}
		return false;
	}

}
