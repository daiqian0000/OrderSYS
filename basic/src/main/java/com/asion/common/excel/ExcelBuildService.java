package com.asion.common.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * EXCEL构建服务接口
 * 
 * @author chenboyang
 *
 */
public interface ExcelBuildService {

	/**
	 * 构建EXCEL
	 * 
	 * @param head
	 *            表头信息
	 * @param body
	 *            主体内容
	 * @return EXCEL文本信息
	 */
	HSSFWorkbook build(List<? extends Object> head, List<? extends List<? extends Object>> body);

	/**
	 * 构建EXCEL
	 * 
	 * @param head
	 *            表头信息
	 * @param body
	 *            主体内容
	 * @param book
	 *            文本信息
	 * @param headStyle
	 *            表头样式
	 * @param bodyStyle
	 *            主体样式
	 * @return EXCEL文本信息
	 */
	HSSFWorkbook build(List<? extends Object> head, List<? extends List<? extends Object>> body, HSSFWorkbook book,
			HSSFCellStyle headStyle, HSSFCellStyle bodyStyle);

}
