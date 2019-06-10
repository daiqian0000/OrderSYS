package com.asion.common.excel;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * EXCEL导出服务接口
 * 
 * @author chenboyang
 *
 */
public interface ExcelExportService {

	/**
	 * 
	 * @param head
	 *            表头信息
	 * @param body
	 *            主体内容
	 * @param output
	 *            输出流
	 */
	void export(List<? extends Object> head, List<? extends List<? extends Object>> body, OutputStream output);

	/**
	 * 
	 * @param head
	 *            表头信息
	 * @param body
	 *            主体内容
	 * @param output
	 *            输出流
	 * @param book
	 *            文本信息
	 * @param headStyle
	 *            表头样式
	 * @param bodyStyle
	 *            主体样式
	 */
	void export(List<? extends Object> head, List<? extends List<? extends Object>> body, OutputStream output,
			HSSFWorkbook book, HSSFCellStyle headStyle, HSSFCellStyle bodyStyle);

	/**
	 * 导出EXCEL文件
	 * 
	 * @param head
	 *            表头信息
	 * @param body
	 *            主体内容
	 * @param response
	 *            响应信息
	 */
	void export(List<? extends Object> head, List<? extends List<? extends Object>> body, HttpServletResponse response);

	/**
	 * 导出EXCEL文件
	 * 
	 * @param head
	 *            表头信息
	 * @param body
	 *            主体内容
	 * @param response
	 *            响应信息
	 * @param book
	 *            文本信息
	 * @param headStyle
	 *            表头样式
	 * @param bodyStyle
	 *            主体样式
	 */
	void export(List<? extends Object> head, List<? extends List<? extends Object>> body, HttpServletResponse response,
			HSSFWorkbook book, HSSFCellStyle headStyle, HSSFCellStyle bodyStyle);

}
