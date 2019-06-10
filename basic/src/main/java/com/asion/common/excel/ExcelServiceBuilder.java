package com.asion.common.excel;

/**
 * Excel服务构建器
 * 
 * @author chenboyang
 *
 */
public abstract class ExcelServiceBuilder {

	/**
	 * Excel构建服务
	 */
	private static ExcelBuildService excelBuildService = null;

	/**
	 * Excel导出服务
	 */
	private static ExcelExportService excelExportService = null;

	/**
	 * 获取Excel构建服务
	 * 
	 * @return Excel构建服务
	 */
	public static ExcelBuildService excelBuildService() {
		if (excelBuildService == null) {
			excelBuildService = new ExcelBuildServiceImpl();
		}
		return excelBuildService;
	}

	/**
	 * 获取Excel导出服务
	 * 
	 * @return Excel导出服务
	 */
	public static ExcelExportService excelExportService() {
		if (excelExportService == null) {
			excelExportService = new ExcelExportServiceImpl();
		}
		return excelExportService;
	}

}
