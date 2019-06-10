package com.asion.common.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.asion.common.base.BaseMapper;
import com.asion.common.job.AbstractJobExecutor;
import com.asion.common.job.RedisToMapperExecutor;
import com.asion.common.redis.storage.hash.RedisHashStorage;
import com.asion.common.redis.storage.list.RedisListStorage;
import com.asion.common.redis.storage.set.RedisSetStorage;

/**
 * Redis到数据访问层执行器接口实现类
 * 
 * @author chenboyang
 *
 */
public class RedisToMapperExecutorImpl extends AbstractJobExecutor implements RedisToMapperExecutor {

	/**
	 * 默认批量操作数量
	 */
	private int defaultBatchSize = 1000;

	public <K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper) {
		addListRedisHashToMapper(redisHash, mapper, defaultBatchSize);
	}

	@Transactional
	public <K, V> void addListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize) {
		Set<K> keys = redisHash.keys();
		if (CollectionUtils.isNotEmpty(keys)) {
			List<V> values = redisHash.multiGet(keys);
			if (CollectionUtils.isNotEmpty(values)) {
				int num = mapper.insertBatch(values, batchSize);
				if (num > 0) {
					redisHash.delete(keys);
					doubleOperateLog("Redis哈希存储", redisHash.getClass().getName(), "同步新增列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

	public <K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper) {
		modListRedisHashToMapper(redisHash, mapper, defaultBatchSize);
	}

	@Transactional
	public <K, V> void modListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper, int batchSize) {
		Set<K> keys = redisHash.keys();
		if (CollectionUtils.isNotEmpty(keys)) {
			List<V> values = redisHash.multiGet(keys);
			if (CollectionUtils.isNotEmpty(values)) {
				int num = mapper.updateBatchById(values, batchSize);
				if (num > 0) {
					redisHash.delete(keys);
					doubleOperateLog("Redis哈希存储", redisHash.getClass().getName(), "同步修改列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

	public <K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper) {
		addOrModListRedisHashToMapper(redisHash, mapper, defaultBatchSize);
	}

	@Transactional
	public <K, V> void addOrModListRedisHashToMapper(RedisHashStorage<K, V> redisHash, BaseMapper<V> mapper,
			int batchSize) {
		Set<K> keys = redisHash.keys();
		if (CollectionUtils.isNotEmpty(keys)) {
			List<V> values = redisHash.multiGet(keys);
			if (CollectionUtils.isNotEmpty(values)) {
				int num = mapper.insertOrUpdateBatch(values, batchSize);
				if (num > 0) {
					redisHash.delete(keys);
					doubleOperateLog("Redis哈希存储", redisHash.getClass().getName(), "同步新增或修改列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

	public <E> void addListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper) {
		addListRedisListToMapper(redisList, mapper, defaultBatchSize);
	}

	@Transactional
	public <E> void addListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper, int batchSize) {
		Long size = redisList.size();
		if (size != null && size.longValue() > 0) {
			List<E> list = new ArrayList<E>();
			while (redisList.size() > 0) {
				E e = redisList.rightPop();
				if (e != null) {
					list.add(e);
				}
			}
			if (CollectionUtils.isNotEmpty(list)) {
				int num = mapper.insertBatch(list, batchSize);
				if (num > 0) {
					doubleOperateLog("Redis列表存储", redisList.getClass().getName(), "同步新增列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

	public <E> void modListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper) {
		modListRedisListToMapper(redisList, mapper, defaultBatchSize);
	}

	@Transactional
	public <E> void modListRedisListToMapper(RedisListStorage<E> redisList, BaseMapper<E> mapper, int batchSize) {
		Long size = redisList.size();
		if (size != null && size.longValue() > 0) {
			List<E> list = new ArrayList<E>();
			while (redisList.size() > 0) {
				E e = redisList.rightPop();
				if (e != null) {
					list.add(e);
				}
			}
			if (CollectionUtils.isNotEmpty(list)) {
				int num = mapper.updateBatchById(list, batchSize);
				if (num > 0) {
					doubleOperateLog("Redis列表存储", redisList.getClass().getName(), "同步修改列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

	public <E> void addListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper) {
		addListRedisSetToMapper(redisSet, mapper, defaultBatchSize);
	}

	@Transactional
	public <E> void addListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper, int batchSize) {
		Long size = redisSet.size();
		if (size != null && size.longValue() > 0) {
			List<E> list = new ArrayList<E>();
			while (redisSet.size() > 0) {
				E e = redisSet.pop();
				if (e != null) {
					list.add(e);
				}
			}
			if (CollectionUtils.isNotEmpty(list)) {
				int num = mapper.insertBatch(list, batchSize);
				if (num > 0) {
					doubleOperateLog("Redis集合存储", redisSet.getClass().getName(), "同步新增列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

	public <E> void modListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper) {
		modListRedisSetToMapper(redisSet, mapper, defaultBatchSize);
	}

	@Transactional
	public <E> void modListRedisSetToMapper(RedisSetStorage<E> redisSet, BaseMapper<E> mapper, int batchSize) {
		Long size = redisSet.size();
		if (size != null && size.longValue() > 0) {
			List<E> list = new ArrayList<E>();
			while (redisSet.size() > 0) {
				E e = redisSet.pop();
				if (e != null) {
					list.add(e);
				}
			}
			if (CollectionUtils.isNotEmpty(list)) {
				int num = mapper.updateBatchById(list, batchSize);
				if (num > 0) {
					doubleOperateLog("Redis集合存储", redisSet.getClass().getName(), "同步修改列表", num, "数据访问接口",
							mapper.getClass().getTypeName());
				}
			}
		}
	}

}
