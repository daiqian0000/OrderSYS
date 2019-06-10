package com.asion.common.mongo.service;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.asion.business.web.service.ApplyService;
import com.asion.common.mongo.annotation.MongoStorage;
import com.mongodb.gridfs.GridFSFile;

/**
 * MongoDB文件存储服务接口实现类
 * 
 * @author chenboyang
 *
 */
@MongoStorage(name = "", type = Object.class)
public class MongoDBFileServiceImpl implements MongoDBFileService {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	@Autowired(required = false)
	private GridFsService gridFsService;
	
	/**
	 * 系统应用服务接口
	 */
	@Autowired
	private ApplyService applyService;
	
	
	public void download(String name, OutputStream output) {
		try {
			gridFsService.query(name).writeTo(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void download(String name, String bucket, OutputStream output) {
		try {
			gridFsService.query(name, bucket).writeTo(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public GridFSFile saveFileToMongo(String fileName) {
		return gridFsService.save(applyService.getUploadFile(fileName), fileName);
	}

	public GridFSFile saveFileToMongo(String fileName, String bucket) {
		return gridFsService.save(applyService.getUploadFile(fileName), fileName, bucket);
	}

}
