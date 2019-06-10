package com.asion.common.mongo.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

/**
 * MongoDB数据库文件存储系统服务接口
 * 
 * @author chenboyang
 *
 */
public interface GridFsService {

	/**
	 * MongoDB数据库文件存储操作模板容器
	 */
	String GRID_FS_TEMP_MAP = "gridFsTempMap";
	
	/**
	 * 文件名称字段名
	 */
	String FILENAME_KEY = "filename";

	/**
	 * 板块操作模板过期时间
	 */
	long GRIDFS_OPERATE_TIMEOUT = 10;

	/**
	 * 板块操作模板过期时间单位
	 */
	TimeUnit GRIDFS_OPERATE_TIMEUNIT = TimeUnit.MINUTES;

	/**
	 * 获取文件存储处理模板
	 * 
	 * @return 处理模板
	 */
	GridFsTemplate gridFsTemplate();

	/**
	 * 获取文件存储处理模板
	 * 
	 * @param bucket
	 *            存储板块
	 * @return 处理模板
	 */
	GridFsTemplate gridFsTemplate(String bucket);

	/**
	 * 保存文件流
	 * 
	 * @param content
	 *            文件流
	 * @param filename
	 *            文件名称
	 * @return 文件信息
	 */
	GridFSFile save(InputStream content, String filename);

	/**
	 * 保存文件流
	 * 
	 * @param content
	 *            文件流
	 * @param filename
	 *            文件名称
	 * @param bucket
	 *            存储板块
	 * @return 文件信息
	 */
	GridFSFile save(InputStream content, String filename, String bucket);

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @param filename
	 *            文件名称
	 * @return 文件信息
	 */
	GridFSFile save(File file, String filename);

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @param filename
	 *            文件名称
	 * @param bucket
	 *            存储板块
	 * @return 文件信息
	 */
	GridFSFile save(File file, String filename, String bucket);

	/**
	 * 删除文件
	 * 
	 * @param query
	 *            查询信息
	 */
	void delete(Query query);

	/**
	 * 删除文件
	 * 
	 * @param query
	 *            查询信息
	 * @param bucket
	 *            存储板块
	 */
	void delete(Query query, String bucket);

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            文件名称
	 */
	void delete(String filename);

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            文件名称
	 * @param bucket
	 *            存储板块
	 */
	void delete(String filename, String bucket);

	/**
	 * 删除整个数据库的全部文件
	 */
	void deleteAll();

	/**
	 * 删除指定存储板块的全部文件
	 * 
	 * @param bucket
	 *            存储板块
	 */
	void deleteAll(String bucket);

	/**
	 * 查询文件
	 * 
	 * @param query
	 *            查询信息
	 * @return 文件
	 */
	GridFSDBFile query(Query query);

	/**
	 * 查询文件
	 * 
	 * @param query
	 *            查询信息
	 * @param bucket
	 *            存储板块
	 * @return 文件
	 */
	GridFSDBFile query(Query query, String bucket);

	/**
	 * 查询文件
	 * 
	 * @param filename
	 *            文件名称
	 * @return 文件信息
	 */
	GridFSDBFile query(String filename);

	/**
	 * 查询文件
	 * 
	 * @param filename
	 *            文件名称
	 * @param bucket
	 *            存储区域
	 * @return 文件
	 */
	GridFSDBFile query(String filename, String bucket);

	/**
	 * 查询文件列表
	 * 
	 * @param query
	 *            查询信息
	 * @return 文件列表
	 */
	List<GridFSDBFile> queryList(Query query);

	/**
	 * 查询文件列表
	 * 
	 * @param query
	 *            查询信息
	 * @param bucket
	 *            存储板块
	 * @return 文件列表
	 */
	List<GridFSDBFile> queryList(Query query, String bucket);

	/**
	 * 查询整个数据库的全部文件
	 */
	List<GridFSDBFile> queryAll();

	/**
	 * 查询指定存储模板的全部文件
	 * 
	 * @param bucket
	 *            存储板块
	 */
	List<GridFSDBFile> queryAll(String bucket);

}
