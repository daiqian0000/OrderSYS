package com.asion.common.mybatis.plus;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

/**
 * 业务相关SQL自动注入器
 * 
 * @author chenboyang
 *
 */
public class BusinessSqlInjector extends LogicSqlInjector {

	/**
	 * 几何类型字段属性名
	 */
	public static final String SHAPE = "shape";

	/**
	 * 几何类型字段名
	 */
	public static final String SHAPE_CAPITAL = "SHAPE";

	/**
	 * 查询几何类型字段列
	 */
	public static final String TEXT_SHAPE = "ASTEXT(SHAPE) AS SHAPE";

	/**
	 * 设置几何类型字段函数前缀
	 */
	public static final String GEOM_SHAPE_BEFORE = "GEOMFROMTEXT(";

	/**
	 * 设置几何类型字段函数后缀
	 */
	public static final String GEOM_SHAPE_AFTER = ")";

	@Override
	protected void injectInsertOneSql(boolean selective, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
		KeyGenerator keyGenerator = new NoKeyGenerator();
		StringBuilder fieldBuilder = new StringBuilder();
		StringBuilder placeholderBuilder = new StringBuilder();
		SqlMethod sqlMethod = selective ? SqlMethod.INSERT_ONE : SqlMethod.INSERT_ONE_ALL_COLUMN;

		fieldBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
		placeholderBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
		String keyProperty = null;
		String keyColumn = null;

		// 表包含主键处理逻辑,如果不包含主键当普通字段处理
		if (StringUtils.isNotEmpty(table.getKeyProperty())) {
			if (table.getIdType() == IdType.AUTO) {
				// 自增主键
				keyGenerator = new Jdbc3KeyGenerator();
				keyProperty = table.getKeyProperty();
				keyColumn = table.getKeyColumn();
			} else {
				if (null != table.getKeySequence()) {
					keyGenerator = TableInfoHelper.genKeyGenerator(table, builderAssistant, sqlMethod.getMethod(),
							languageDriver);
					keyProperty = table.getKeyProperty();
					keyColumn = table.getKeyColumn();
					fieldBuilder.append(table.getKeyColumn()).append(",");
					placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
				} else {
					// 用户输入自定义ID
					fieldBuilder.append(table.getKeyColumn()).append(",");
					// 正常自定义主键策略
					placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
				}
			}
		}

		// 是否 IF 标签判断
		boolean ifTag;
		List<TableFieldInfo> fieldList = table.getFieldList();
		for (TableFieldInfo fieldInfo : fieldList) {
			// 在FieldIgnore,INSERT_UPDATE,INSERT 时设置为false
			ifTag = !(FieldFill.INSERT == fieldInfo.getFieldFill()
					|| FieldFill.INSERT_UPDATE == fieldInfo.getFieldFill());
			if (selective && ifTag) {
				fieldBuilder.append(convertIfTagIgnored(fieldInfo, false));
				fieldBuilder.append(fieldInfo.getColumn()).append(",");
				fieldBuilder.append(convertIfTagIgnored(fieldInfo, true));
				placeholderBuilder.append(convertIfTagIgnored(fieldInfo, false));
				valuePlaceholder(fieldInfo, placeholderBuilder);
				placeholderBuilder.append(convertIfTagIgnored(fieldInfo, true));
			} else {
				fieldBuilder.append(fieldInfo.getColumn()).append(",");
				valuePlaceholder(fieldInfo, placeholderBuilder);
			}
		}
		fieldBuilder.append("\n</trim>");
		placeholderBuilder.append("\n</trim>");
		String sql = String.format(sqlMethod.getSql(), table.getTableName(), fieldBuilder.toString(),
				placeholderBuilder.toString());
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		this.addInsertMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource, keyGenerator,
				keyProperty, keyColumn);
	}

	/**
	 * 设置插入SQL的属性占位信息
	 * 
	 * @param fieldInfo
	 *            字段信息
	 * @param placeholderBuilder
	 *            占位字符
	 */
	private void valuePlaceholder(TableFieldInfo fieldInfo, StringBuilder placeholderBuilder) {
		if (SHAPE.equals(fieldInfo.getProperty())) {
			placeholderBuilder.append(GEOM_SHAPE_BEFORE).append("#{").append(fieldInfo.getEl()).append("}")
					.append(GEOM_SHAPE_AFTER).append(",");
		} else {
			placeholderBuilder.append("#{").append(fieldInfo.getEl()).append("},");
		}
	}

	@Override
	protected String sqlSet(boolean selective, TableInfo table, String prefix) {
		StringBuilder set = new StringBuilder();
		set.append("<trim prefix=\"SET\" suffixOverrides=\",\">");

		// 是否 IF 标签判断
		boolean ifTag;
		List<TableFieldInfo> fieldList = table.getFieldList();
		for (TableFieldInfo fieldInfo : fieldList) {
			// 判断是否更新忽略,在FieldIgnore,UPDATE,INSERT_UPDATE设置为false
			ifTag = !(FieldFill.UPDATE == fieldInfo.getFieldFill()
					|| FieldFill.INSERT_UPDATE == fieldInfo.getFieldFill());
			if (selective && ifTag) {
				set.append(convertIfTag(true, fieldInfo, prefix, false));
				valueSet(fieldInfo, set, prefix);
				set.append(convertIfTag(true, fieldInfo, null, true));
			} else if (FieldFill.INSERT != fieldInfo.getFieldFill()) {
				// 排除填充注解字段
				valueSet(fieldInfo, set, prefix);
			}
		}
		set.append("\n</trim>");
		return set.toString();
	}

	/**
	 * 设置更新SQL的属性占位信息
	 * 
	 * @param fieldInfo
	 *            字段信息
	 * @param set
	 *            占位字符
	 * @param prefix
	 *            属性前缀
	 */
	private void valueSet(TableFieldInfo fieldInfo, StringBuilder set, String prefix) {
		if (SHAPE.equals(fieldInfo.getProperty())) {
			set.append(fieldInfo.getColumn()).append("=").append(GEOM_SHAPE_BEFORE).append("#{");
			if (null != prefix) {
				set.append(prefix);
			}
			set.append(fieldInfo.getEl()).append("}").append(GEOM_SHAPE_AFTER).append(",");
		} else {
			set.append(fieldInfo.getColumn()).append("=#{");
			if (null != prefix) {
				set.append(prefix);
			}
			set.append(fieldInfo.getEl()).append("},");
		}
	}

	@Override
	protected String sqlSelectColumns(TableInfo table, boolean entityWrapper) {
		StringBuilder columns = new StringBuilder();
		if (null != table.getResultMap()) {
			// 存在 resultMap 映射返回
			if (entityWrapper) {
				columns.append(
						"<choose><when test=\"ew != null and ew.sqlSelect != null\">${ew.sqlSelect}</when><otherwise>");
			}
			columns.append("*");
			if (entityWrapper) {
				columns.append("</otherwise></choose>");
			}
		} else {
			// 普通查询
			if (entityWrapper) {
				columns.append(
						"<choose><when test=\"ew != null and ew.sqlSelect != null\">${ew.sqlSelect}</when><otherwise>");
			}
			List<TableFieldInfo> fieldList = table.getFieldList();
			int size = 0;
			if (null != fieldList) {
				size = fieldList.size();
			}

			// 主键处理
			if (StringUtils.isNotEmpty(table.getKeyProperty())) {
				if (table.isKeyRelated()) {
					columns.append(table.getKeyColumn()).append(" AS ").append(sqlWordConvert(table.getKeyProperty()));
				} else {
					columns.append(sqlWordConvert(table.getKeyProperty()));
				}
				if (size >= 1) {
					// 判断其余字段是否存在
					columns.append(",");
				}
			}

			if (size >= 1) {
				// 字段处理
				int i = 0;
				Iterator<TableFieldInfo> iterator = fieldList.iterator();
				while (iterator.hasNext()) {
					TableFieldInfo fieldInfo = iterator.next();
					// 匹配转换内容
					String wordConvert = sqlWordConvert(fieldInfo.getProperty());
					if (SHAPE.equals(fieldInfo.getProperty())) {
						columns.append(TEXT_SHAPE);
					} else if (fieldInfo.getColumn().equals(wordConvert)) {
						columns.append(wordConvert);
					} else {
						// 字段属性不一致
						columns.append(fieldInfo.getColumn());
						columns.append(" AS ").append(wordConvert);
					}
					if (i + 1 < size) {
						columns.append(",");
					}
					i++;
				}
			}
			if (entityWrapper) {
				columns.append("</otherwise></choose>");
			}
		}

		// 返回所有查询字段内容
		return columns.toString();
	}

}
