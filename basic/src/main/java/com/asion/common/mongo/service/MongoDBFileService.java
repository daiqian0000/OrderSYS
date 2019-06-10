package com.asion.common.mongo.service;

import java.io.OutputStream;

import com.mongodb.gridfs.GridFSFile;

/**
 * MongoDB文件存储服务接口
 * 
 * @author chenboyang
 *
 */
public interface MongoDBFileService {

	/**
	 * 文件下载
	 * 
	 * @param name
	 *            文件名称
	 * @param output
	 *            文件输出流
	 */
	void download(String name, OutputStream output);

	/**
	 * 文件下载
	 * 
	 * @param name
	 *            文件名称
	 * @param bucket
	 *            存储模块名称
	 * @param output
	 *            文件输出流
	 */
	void download(String name, String bucket, OutputStream output);

	/**
	 * 保存文件到MongoDB数据库
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件信息
	 */
	GridFSFile saveFileToMongo(String fileName);

	/**
	 * 保存文件到MongoDB数据库
	 * 
	 * @param fileName
	 *            文件名
	 * @param bucket
	 *            存储板块
	 * @return 文件信息
	 */
	GridFSFile saveFileToMongo(String fileName, String bucket);
	
}
