package com.asion.common.mongo.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.asion.business.sys.service.SysService;
import com.asion.business.sys.service.SysServiceImpl;
import com.asion.common.model.time.TimeHashMap;
import com.asion.common.model.time.TimeMap;
import com.asion.common.mongo.factory.GridFsTemplateFactory;
import com.asion.common.mongo.factory.GridFsTemplateFactoryImpl;
import com.asion.common.mongo.handler.MongoDBHandler;
import com.asion.common.util.SpringContextUtil;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * <h1>MongoDB文件存储服务接口实现类</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
public class GridFsStorageImpl implements GridFsStorage {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * MongoDb工厂
	 */
	private MongoDbFactory factory;

	/**
	 * MongoDb转换器
	 */
	private MongoConverter converter;

	/**
	 * MongoDB数据库文件存储系统操作模板构建工厂
	 */
	private GridFsTemplateFactory gridFsTemplateFactory;

	/**
	 * MongoDB数据库文件存储处理模板
	 */
	private GridFsTemplate gridFsTemplate;

	/**
	 * 初始化MongoDB文件处存储服务
	 * 
	 * @param factory
	 *            MongoDb工厂
	 * @param converter
	 *            MongoDb转换器
	 */
	public GridFsStorageImpl(MongoDbFactory factory, MongoConverter converter) {
		this.factory = factory;
		this.converter = converter;
		this.gridFsTemplateFactory = new GridFsTemplateFactoryImpl(factory, converter);
	}

	/**
	 * 获取系统应用服务接口
	 * 
	 * @return 系统应用服务接口
	 */
	private SysService sysService() {
		return SpringContextUtil.getBean(SysServiceImpl.class);
	}

	@Override
	public String save(String bucket, String name, String path) {
		return save(bucket, name, new File(path));
	}

	@Override
	public String save(String name, String path) {
		return save(name, new File(path));
	}

	@Override
	public String save(String bucket, String name, File file) {
		try {
			return save(bucket, name, FileUtils.openInputStream(file));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String save(String name, File file) {
		try {
			return save(name, FileUtils.openInputStream(file));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String save(String bucket, String name, InputStream input) {
		return gridFsTemplate(bucket).store(input, name).getFilename();
	}

	@Override
	public String save(String name, InputStream input) {
		return gridFsTemplate().store(input, name).getFilename();
	}

	@Override
	public boolean exist(String bucket, String name) {
		return CollectionUtils.isNotEmpty(queryList(bucket, filenameQuery(name)));
	}

	@Override
	public boolean exist(String name) {
		return CollectionUtils.isNotEmpty(queryList(filenameQuery(name)));
	}

	@Override
	public void remove(String bucket, String name) {
		delete(bucket, filenameQuery(name));
	}

	@Override
	public void remove(String name) {
		delete(filenameQuery(name));
	}

	@Override
	public void copy(String bucket, String sourceName, String targetName) {
		GridFSDBFile file = query(bucket, sourceName);
		if (file != null) {
			save(bucket, targetName, file.getInputStream());
		}
	}

	@Override
	public void copy(String sourceName, String targetName) {
		GridFSDBFile file = query(sourceName);
		if (file != null) {
			save(targetName, file.getInputStream());
		}
	}

	@Override
	public InputStream find(String bucket, String name) {
		return query(bucket, name).getInputStream();
	}

	@Override
	public InputStream find(String name) {
		return query(name).getInputStream();
	}

	@Override
	public String findText(String bucket, String name, Charset charset) {
		try {
			List<String> textList = IOUtils.readLines(query(bucket, name).getInputStream(), charset);
			if (CollectionUtils.isNotEmpty(textList)) {
				return StringUtils.join(textList, "");
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String findText(String name, Charset charset) {
		try {
			List<String> textList = IOUtils.readLines(query(name).getInputStream(), charset);
			if (CollectionUtils.isNotEmpty(textList)) {
				return StringUtils.join(textList, "");
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String findText(String bucket, String name) {
		return findText(bucket, name, Charset.defaultCharset());
	}

	@Override
	public String findText(String name) {
		return findText(name, Charset.defaultCharset());
	}

	@Override
	public void write(String bucket, String name, OutputStream output) {
		try {
			IOUtils.copy(query(bucket, name).getInputStream(), output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void write(String name, OutputStream output) {
		try {
			IOUtils.copy(query(name).getInputStream(), output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public GridFsTemplate gridFsTemplate() {
		if (gridFsTemplate == null) {
			gridFsTemplate = gridFsTemplateFactory.buildTemplate();
		}
		return gridFsTemplate;
	}

	@Override
	public GridFsTemplate gridFsTemplate(String bucket) {
		if (!sysService().systemStorage().has(GRID_FS_TEMP_MAP)) {
			sysService().systemStorage().set(GRID_FS_TEMP_MAP,
					new TimeHashMap<String, GridFsTemplate>(GRIDFS_OPERATE_TIMEOUT, GRIDFS_OPERATE_TIMEUNIT));
		}
		TimeMap<String, GridFsTemplate> gridFsMap = sysService().systemStorage().get(GRID_FS_TEMP_MAP);
		if (!gridFsMap.containsKey(bucket)) {
			gridFsMap.put(bucket, gridFsTemplateFactory.buildTemplate(factory, converter, bucket));
		}
		return gridFsMap.get(bucket);
	}

	@Override
	public void delete(Query query) {
		gridFsTemplate().delete(query);
	}

	@Override
	public void delete(String bucket, Query query) {
		gridFsTemplate(bucket).delete(query);
	}

	@Override
	public void deleteAll() {
		gridFsTemplate().delete(null);
	}

	@Override
	public void deleteAll(String bucket) {
		gridFsTemplate(bucket).delete(null);
	}

	@Override
	public GridFSDBFile query(Query query) {
		return gridFsTemplate().findOne(query);
	}

	@Override
	public GridFSDBFile query(String bucket, Query query) {
		return gridFsTemplate(bucket).findOne(query);
	}

	@Override
	public GridFSDBFile query(String name) {
		return query(filenameQuery(name));
	}

	@Override
	public GridFSDBFile query(String bucket, String name) {
		return query(bucket, filenameQuery(name));
	}

	@Override
	public List<GridFSDBFile> queryList(Query query) {
		return gridFsTemplate().find(query);
	}

	@Override
	public List<GridFSDBFile> queryList(String bucket, Query query) {
		return gridFsTemplate(bucket).find(query);
	}

	@Override
	public List<GridFSDBFile> queryAll() {
		return queryList(null);
	}

	@Override
	public List<GridFSDBFile> queryAll(String bucket) {
		return queryList(bucket, null);
	}

	/**
	 * 获取文件名称查询条件
	 * 
	 * @param name
	 *            文件名称
	 * @return 查询条件
	 */
	private Query filenameQuery(String name) {
		return is(MongoDBHandler.FILENAME_KEY, name);
	}

	/**
	 * 获取相对比对查询条件
	 * 
	 * @param key
	 *            字段名称
	 * @param value
	 *            比对值
	 * @return 查询条件
	 */
	private Query is(String key, Object value) {
		return Query.query(Criteria.where(key).is(value));
	}

}
