package com.asion.common.excel;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * EXCEL构建服务接口实现类
 * 
 * @author chenboyang
 *
 */
public class ExcelBuildServiceImpl implements ExcelBuildService {

	public HSSFWorkbook build(List<? extends Object> head, List<? extends List<? extends Object>> body) {
		HSSFWorkbook book = new HSSFWorkbook();
		return build(head, body, book, defaultHeadStyle(book), defaultBodyStyle(book));
	}

	@Override
	public HSSFWorkbook build(List<? extends Object> head, List<? extends List<? extends Object>> body,
			HSSFWorkbook book, HSSFCellStyle headStyle, HSSFCellStyle bodyStyle) {
		// 创建EXCEL文本信息
		HSSFSheet sheet = book.createSheet();
		// 创建EXCEL表头信息
		if (CollectionUtils.isNotEmpty(head)) {
			HSSFRow headRow = sheet.createRow(0);
			for (int i = 0; i < head.size(); i++) {
				HSSFCell cell = headRow.createCell(i);
				cell.setCellStyle(headStyle);
				if (head.get(i) != null) {
					cell.setCellValue(head.get(i).toString());
				}
			}
		}
		// 创建EXCEL主体内容信息
		if (CollectionUtils.isNotEmpty(body)) {
			for (int i = 0; i < body.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				row.setRowStyle(bodyStyle);
				if (CollectionUtils.isNotEmpty(body.get(i))) {
					for (int j = 0; j < body.get(i).size(); j++) {
						HSSFCell cell = row.createCell(j);
						cell.setCellStyle(bodyStyle);
						if (body.get(i).get(j) != null) {
							cell.setCellValue(body.get(i).get(j).toString());
						}
					}
				}
			}
		}
		// 返回EXCEL文本信息
		return book;
	}

	/**
	 * 获取默认表头样式
	 * 
	 * @param book
	 *            文本信息
	 * @return 表头样式
	 */
	private HSSFCellStyle defaultHeadStyle(HSSFWorkbook book) {
		HSSFCellStyle headStyle = book.createCellStyle();
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont headFont = book.createFont();
		headFont.setBold(true);
		headFont.setFontHeightInPoints((short) 12);
		headStyle.setFont(headFont);
		return headStyle;
	}

	/**
	 * 获取默认主体样式
	 * 
	 * @param book
	 *            文本信息
	 * @return 主体样式
	 */
	private HSSFCellStyle defaultBodyStyle(HSSFWorkbook book) {
		HSSFCellStyle bodyStyle = book.createCellStyle();
		HSSFFont bodyFont = book.createFont();
		bodyFont.setFontHeightInPoints((short) 12);
		bodyStyle.setFont(bodyFont);
		return bodyStyle;
	}

}
