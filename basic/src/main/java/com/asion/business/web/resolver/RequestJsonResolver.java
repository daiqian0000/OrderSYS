package com.asion.business.web.resolver;

import java.util.Collection;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.HtmlUtils;

import com.asion.business.web.annotation.RequestJson;
import com.asion.common.util.JsonUtil;
import com.asion.common.util.PageBroker;
import com.asion.common.util.ParamBroker;
import com.asion.common.util.ReflectUtil;

/**
 * JSON请求参数模型解析器
 * 
 * @author chenboyang
 *
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class RequestJsonResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestJson.class);
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
		String name = parameter.getParameterAnnotation(RequestJson.class).value();
		String value = HtmlUtils.htmlUnescape(request.getParameter(name));
		Class<?> type = parameter.getParameterType();
		Object object = ReflectUtil.createObject(type);
		WebDataBinder binder = binderFactory.createBinder(request, object, name);
		if (ParamBroker.class.equals(type)) {
			ParamBroker<String, Object> paramBroker = binder.convertIfNecessary(object, ParamBroker.class);
			paramBroker.setParam((Map<String, Object>) JsonUtil.toObject(value, paramBroker.getParam().getClass()));
		} else if (PageBroker.class.equals(type)) {
			PageBroker<?> pageBroker = binder.convertIfNecessary(object, PageBroker.class);
			Map<String, Object> map = JsonUtil.toObject(value, pageBroker.getPage().getClass());
			for (String key : map.keySet()) {
				pageBroker.getPage().put(key, map.get(key));
			}
		} else {
			if (type.isArray()) {
				Class<?> clazz = ReflectUtil.getClass(ReflectUtil.getPropertyType(type, null));
				object = JsonUtil.toObject(value, clazz);
			} else if (Collection.class.isAssignableFrom(type)) {
				Class<?> clazz = ReflectUtil.getClass(ReflectUtil.getPropertyType(type, null));
				object = JsonUtil.toObject(value, clazz);
			} else {
				object = JsonUtil.toObject(value, type);
			}
		}
		return binder.convertIfNecessary(object, type);
	}

}
