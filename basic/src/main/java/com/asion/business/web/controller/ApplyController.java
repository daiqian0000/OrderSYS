package com.asion.business.web.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.asion.business.sys.service.SysService;
import com.asion.business.web.service.ApplyService;
import com.asion.common.log.LogInfo;

/**
 * 系统应用控制器
 * 
 * @author chenboyang
 *
 */
public class ApplyController {

	/**
	 * 系统服务
	 */
	@Autowired
	private SysService sysService;

	/**
	 * 系统应用服务接口
	 */
	@Autowired
	private ApplyService applyService;

	/**
	 * 获取系统默认信息配置属性
	 * 
	 * @return 配置属性
	 */
	@LogInfo(info = "获取系统默认信息配置属性")
	@RequestMapping("/systemConfig")
	public Object systemConfig() {
		return sysService.sysProperties();
	}

	/**
	 * 系统统一文件上传
	 * 
	 * @param file
	 *            文件
	 * @param bucket
	 *            存储板块名称
	 * @return 文件保存信息
	 */
	@LogInfo(info = "系统统一文件上传")
	@RequestMapping("/upload")
	public Object upload(MultipartFile file, String bucket) {
		return applyService.upload(file, bucket);
	}

	/**
	 * 系统统一文件批量上传
	 * 
	 * @param request
	 *            请求信息
	 * @param bucket
	 *            存储板块名称
	 * @return 文件保存信息
	 */
	@LogInfo(info = "系统统一文件上传")
	@RequestMapping("/batchUpload")
	public Object batchUpload(HttpServletRequest request, String bucket) {
		List<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFiles("file");
		return applyService.upload(fileList, bucket);
	}

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
	@LogInfo(info = "系统统一文件下载")
	@RequestMapping("/download")
	public void download(String name, String bucket, OutputStream output) {
		applyService.download(name, bucket, output);
	}

	/**
	 * HTTP反向代理服务
	 * 
	 * @param request
	 *            请求信息
	 * @return 响应结果
	 */
	@LogInfo(info = "HTTP反向代理服务")
	@RequestMapping("/proxy")
	public Object proxy(HttpServletRequest request) {
		return applyService.proxy(request);
	}

}
