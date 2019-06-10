package com.asion.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.asion.common.util.IDUtil;

/**
 * EXCEL导出服务接口
 * 
 * @author chenboyang
 *
 */
public class ExcelExportServiceImpl implements ExcelExportService {

	/**
	 * 日志记录
	 */
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * EXCEL构建服务接口
	 */
	private ExcelBuildService excelBuildService = ExcelServiceBuilder.excelBuildService();

	public void export(List<? extends Object> head, List<? extends List<? extends Object>> body, OutputStream output) {
		try {
			HSSFWorkbook book = excelBuildService.build(head, body);
			book.write(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void export(List<? extends Object> head, List<? extends List<? extends Object>> body, OutputStream output,
			HSSFWorkbook book, HSSFCellStyle headStyle, HSSFCellStyle bodyStyle) {
		try {
			book = excelBuildService.build(head, body, book, headStyle, bodyStyle);
			book.write(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void export(List<? extends Object> head, List<? extends List<? extends Object>> body,
			HttpServletResponse response) {
		try {
			setExcelExportResponse(response);
			export(head, body, response.getOutputStream());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void export(List<? extends Object> head, List<? extends List<? extends Object>> body,
			HttpServletResponse response, HSSFWorkbook book, HSSFCellStyle headStyle, HSSFCellStyle bodyStyle) {
		try {
			setExcelExportResponse(response);
			export(head, body, response.getOutputStream(), book, headStyle, bodyStyle);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 设置EXCEL导出响应信息
	 * 
	 * @param response
	 *            响应信息
	 */
	private void setExcelExportResponse(HttpServletResponse response) {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename=" + IDUtil.createTimeId() + ".xls");
	}

}
