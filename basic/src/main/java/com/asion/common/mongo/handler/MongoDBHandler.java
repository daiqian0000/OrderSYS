package com.asion.common.mongo.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import com.asion.common.mongo.service.GridFsService;
import com.asion.common.util.SpringContextUtil;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

/**
 * MongoDB处理器
 * 
 * @author chenboyang
 *
 */
public class MongoDBHandler extends MongoTemplate implements GridFsService {

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
	 * 初始化MongoDB处理器
	 * 
	 * @param factory
	 *            MongoDb工厂
	 * @param converter
	 *            MongoDb转换器
	 */
	public MongoDBHandler(MongoDbFactory factory, MongoConverter converter) {
		super(factory, converter);
		this.factory = factory;
		this.converter = converter;
		this.gridFsTemplateFactory = new GridFsTemplateFactoryImpl(factory, converter);
	}

	/**
	 * 系统应用服务接口
	 */
	private SysService sysService() {
		return SpringContextUtil.getBean(SysServiceImpl.class);
	}

	public GridFsTemplate gridFsTemplate() {
		if (gridFsTemplate == null) {
			gridFsTemplate = gridFsTemplateFactory.buildTemplate();
		}
		return gridFsTemplate;
	}

	public GridFsTemplate gridFsTemplate(String bucket) {
		if (!sysService().systemStorage().has(GRID_FS_TEMP_MAP)) {
			sysService().systemStorage().set(GRID_FS_TEMP_MAP, new TimeHashMap<String, GridFsTemplate>(GRIDFS_OPERATE_TIMEOUT, GRIDFS_OPERATE_TIMEUNIT));
		}
		TimeMap<String, GridFsTemplate> gridFsMap = sysService().systemStorage().get(GRID_FS_TEMP_MAP);
		if (!gridFsMap.containsKey(bucket)) {
			gridFsMap.put(bucket, gridFsTemplateFactory.buildTemplate(factory, converter, bucket));
		}
		return gridFsMap.get(bucket);
	}

	public GridFSFile save(InputStream content, String filename) {
		return gridFsTemplate().store(content, filename);
	}

	public GridFSFile save(InputStream content, String filename, String bucket) {
		return gridFsTemplate(bucket).store(content, filename);
	}

	public GridFSFile save(File file, String filename) {
		try {
			return save(new FileInputStream(file), filename);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public GridFSFile save(File file, String filename, String bucket) {
		try {
			return save(new FileInputStream(file), filename, bucket);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void delete(Query query) {
		gridFsTemplate().delete(query);
	}

	public void delete(Query query, String bucket) {
		gridFsTemplate(bucket).delete(query);
	}

	public void delete(String filename) {
		delete(filenameQuery(filename));
	}

	public void delete(String filename, String bucket) {
		delete(filenameQuery(filename), bucket);
	}

	public void deleteAll() {
		gridFsTemplate().delete(null);
	}

	public void deleteAll(String bucket) {
		gridFsTemplate(bucket).delete(null);
	}
	
	public GridFSDBFile query(Query query) {
		return gridFsTemplate().findOne(query);
	}

	public GridFSDBFile query(Query query, String bucket) {
		return gridFsTemplate(bucket).findOne(query);
	}

	public GridFSDBFile query(String filename) {
		return query(filenameQuery(filename));
	}

	public GridFSDBFile query(String filename, String bucket) {
		return query(filenameQuery(filename), bucket);
	}
	
	public List<GridFSDBFile> queryList(Query query) {
		return gridFsTemplate().find(query);
	}

	public List<GridFSDBFile> queryList(Query query, String bucket) {
		return gridFsTemplate(bucket).find(query);
	}

	public List<GridFSDBFile> queryAll() {
		return queryList(null);
	}

	public List<GridFSDBFile> queryAll(String bucket) {
		return queryList(null, bucket);
	}
	
	/**
	 * 获取文件名称查询条件
	 * 
	 * @param filename
	 *            文件名称
	 * @return 查询条件
	 */
	private Query filenameQuery(String filename) {
		return is(MongoDBHandler.FILENAME_KEY, filename);
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
