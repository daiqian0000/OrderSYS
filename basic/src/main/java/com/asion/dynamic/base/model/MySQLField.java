package com.asion.dynamic.base.model;

/**
 * MySQL字段信息
 * 
 * @author chenboyang
 *
 */
public class MySQLField {
	
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 列名
	 */
	private String columnName;

	/**
	 * 旧列名
	 */
	private String oldColumnName;

	/**
	 * 新列名
	 */
	private String newColumnName;

	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 长度
	 */
	private Integer length;

	/**
	 * 精度
	 */
	private Integer decimal;

	/**
	 * 不为空
	 */
	private String notNull;

	/**
	 * 默认值
	 */
	private String defaultValue;

	/**
	 * 描述
	 */
	private String comment;

	/**
	 * 操作
	 */
	private String operation;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOldColumnName() {
		return oldColumnName;
	}

	public void setOldColumnName(String oldColumnName) {
		this.oldColumnName = oldColumnName;
	}

	public String getNewColumnName() {
		return newColumnName;
	}

	public void setNewColumnName(String newColumnName) {
		this.newColumnName = newColumnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getDecimal() {
		return decimal;
	}

	public void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	public String getNotNull() {
		return notNull;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
