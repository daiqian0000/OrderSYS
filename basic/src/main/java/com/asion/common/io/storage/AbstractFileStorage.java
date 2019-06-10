package com.asion.common.io.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * <h1>文件存储服务抽象类</h1>
 * <h2>主要功能：</h2>
 * <p>
 * 保存文件、判断文件是否存在、删除文件、复制文件、查询文件、写入文件、获取文件文本
 * </p>
 * 
 * @author chenboyang
 * 
 */
public abstract class AbstractFileStorage implements FileStorage {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 文件存储根目录
	 */
	protected abstract String directory();

	/**
	 * 默认存储板块
	 */
	protected abstract String defaultBucket();

	/**
	 * 根据存储板块和文件名称获取文件
	 * 
	 * @param bucket
	 *            存储板块
	 * @param name
	 *            文件名称
	 * @return 文件
	 */
	private File getFile(String bucket, String name) {
		File dir = new File(directory() + "/" + bucket);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return new File(dir, name);
	}

	/**
	 * 根据存储板块和文件名称获取文件访问相对路径
	 * 
	 * @param bucket
	 *            存储板块
	 * @param name
	 *            文件名称
	 * @return 路径
	 */
	private String relativePath(String bucket, String name) {
		return directory() + "/" + bucket + "/" + name;
	}

	@Override
	public String save(String bucket, String name, String path) {
		return save(bucket, name, new File(path));
	}

	@Override
	public String save(String name, String path) {
		return save(defaultBucket(), name, path);
	}

	@Override
	public String save(String bucket, String name, File file) {
		try {
			File newFile = getFile(bucket, name);
			newFile.createNewFile();
			FileUtils.copyFile(file, newFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return relativePath(bucket, name);
	}

	@Override
	public String save(String name, File file) {
		return save(defaultBucket(), name, file);
	}

	@Override
	public String save(String bucket, String name, InputStream input) {
		OutputStream output = null;
		try {
			File newFile = getFile(bucket, name);
			newFile.createNewFile();
			output = FileUtils.openOutputStream(newFile);
			IOUtils.copy(input, output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return relativePath(bucket, name);
	}

	@Override
	public String save(String name, InputStream input) {
		return save(defaultBucket(), name, input);
	}

	@Override
	public boolean exist(String bucket, String name) {
		return getFile(bucket, name).exists();
	}

	@Override
	public boolean exist(String name) {
		return exist(defaultBucket(), name);
	}

	@Override
	public void remove(String bucket, String name) {
		File file = getFile(bucket, name);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public void remove(String name) {
		remove(defaultBucket(), name);
	}

	@Override
	public void copy(String bucket, String sourceName, String targetName) {
		try {
			File source = getFile(bucket, sourceName);
			File target = getFile(bucket, targetName);
			if (source.exists()) {
				if (!target.exists()) {
					target.createNewFile();
				}
				FileUtils.copyFile(source, target);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void copy(String sourceName, String targetName) {
		copy(defaultBucket(), sourceName, targetName);
	}

	@Override
	public InputStream find(String bucket, String name) {
		try {
			File source = getFile(bucket, name);
			if (source.exists()) {
				return FileUtils.openInputStream(source);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public InputStream find(String name) {
		return find(defaultBucket(), name);
	}

	@Override
	public String findText(String bucket, String name, Charset charset) {
		try {
			File file = getFile(bucket, name);
			if (file.exists()) {
				return FileUtils.readFileToString(file, charset);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String findText(String name, Charset charset) {
		return findText(defaultBucket(), name, charset);
	}

	@Override
	public String findText(String bucket, String name) {
		return findText(bucket, name, Charset.defaultCharset());
	}

	@Override
	public String findText(String name) {
		return findText(defaultBucket(), name);
	}

	@Override
	public void write(String bucket, String name, OutputStream output) {
		InputStream input = null;
		try {
			File file = getFile(bucket, name);
			if (file.exists()) {
				input = FileUtils.openInputStream(file);
				IOUtils.copy(input, output);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void write(String name, OutputStream output) {
		write(defaultBucket(), name, output);
	}

}
