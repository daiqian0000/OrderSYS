package com.asion.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * IO帮助类
 * 
 * @author chenboyang
 *
 */
public abstract class IOUtil {

	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = Logger.getLogger(IOUtil.class);
	
	/**
	 * 将输入流数据写入到输出流
	 * 
	 * @param input
	 *            输入流
	 * @param output
	 *            输出流
	 */
	public static void write(InputStream input, OutputStream output) {
		try {
			int count, bufferLen = 1024;
			byte data[] = new byte[bufferLen];
			while ((count = input.read(data, 0, bufferLen)) != -1) {
				output.write(data, 0, count);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
