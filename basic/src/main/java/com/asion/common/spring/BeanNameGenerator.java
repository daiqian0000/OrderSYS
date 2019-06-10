package com.asion.common.spring;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * SpringBean注解命名生成器
 * 
 * @author chenboyang
 *
 */
public class BeanNameGenerator extends AnnotationBeanNameGenerator {

	/**
	 * SpringBean注解类名称
	 */
	private static final String SPRINGBEAN_ANNOTATION_CLASSNAME = "com.asion.common.spring.SpringBean";

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			AnnotationMetadata amd = ((AnnotatedBeanDefinition) definition).getMetadata();
			Set<String> types = amd.getAnnotationTypes();
			String beanName = null;
			for (String type : types) {
				AnnotationAttributes attributes = AnnotationAttributes
						.fromMap(amd.getAnnotationAttributes(type, false));
				if (isSpringBeanValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
					Object value = attributes.get("value");
					if (value instanceof String) {
						String strVal = (String) value;
						if (StringUtils.hasLength(strVal)) {
							if (beanName != null && !strVal.equals(beanName)) {
								throw new IllegalStateException("Stereotype annotations suggest inconsistent "
										+ "component names: '" + beanName + "' versus '" + strVal + "'");
							}
							beanName = strVal;
						}
					}
				}
			}
			if (StringUtils.hasText(beanName)) {
				return beanName;
			}
		}
		return super.generateBeanName(definition, registry);
	}

	/**
	 * 是否包含SpringBean注解命名属性
	 * 
	 * @param annotationType
	 *            注解类型
	 * @param metaAnnotationTypes
	 *            注解类型集
	 * @param attributes
	 *            注解属性
	 * @return 是否包含
	 */
	protected boolean isSpringBeanValue(String annotationType, Set<String> metaAnnotationTypes,
			Map<String, Object> attributes) {
		boolean isStereotype = annotationType.equals(SPRINGBEAN_ANNOTATION_CLASSNAME)
				|| (metaAnnotationTypes != null && metaAnnotationTypes.contains(SPRINGBEAN_ANNOTATION_CLASSNAME))
				|| annotationType.equals("javax.annotation.ManagedBean") || annotationType.equals("javax.inject.Named");
		return (isStereotype && attributes != null && attributes.containsKey("value"));
	}

}
