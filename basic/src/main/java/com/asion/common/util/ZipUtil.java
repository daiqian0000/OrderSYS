package com.asion.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * ZIP压缩帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class ZipUtil {

	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = Logger.getLogger(IOUtil.class);
	
	/**
	 * 将文件打包到压缩文件中
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param files
	 *            文件
	 */
	public static void zip(File zipFile, File... files) {
		ZipOutputStream zos = null;
		BufferedInputStream bis = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				zos.putNextEntry(new ZipEntry(file.getName()));
				bis = new BufferedInputStream(new FileInputStream(file));
	            IOUtil.write(bis, zos);
	            zos.closeEntry();
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

}
