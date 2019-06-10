package com.asion.business.web.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.asion.business.sys.service.SysService;
import com.asion.business.sys.storage.StorageType;
import com.asion.business.web.model.FileInfo;
import com.asion.business.web.session.SessionHttpStorage;
import com.asion.business.web.session.SessionRedisStorage;
import com.asion.business.web.session.SessionStorage;
import com.asion.common.io.storage.FileStorage;
import com.asion.common.util.IDUtil;
import com.asion.common.util.SpringContextUtil;

/**
 * 系统应用服务接口实现类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@Service
@SuppressWarnings("unchecked")
public class ApplyServiceImpl implements ApplyService {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 系统服务
	 */
	@Autowired
	private SysService sysService;

	/**
	 * 文件存储接口
	 */
	@Autowired(required = false)
	private FileStorage fileStorage;


	/**
	 * REST接口访问操作模板
	 */
	@Autowired(required = false)
	private RestTemplate restTemplate;
	
	
	public SessionStorage sessionStorage(StorageType... types) {
		if (ArrayUtils.isNotEmpty(types)) {
			StorageType type = types[0];
			if (StorageType.REDIS == type) {
				return SpringContextUtil.getBean(SessionRedisStorage.class);
			}
		}
		return SpringContextUtil.getBean(SessionHttpStorage.class);
	}
	
	/**
	 * 获取请求信息属性
	 * 
	 * @return 请求信息属性
	 */
	private ServletRequestAttributes servletRequestAttributes() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			return (ServletRequestAttributes) requestAttributes;
		}
		return null;
	}
	
	public HttpServletRequest request() {
		ServletRequestAttributes servletRequestAttributes = servletRequestAttributes();
		return servletRequestAttributes != null ? servletRequestAttributes.getRequest() : null;
	}

	public HttpServletResponse response() {
		ServletRequestAttributes servletRequestAttributes = servletRequestAttributes();
		return servletRequestAttributes != null ? servletRequestAttributes.getResponse() : null;
	}
	
	public HttpSession session() {
		HttpServletRequest request = request();
		return request != null ? request.getSession(false) : null;
	}
	
	public void setSessionAttribute(String name, Object value) {
		HttpSession session = session();
		if (session != null) {
			session.setAttribute(name, value);
		}
	}

	public <T> T getSessionAttribute(String name) {
		HttpSession session = session();
		return session != null ? (T) session.getAttribute(name) : null;
	}

	public File uploadDir() {
		File dir = null;
		HttpSession session = session();
		if (session != null) {
			String serverPath = session.getServletContext().getRealPath("/");
			String dirPath = sysService.sysProperties().getUploadDir();
			dir = FileUtils.getFile(serverPath + dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return dir;
	}

	public File getUploadFile(String fileName) {
		return new File(uploadDir(), fileName);
	}
	
	public FileInfo upload(File file) {
		return upload(file, null);
	}

	public FileInfo upload(File file, String bucket) {
		FileInfo fileInfo = null;
		try {
			if (file != null) {
				fileInfo = new FileInfo();
				fileInfo.setOriginName(file.getName());
				StringBuilder fileName = new StringBuilder(IDUtil.createUUID32());
				if (fileInfo.getOriginName().contains(".")) {
					String suffix = fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf("."));
					fileName.append(suffix);
					fileInfo.setSuffix(suffix);
				}
				fileInfo.setFileName(fileName.toString());
				fileInfo.setSize(file.length());
				if (StringUtils.isNotBlank(bucket)) {
					fileInfo.setPath(fileStorage.save(bucket, fileInfo.getFileName(), FileUtils.openInputStream(file)));
				} else {
					fileInfo.setPath(fileStorage.save(fileInfo.getFileName(), FileUtils.openInputStream(file)));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	public FileInfo upload(MultipartFile file) {
		return upload(file, null);
	}

	public FileInfo upload(MultipartFile file, String bucket) {
		FileInfo fileInfo = null;
		try {
			if (file != null) {
				fileInfo = new FileInfo();
				fileInfo.setOriginName(file.getOriginalFilename());
				StringBuilder fileName = new StringBuilder(IDUtil.createUUID32());
				if (fileInfo.getOriginName().contains(".")) {
					String suffix = fileInfo.getOriginName().substring(fileInfo.getOriginName().lastIndexOf("."));
					fileName.append(suffix);
					fileInfo.setSuffix(suffix);
				}
				fileInfo.setFileName(fileName.toString());
				fileInfo.setSize(file.getSize());
				if (StringUtils.isNotBlank(bucket)) {
					fileInfo.setPath(fileStorage.save(bucket, fileInfo.getFileName(), file.getInputStream()));
				} else {
					fileInfo.setPath(fileStorage.save(fileInfo.getFileName(), file.getInputStream()));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	public List<FileInfo> upload(List<MultipartFile> fileList) {
		return upload(fileList, null);
	}

	public List<FileInfo> upload(List<MultipartFile> fileList, String bucket) {
		List<FileInfo> list = null;
		if (CollectionUtils.isNotEmpty(fileList)) {
			list = new ArrayList<FileInfo>();
			for (MultipartFile file : fileList) {
				FileInfo fileInfo = upload(file, bucket);
				if (fileInfo != null) {
					list.add(fileInfo);
				}
			}
		}
		return list;
	}

	public void download(String name, String bucket, OutputStream output) {
		Assert.hasText(name, "文件名称为空，无法下载！");
		if (StringUtils.isNotBlank(bucket)) {
			fileStorage.write(bucket, name, output);
		} else {
			fileStorage.write(name, output);
		}
	}

	@Override
	public Object proxy(HttpServletRequest request) {
		String url = request.getQueryString();
		if (StringUtils.isNotBlank(url) && (url.startsWith("http://") || url.startsWith("https://"))) {
			return restTemplate.getForEntity(url, Object.class).getBody();
		}
		return null;
	}

}
