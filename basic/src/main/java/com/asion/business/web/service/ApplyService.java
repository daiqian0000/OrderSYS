package com.asion.business.web.service;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.asion.business.sys.storage.StorageType;
import com.asion.business.web.model.FileInfo;
import com.asion.business.web.session.SessionStorage;

/**
 * 系统应用服务接口
 * 
 * @author chenboyang
 *
 */
public interface ApplyService {

	/**
	 * Session存储控制器
	 * 
	 * @param types
	 *            Session存储类型
	 * @return Session存储
	 */
	SessionStorage sessionStorage(StorageType... types);

	/**
	 * 获取当前请求信息
	 * 
	 * @return 请求信息
	 */
	HttpServletRequest request();

	/**
	 * 获取当前响应信息
	 * 
	 * @return 响应信息
	 */
	HttpServletResponse response();

	/**
	 * 获取当前会话信息
	 * 
	 * @return 会话信息
	 */
	HttpSession session();

	/**
	 * 设置会话业务属性
	 * 
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 */
	void setSessionAttribute(String name, Object value);

	/**
	 * 获取会话业务属性
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性值
	 */
	<T> T getSessionAttribute(String name);

	/**
	 * 获取系统文件上传目录
	 * 
	 * @return 目录
	 */
	File uploadDir();

	/**
	 * 根据文件名从文件上传目录获取文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件
	 */
	File getUploadFile(String fileName);

	/**
	 * 普通文件上传
	 * 
	 * @param file
	 *            文件
	 * @return 保存信息
	 */
	FileInfo upload(File file);

	/**
	 * 普通文件上传
	 * 
	 * @param file
	 *            文件
	 * @param bucket
	 *            存储板块
	 * @return 保存信息
	 */
	FileInfo upload(File file, String bucket);

	/**
	 * SpringWeb文件上传
	 * 
	 * @param file
	 *            文件
	 * @return 保存信息
	 */
	FileInfo upload(MultipartFile file);

	/**
	 * SpringWeb文件上传
	 * 
	 * @param file
	 *            文件
	 * @param bucket
	 *            存储板块
	 * @return 保存信息
	 */
	FileInfo upload(MultipartFile file, String bucket);

	/**
	 * SpringWeb文件批量上传
	 * 
	 * @param fileList
	 *            文件列表
	 * @return 保存信息
	 */
	List<FileInfo> upload(List<MultipartFile> fileList);

	/**
	 * SpringWeb文件批量上传
	 * 
	 * @param fileList
	 *            文件列表
	 * @param bucket
	 *            存储板块
	 * @return 保存信息
	 */
	List<FileInfo> upload(List<MultipartFile> fileList, String bucket);

	/**
	 * 系统统一文件下载
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
	 * HTTP反向代理服务
	 * 
	 * @param request
	 *            请求信息
	 * @return 响应结果
	 */
	Object proxy(HttpServletRequest request);

}
