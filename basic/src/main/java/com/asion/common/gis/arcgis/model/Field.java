package com.asion.common.gis.arcgis.model;

import java.util.ArrayList;
import java.util.List;

import com.asion.common.util.ReflectUtil;

/**
 * 业务数据字段类
 * 
 * @author chenboyang
 *
 */
public class Field {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 长度
	 */
	private int length;

	/**
	 * 是否编辑
	 */
	private boolean editable;

	/**
	 * 是否为空
	 */
	private boolean nullable;

	/**
	 * 领域范围
	 */
	private Domain domain;

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            名称
	 */
	public Field(String name) {
		this(name, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            名称
	 * @param type
	 *            类型
	 */
	public Field(String name, String type) {
		this(name, type, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            名称
	 * @param type
	 *            类型
	 * @param alias
	 *            别名
	 */
	public Field(String name, String type, String alias) {
		this.name = name;
		this.type = type;
		this.alias = alias;
	}

	/**
	 * 根据类获取字段集
	 * 
	 * @param clazz
	 *            类
	 * @return 字段集
	 */
	public static List<Field> getFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		List<java.lang.reflect.Field> fieldList = ReflectUtil.getFields(clazz);
		for (java.lang.reflect.Field field : fieldList) {
			fields.add(new Field(field.getName()));
		}
		return fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

}
