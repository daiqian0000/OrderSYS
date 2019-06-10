package com.asion.common.gis.arcgis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.asion.common.gis.arcgis.model.Attribute;
import com.asion.common.gis.geometry.Geometry;

/**
 * 要素类型注解
 * 
 * @author chenboyang
 *
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureType {
	
	/**
	 * 几何数据类型
	 * @return 数据类型
	 */
	Class<? extends Geometry> geometry();

	/**
	 * 业务数据类型
	 * @return 数据类型
	 */
	Class<? extends Attribute> attribute();

}
